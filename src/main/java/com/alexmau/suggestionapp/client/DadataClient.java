package com.alexmau.suggestionapp.client;

import com.alexmau.suggestionapp.resource.DadataConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Getter
@Slf4j
public final class DadataClient {

    private final HttpClient client;

    public DadataClient() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }

    public HttpResponse sendRequest(String body) throws ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DadataConstants.API_URL))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", DadataConstants.API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(body)) //
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
    }
}
