package com.alexmau.suggestionapp.dao;

import com.alexmau.suggestionapp.TestUtil;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.entity.SuggestionEntity;
import com.alexmau.suggestionapp.mapper.SuggestionMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuggestionDaoTest {

    @Autowired
    private SuggestionDAO suggestionDAO;

    @Autowired
    private SuggestionMapper suggestionMapper;

    @BeforeEach
    void createTestData() {
        suggestionDAO
                .saveAll(TestUtil.SUGGESTION_LIST
                        .stream()
                        .map(s -> suggestionMapper.mapDtoToEntity(s))
                        .collect(Collectors.toList()));
    }

    @AfterEach
    void clearTestData() {
        suggestionDAO.deleteAll();
    }

    @Test
    void shouldSaveEntity() {
        suggestionDAO.save(SuggestionEntity.builder()
                .id(new Random().nextLong())
                .city("Moscow")
                .build());
        SuggestionEntity entity = suggestionDAO.findByCity("Moscow");
        assertNotNull(entity);
    }

    @Test
    void shouldFindAllEntitiesIdIn() {
        List<SuggestionEntity> suggestionFromDb = suggestionDAO.findByIdIn(TestUtil.IDS);
        List<Suggestion> suggestion = suggestionFromDb.stream().map(s -> suggestionMapper
                .mapSuggestionEntityToSuggestion(s))
                .collect(Collectors.toList());
        assertNotNull(suggestion);
        assertEquals(suggestion.size(), TestUtil.SUGGESTION_LIST.size());
        assertThat(TestUtil.SUGGESTION_LIST)
                .usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(suggestion);
    }

    @Test
    void shouldFindEntityByParams() {
        List<SuggestionEntity> suggestion = suggestionDAO.findAllSuggestionByRegionAndCityAndSettlemntAndStreet(
                TestUtil.PARAM_REGION_1, TestUtil.PARAM_CITY_1, TestUtil.PARAM_SETTLEMENT_1, TestUtil.PARAM_STREET_1
        );
        assertTrue(suggestion.containsAll(List.of(TestUtil.SUGGESTION_1, TestUtil.SUGGESTION_3, TestUtil.SUGGESTION_4,
                TestUtil.SUGGESTION_5, TestUtil.SUGGESTION_6, TestUtil.SUGGESTION_7, TestUtil.SUGGESTION_8)
                .stream()
                .map(s -> suggestionMapper.mapDtoToEntity(s))
                .collect(Collectors.toList())));

        suggestion = suggestionDAO.findAllSuggestionByRegionAndCityAndSettlemntAndStreet(
                TestUtil.PARAM_REGION_2, TestUtil.PARAM_CITY_2, TestUtil.PARAM_SETTLEMENT_2, TestUtil.PARAM_STREET_2
        );

        assertTrue(suggestion.containsAll(List.of(TestUtil.SUGGESTION_2)
                .stream()
                .map(s -> suggestionMapper.mapDtoToEntity(s))
                .collect(Collectors.toList())));
    }
}
