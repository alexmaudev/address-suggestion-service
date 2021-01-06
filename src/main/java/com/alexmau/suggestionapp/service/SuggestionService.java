package com.alexmau.suggestionapp.service;

import com.alexmau.suggestionapp.client.DadataClient;
import com.alexmau.suggestionapp.dao.SuggestionDAO;
import com.alexmau.suggestionapp.entity.SuggestionEntity;
import com.alexmau.suggestionapp.mapper.SuggestionMapper;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class SuggestionService {

    @Autowired
    private DadataClient client;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SuggestionMapper suggestionMapper;

    @Autowired
    private SuggestionDAO suggestionDAO;

    @Setter
    private Map<String, List<Long>> threeHoursCacheRequest = new HashMap<>();

    public List<Suggestion> getSuggestionList(String requestBody) throws ExecutionException, InterruptedException {
        List<Long> listOfIds;
        if ((listOfIds = threeHoursCacheRequest.get(requestBody)) !=null) {
            List<SuggestionEntity> suggestionListFromDb = suggestionDAO.findByIdIn(listOfIds);
            if (suggestionListFromDb.isEmpty()) {
                List<Suggestion> suggestion = findSuggestionOnResourse(requestBody);
                saveEntity(requestBody, suggestion);
                return suggestion;
            } else {
                return suggestionListFromDb
                        .stream().map(s -> suggestionMapper.mapSuggestionEntityToSuggestion(s))
                        .collect(Collectors.toList());
            }
        } else {
            List<Suggestion> suggestion = findSuggestionOnResourse(requestBody);
            saveEntity(requestBody, suggestion);
            return suggestion;
        }
    }

    public List<SuggestionEntity> findFromDb(String region, String city, String settlement, String street) {
        return suggestionDAO
                .findAllSuggestionByRegionAndCityAndSettlemntAndStreet(region, city, settlement, street);

    }

    private void saveEntity(String requestBody, List<Suggestion> suggestion) {
        List<SuggestionEntity> entityToSave = new ArrayList<>();
        for (Suggestion s : suggestion) {
            SuggestionEntity entity = suggestionMapper.mapDtoToEntity(s);
            entityToSave.add(entity);
        }
        List<SuggestionEntity> saveEntity = new ArrayList<>();
        suggestionDAO.saveAll(entityToSave).forEach(saveEntity::add);
        List<Long> ids = saveEntity
                .stream()
                .map(SuggestionEntity::getId)
                .collect(Collectors.toList());
        threeHoursCacheRequest.put(requestBody, ids);
    }

    private List<Suggestion> findSuggestionOnResourse(String requestBody) throws ExecutionException, InterruptedException {
        final HttpResponse response = client.sendRequest(requestBody);
        String jsonBody = String.valueOf(response.body());

        List<Suggestion> result = new ArrayList<>();
        try {
            JsonNode node = objectMapper.readTree(jsonBody);
            result = objectMapper.readValue(node.findParents("postal_code").toString(),
                    new TypeReference<List<Suggestion>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Scheduled(cron = "@midnight")
    protected void deleteDataOlderThanOneMonthOrCountLessThanThree() {
        final LocalDateTime expired = LocalDateTime.now().minusMonths(3);
        suggestionDAO.deleteAllSuggestionsOlderThanMonthOrHaveCountLessThanThree(expired);
    }

    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * *")
    protected void deleteThreeHourCache() {
        threeHoursCacheRequest.clear();
    }
}
