package com.alexmau.suggestionapp;

import com.alexmau.suggestionapp.dto.Suggestion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

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

    public static final String REQUEST = "москва хабар";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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

    public static final List<Suggestion> SUGGESTION_LIST = List.of(SUGGESTION_1, SUGGESTION_2, SUGGESTION_3, SUGGESTION_4,
            SUGGESTION_5, SUGGESTION_6, SUGGESTION_7, SUGGESTION_8);

    public static final List<Long> IDS = SUGGESTION_LIST.stream().map(Suggestion::getId).collect(Collectors.toList());
}
