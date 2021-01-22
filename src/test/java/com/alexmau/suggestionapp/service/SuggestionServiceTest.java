package com.alexmau.suggestionapp.service;

import com.alexmau.suggestionapp.TestUtil;
import com.alexmau.suggestionapp.client.DadataClient;
import com.alexmau.suggestionapp.dao.SuggestionDAO;
import com.alexmau.suggestionapp.dto.ListResponse;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.mapper.SuggestionMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuggestionServiceTest {

    @Autowired
    @Spy
    private final SuggestionMapper suggestionMapper = Mappers.getMapper(SuggestionMapper.class);;

    @Mock
    private DadataClient client;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private SuggestionDAO dao;

    @InjectMocks
    private SuggestionService service;

    @Test
    void shouldReturnDataFromDb() throws Exception {
        service.setThreeHoursCacheRequest(Set.of(TestUtil.SUGGESTION_REQUEST));

        Mockito.when(dao
                .findAllSuggestionByRegionAndCityAndSettlemntAndStreet(
                        TestUtil.SUGGESTION_REQUEST.getRegion(), TestUtil.SUGGESTION_REQUEST.getCity(),
                        TestUtil.SUGGESTION_REQUEST.getSettlement(), TestUtil.SUGGESTION_REQUEST.getStreet()))
                .thenReturn(TestUtil.SUGGESTION_LIST
                .stream().map(s -> suggestionMapper.mapDtoToEntity(s)).collect(Collectors.toList()));

        ListResponse<Suggestion> suggestion = service.findSuggestion(TestUtil.SUGGESTION_REQUEST);

        assertThat(TestUtil.SUGGESTION_LIST)
                .usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(suggestion.getListResult());
    }

    @Test
    void shouldReturnDataFromResource() throws Exception {
        service.setThreeHoursCacheRequest(new HashSet<>());
        HttpResponse response = TestUtil.createResponse();
        JsonNode node = objectMapper.readTree(String.valueOf(response.body()));

        Mockito.when(client.sendRequest(TestUtil.createSuggestionQuery()))
                .thenReturn(response);

        Mockito.when(objectMapper.readTree(String.valueOf(response.body()))).thenReturn(node);
        Mockito.when(objectMapper.readValue(node.findParents("postal_code").toString(),
                new TypeReference<List<Suggestion>>(){})).thenReturn(TestUtil.SUGGESTION_LIST);

        ListResponse<Suggestion> suggestion = service.findSuggestion(TestUtil.SUGGESTION_REQUEST);

        assertThat(TestUtil.SUGGESTION_LIST)
                .usingRecursiveComparison().ignoringFields("id","dateTime").isEqualTo(suggestion.getListResult());
    }
}
