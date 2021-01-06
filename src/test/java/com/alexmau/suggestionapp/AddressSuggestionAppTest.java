package com.alexmau.suggestionapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.alexmau.suggestionapp.AddressSuggestionAppTest.TEST_PROPERTIES;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = TEST_PROPERTIES)
class AddressSuggestionAppTest {

    protected static final String TEST_PROPERTIES =
            "spring.main.allow-bean-definition-overriding=true";

    @Test
    void contextLoads() {
        assertTrue(true);
    }
}
