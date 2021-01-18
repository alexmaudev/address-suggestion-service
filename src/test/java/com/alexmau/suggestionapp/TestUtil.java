package com.alexmau.suggestionapp;

import com.alexmau.suggestionapp.dto.ListResponse;
import com.alexmau.suggestionapp.dto.ResponseMessage;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.entity.SuggestionEntity;
import com.alexmau.suggestionapp.resource.DadataConstants;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private TestUtil() {
    }

    public static final String PARAM_REGION_1 = "Моск";

    public static final String PARAM_CITY_1 = "Москв";

    public static final String PARAM_SETTLEMENT_1 = null;

    public static final String PARAM_STREET_1 = "Хаб";

    public static final String PARAM_REGION_2 = "Мос";

    public static final String PARAM_CITY_2 = "Моск";

    public static final String PARAM_SETTLEMENT_2 = null;

    public static final String PARAM_STREET_2 = "Хабарова";

    public static final String PARAM_REGION_3 = "спб";

    public static final String PARAM_CITY_3 = "спб";

    public static final String PARAM_SETTLEMENT_3 = null;

    public static final String PARAM_STREET_3 = "нев";

    public static final String REQUEST = "москва хабар";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static final Suggestion SUGGESTION_REQUEST =
            new Suggestion(null, null, "москва", "москва", null, "хабар",
                    null, null);

    public static final Suggestion SUGGESTION_BAD_REQUEST =
            new Suggestion(null, null, null, "аааааааа", null, null,
                    null, null);

    public static final Suggestion SUGGESTION_1 =
            new Suggestion(1L, null, "Москва", "Москва", null, "Хабаровская",
                    null, LocalDateTime.parse("2021-01-05T19:32:22.209553", FORMATTER));

    public static final Suggestion SUGGESTION_2 =
            new Suggestion(2L, null, "Москва", "Московский", null, "Хабарова",
                    null, LocalDateTime.parse("2021-01-05T19:32:22.23349", FORMATTER));

    public static final Suggestion SUGGESTION_3 =
            new Suggestion(3L, "107497", "Москва", "Москва", null, "Хабаровская",
                    "1", LocalDateTime.parse("2021-01-05T19:32:22.234489", FORMATTER));

    public static final Suggestion SUGGESTION_4 =
            new Suggestion(4L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "2", LocalDateTime.parse("2021-01-05T19:32:22.235484", FORMATTER));

    public static final Suggestion SUGGESTION_5 =
            new Suggestion(5L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "2A", LocalDateTime.parse("2021-01-05T19:32:22.237479", FORMATTER));

    public static final Suggestion SUGGESTION_6 =
            new Suggestion(6L, "107497", "Москва", "Москва", null, "Хабаровская",
                    "3", LocalDateTime.parse("2021-01-05T19:32:22.238476", FORMATTER));

    public static final Suggestion SUGGESTION_7 =
            new Suggestion(7L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "4", LocalDateTime.parse("2021-01-05T19:32:22.24047", FORMATTER));

    public static final Suggestion SUGGESTION_8 =
            new Suggestion(8L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "4A", LocalDateTime.parse("2021-01-05T19:32:22.242501", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_1 =
            new SuggestionEntity(1L, null, "Москва", "Москва", null, "Хабаровская",
                    null, LocalDateTime.parse("2021-01-05T19:32:22.209553", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_2 =
            new SuggestionEntity(2L, null, "Москва", "Московский", null, "Хабарова",
                    null, LocalDateTime.parse("2021-01-05T19:32:22.23349", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_3 =
            new SuggestionEntity(3L, "107497", "Москва", "Москва", null, "Хабаровская",
                    "1", LocalDateTime.parse("2021-01-05T19:32:22.234489", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_4 =
            new SuggestionEntity(4L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "2", LocalDateTime.parse("2021-01-05T19:32:22.235484", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_5 =
            new SuggestionEntity(5L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "2A", LocalDateTime.parse("2021-01-05T19:32:22.237479", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_6 =
            new SuggestionEntity(6L, "107497", "Москва", "Москва", null, "Хабаровская",
                    "3", LocalDateTime.parse("2021-01-05T19:32:22.238476", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_7 =
            new SuggestionEntity(7L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "4", LocalDateTime.parse("2021-01-05T19:32:22.24047", FORMATTER));

    public static final SuggestionEntity SUGGESTION_ENTITY_8 =
            new SuggestionEntity(8L, "107589", "Москва", "Москва", null, "Хабаровская",
                    "4A", LocalDateTime.parse("2021-01-05T19:32:22.242501", FORMATTER));

    public static final List<Suggestion> SUGGESTION_LIST = List.of(SUGGESTION_1, SUGGESTION_2, SUGGESTION_3,
            SUGGESTION_4, SUGGESTION_5, SUGGESTION_6, SUGGESTION_7, SUGGESTION_8);

    public static final List<SuggestionEntity> SUGGESTION_ENTITY_LIST = List.of(SUGGESTION_ENTITY_1,
            SUGGESTION_ENTITY_2, SUGGESTION_ENTITY_3, SUGGESTION_ENTITY_4, SUGGESTION_ENTITY_5, SUGGESTION_ENTITY_6,
            SUGGESTION_ENTITY_7, SUGGESTION_ENTITY_8);

    public static final List<Long> IDS = SUGGESTION_LIST.stream().map(Suggestion::getId).collect(Collectors.toList());

    public static ListResponse<Suggestion> createSuggestionListResponse() {
        ListResponse<Suggestion> result = new ListResponse<>();
        result.setListResult(SUGGESTION_LIST);
        return result;
    };

    public static ListResponse<Suggestion> createEmptySuggestionListResponse() {
        ListResponse<Suggestion> listResponse = new ListResponse<>();
        List<Suggestion> suggestion = new ArrayList<>();
        List<ResponseMessage> responseList = new ArrayList<>();
        listResponse.setMessages(responseList);
        listResponse.setListResult(suggestion);
        return listResponse;
    };

    public static String createSuggestionQuery () {
        StringBuilder sb = new StringBuilder("{ \"query\": \"");
        sb.append(SUGGESTION_REQUEST.getPostalCode()==null?"":SUGGESTION_REQUEST.getPostalCode() + " ");
        sb.append(SUGGESTION_REQUEST.getRegion()==null?"":SUGGESTION_REQUEST.getRegion() + " ");
        sb.append(SUGGESTION_REQUEST.getCity()==null?"":SUGGESTION_REQUEST.getCity() + " ");
        sb.append(SUGGESTION_REQUEST.getSettlement()==null?"":SUGGESTION_REQUEST.getSettlement() + " ");
        sb.append(SUGGESTION_REQUEST.getStreet()==null?"":SUGGESTION_REQUEST.getStreet() + " ");
        sb.append(SUGGESTION_REQUEST.getHouse()==null?"":SUGGESTION_REQUEST.getHouse() + " ");
        sb.append("\" }");
        return new String(sb);
    }

    public static HttpResponse createResponse() {
        return new HttpResponse() {
            @Override
            public int statusCode() {
                return 200;
            }

            @Override
            public HttpRequest request() {
                return HttpRequest.newBuilder()
                        .uri(URI.create(DadataConstants.API_URL))
                        .timeout(Duration.ofMinutes(2))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", DadataConstants.API_KEY)
                        .POST(HttpRequest.BodyPublishers.ofString(SUGGESTION_1.toString())) //
                        .build();
            }

            @Override
            public Optional<HttpResponse> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public Object body() {
                try {
                    return objectMapper.writeValueAsString(SUGGESTION_LIST);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return HttpClient.Version.HTTP_2;
            }
        };
    }
}
