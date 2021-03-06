package com.alexmau.suggestionapp.controller;

import com.alexmau.suggestionapp.TestUtil;
import com.alexmau.suggestionapp.dto.ListResponse;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.service.SuggestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SuggestionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SuggestionService service;

    @InjectMocks
    private SuggestionController controller;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void post() throws Exception {
        final ListResponse<Suggestion> listResponse = TestUtil.createSuggestionListResponse();

        Mockito.when(service.findSuggestion(TestUtil.SUGGESTION_REQUEST))
                .thenReturn(listResponse);

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/suggest/address")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(TestUtil.SUGGESTION_REQUEST));

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.listResult[0].id").value(TestUtil.SUGGESTION_1.getId()))
                .andExpect(jsonPath("$.listResult[1].id").value(TestUtil.SUGGESTION_2.getId()))
                .andExpect(jsonPath("$.listResult[2].id").value(TestUtil.SUGGESTION_3.getId()))
                .andExpect(jsonPath("$.listResult[3].id").value(TestUtil.SUGGESTION_4.getId()))
                .andExpect(jsonPath("$.listResult[4].id").value(TestUtil.SUGGESTION_5.getId()))
                .andExpect(jsonPath("$.listResult[5].id").value(TestUtil.SUGGESTION_6.getId()))
                .andExpect(jsonPath("$.listResult[6].id").value(TestUtil.SUGGESTION_7.getId()))
                .andExpect(jsonPath("$.listResult[7].id").value(TestUtil.SUGGESTION_8.getId()))
                .andExpect(jsonPath("$.listResult[0].city").value(TestUtil.SUGGESTION_1.getCity()))
                .andExpect(jsonPath("$.listResult[1].city").value(TestUtil.SUGGESTION_2.getCity()))
                .andExpect(jsonPath("$.listResult[2].city").value(TestUtil.SUGGESTION_3.getCity()))
                .andExpect(jsonPath("$.listResult[3].city").value(TestUtil.SUGGESTION_4.getCity()))
                .andExpect(jsonPath("$.listResult[4].city").value(TestUtil.SUGGESTION_5.getCity()))
                .andExpect(jsonPath("$.listResult[5].city").value(TestUtil.SUGGESTION_6.getCity()))
                .andExpect(jsonPath("$.listResult[6].city").value(TestUtil.SUGGESTION_7.getCity()))
                .andExpect(jsonPath("$.listResult[7].city").value(TestUtil.SUGGESTION_8.getCity()))
                .andExpect(jsonPath("$.listResult[0].street").value(TestUtil.SUGGESTION_1.getStreet()))
                .andExpect(jsonPath("$.listResult[1].street").value(TestUtil.SUGGESTION_2.getStreet()))
                .andExpect(jsonPath("$.listResult[2].street").value(TestUtil.SUGGESTION_3.getStreet()))
                .andExpect(jsonPath("$.listResult[3].street").value(TestUtil.SUGGESTION_4.getStreet()))
                .andExpect(jsonPath("$.listResult[4].street").value(TestUtil.SUGGESTION_5.getStreet()))
                .andExpect(jsonPath("$.listResult[5].street").value(TestUtil.SUGGESTION_6.getStreet()))
                .andExpect(jsonPath("$.listResult[6].street").value(TestUtil.SUGGESTION_7.getStreet()))
                .andExpect(jsonPath("$.listResult[7].street").value(TestUtil.SUGGESTION_8.getStreet()));
    }

    @Test
    void postNotFound() throws Exception {
        Mockito.when(service.findSuggestion(TestUtil.SUGGESTION_BAD_REQUEST))
                .thenReturn(TestUtil.createEmptySuggestionListResponse());

        final MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.post("/suggest/address")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(TestUtil.SUGGESTION_BAD_REQUEST));

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.messages[0].message")
                        .value("No suggestion has been found for request: " + TestUtil.SUGGESTION_BAD_REQUEST.toString()))
                .andExpect(jsonPath("$.messages[0].code")
                        .value(HttpStatus.NOT_FOUND.value()));
    }
}