package com.alexmau.suggestionapp.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ResponseMessage {

    private String message;

    private Integer code;
}
