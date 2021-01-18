package com.alexmau.suggestionapp.controller;

import com.alexmau.suggestionapp.dto.ListResponse;
import com.alexmau.suggestionapp.dto.ResponseMessage;
import com.alexmau.suggestionapp.dto.StatusType;
import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.mapper.SuggestionMapper;
import com.alexmau.suggestionapp.service.SuggestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class SuggestionController {

    @Autowired
    private SuggestionService service;

    @Autowired
    private SuggestionMapper suggestionMapper;

    @PostMapping(value = "/suggest/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<Suggestion>> findSuggestion(@RequestBody @Valid Suggestion suggestion) {
        final ListResponse<Suggestion> response = service.findSuggestion(suggestion);
        String message;
        if (response.getListResult().isEmpty() && response.getMessages().isEmpty()) {
            response.setStatus(StatusType.ERROR);
            message = "No suggestion has been found for request: " + suggestion.toString();
            response.addMessage(new ResponseMessage(message, HttpStatus.NOT_FOUND.value()));
            log.error(message);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            message = response.getListResult().size() + " suggestions have been found";
            log.info(message);
            response.addMessage(new ResponseMessage(message, HttpStatus.OK.value()));
            response.setStatus(StatusType.SUCCESSFUL);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
