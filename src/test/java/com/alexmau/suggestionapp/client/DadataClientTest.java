package com.alexmau.suggestionapp.client;

import com.alexmau.suggestionapp.TestUtil;
import com.alexmau.suggestionapp.resource.DadataConstants;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DadataClientTest {

    @InjectMocks
    private DadataClient client;

    @Rule
    public WireMockRule wm = new WireMockRule(options().port(8089));

    @Test
    public void clientTest() throws Exception {
        WireMock.configureFor("localhost", wm.port());
        stubFor(post(urlEqualTo(DadataConstants.API_URL))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        HttpResponse response = client.sendRequest(TestUtil.createSuggestionQuery());

        Assertions.assertEquals(response.statusCode(), HttpStatus.OK.value());
    }
}
