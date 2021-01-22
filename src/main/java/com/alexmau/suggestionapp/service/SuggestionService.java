package com.alexmau.suggestionapp.service;

import com.alexmau.suggestionapp.client.DadataClient;
import com.alexmau.suggestionapp.dao.SuggestionDAO;
import com.alexmau.suggestionapp.dto.ListResponse;
import com.alexmau.suggestionapp.dto.ResponseMessage;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.entity.SuggestionEntity;
import com.alexmau.suggestionapp.mapper.SuggestionMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private Set<Suggestion> threeHoursCacheRequest = new HashSet<>();

    public ListResponse<Suggestion> findSuggestion(Suggestion suggestion) {
        final ListResponse<Suggestion> response = new ListResponse<>();

        if (threeHoursCacheRequest.contains(suggestion)) {
            List<SuggestionEntity> suggestionFromDb = suggestionDAO
                    .findAllSuggestionByRegionAndCityAndSettlemntAndStreet(
                    suggestion.getRegion(), suggestion.getCity(), suggestion.getSettlement(), suggestion.getStreet());
            response.setListResult(suggestionFromDb.stream()
                    .map(s -> suggestionMapper.mapSuggestionEntityToSuggestion(s))
                    .collect(Collectors.toList()));
        } else {
            List<SuggestionEntity> suggestionFromResource = findSuggestionOnResourseAndSave(suggestion, response);
            response.setListResult(suggestionFromResource.stream()
                    .map(s -> suggestionMapper.mapSuggestionEntityToSuggestion(s))
                    .collect(Collectors.toList()));
        }
        return response;
    }

    private List<SuggestionEntity> findSuggestionOnResourseAndSave(Suggestion suggestion,
                                                                   ListResponse<Suggestion> response) {
        HttpResponse resourceResponse  = null;
        List<Suggestion> suggestionListFromResource = new ArrayList<>();
        List<SuggestionEntity> result = new ArrayList<>();
        try {
            resourceResponse = client.sendRequest(createQueryFromSuggestion(suggestion));
            JsonNode node = objectMapper.readTree(String.valueOf(resourceResponse.body()));
            suggestionListFromResource = objectMapper.readValue(node.findParents("postal_code").toString(),
                    new TypeReference<List<Suggestion>>(){});
            result = suggestionListFromResource
                    .stream()
                    .map(s -> suggestionMapper.mapDtoToEntity(s)).collect(Collectors.toList());
            suggestionDAO.saveAll(result);
            suggestion.setDateTime(LocalDateTime.now());
            threeHoursCacheRequest.add(suggestion);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
            response.addMessage(new ResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        catch (InterruptedException e) {
            log.error(e.getMessage());
            response.addMessage(new ResponseMessage(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value()));
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error(e.getMessage());
            response.addMessage(new ResponseMessage(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE.value()));
        }
        return result;
    }

    private String createQueryFromSuggestion (Suggestion suggestion) {
        StringBuilder sb = new StringBuilder("{ \"query\": \"");
        sb.append(suggestion.getPostalCode()==null?"":suggestion.getPostalCode() + " ");
        sb.append(suggestion.getRegion()==null?"":suggestion.getRegion() + " ");
        sb.append(suggestion.getCity()==null?"":suggestion.getCity() + " ");
        sb.append(suggestion.getSettlement()==null?"":suggestion.getSettlement() + " ");
        sb.append(suggestion.getStreet()==null?"":suggestion.getStreet() + " ");
        sb.append(suggestion.getHouse()==null?"":suggestion.getHouse() + " ");
        sb.append("\" }");
        return new String(sb);
    }

    @Scheduled(cron = "@midnight")
    protected void deleteDataOlderThanOneMonthOrCountLessThanThree() {
        final LocalDateTime expired = LocalDateTime.now().minusMonths(3);
        suggestionDAO.deleteAllSuggestionsOlderThanMonthOrHaveCountLessThanThree(expired);
    }

    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * *")
    protected void deleteThreeHourCache() {
        LocalDateTime currentTime = LocalDateTime.now();
        threeHoursCacheRequest.removeIf(s -> s.getDateTime().plusHours(3).isAfter(currentTime));
    }
}
