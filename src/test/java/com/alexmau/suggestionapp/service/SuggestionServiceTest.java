package com.alexmau.suggestionapp.service;

import com.alexmau.suggestionapp.TestUtil;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.entity.SuggestionEntity;
import com.alexmau.suggestionapp.mapper.SuggestionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuggestionServiceTest {

    @Autowired
    private SuggestionMapper suggestionMapper;

    @Mock
    private SuggestionService service;

    @Test
    void shouldReturnDataByRequest() throws Exception {
        Mockito.when(service.getSuggestionList(TestUtil.REQUEST)).thenReturn(TestUtil.SUGGESTION_LIST);

        List<Suggestion> suggestion = service.getSuggestionList(TestUtil.REQUEST);

        assertThat(TestUtil.SUGGESTION_LIST)
                .usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(suggestion);

        Mockito.verify(service).getSuggestionList(TestUtil.REQUEST);
    }

    @Test
    void shouldFindDataByParams() throws Exception {
        List<SuggestionEntity> entityList = List.of(TestUtil.SUGGESTION_1, TestUtil.SUGGESTION_3, TestUtil.SUGGESTION_4,
                TestUtil.SUGGESTION_5, TestUtil.SUGGESTION_6, TestUtil.SUGGESTION_7, TestUtil.SUGGESTION_8)
                .stream()
                .map(s -> suggestionMapper.mapDtoToEntity(s))
                .collect(Collectors.toList());

        Mockito.when(service.findFromDb(TestUtil.PARAM_REGION_1, TestUtil.PARAM_CITY_1,
                TestUtil.PARAM_SETTLEMENT_1, TestUtil.PARAM_STREET_1))
                .thenReturn(entityList);

        List<SuggestionEntity> suggestion =
                service.findFromDb(TestUtil.PARAM_REGION_1, TestUtil.PARAM_CITY_1,
                        TestUtil.PARAM_SETTLEMENT_1, TestUtil.PARAM_STREET_1);

        assertThat(entityList)
                .usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(suggestion);

        Mockito.verify(service).findFromDb(TestUtil.PARAM_REGION_1, TestUtil.PARAM_CITY_1,
                TestUtil.PARAM_SETTLEMENT_1, TestUtil.PARAM_STREET_1);
    }
}
