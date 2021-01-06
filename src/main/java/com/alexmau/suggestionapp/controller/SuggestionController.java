package com.alexmau.suggestionapp.controller;

import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.service.SuggestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class SuggestionController {

    @Autowired
    private SuggestionService service;

    @PostMapping(value = "/suggest/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Suggestion> findSuggestion(@RequestBody String request) throws ExecutionException, InterruptedException {
        return service.getSuggestionList(request);
    }
}
