package com.alexmau.suggestionapp.mapper;

import com.alexmau.suggestionapp.dto.Suggestion;
import com.alexmau.suggestionapp.entity.SuggestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",   unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SuggestionMapper {

    Suggestion mapSuggestionEntityToSuggestion(SuggestionEntity suggestionEntity);

    SuggestionEntity mapDtoToEntity(Suggestion suggestion);
}
