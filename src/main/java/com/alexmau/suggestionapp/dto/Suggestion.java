package com.alexmau.suggestionapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Suggestion implements Serializable {

    private Long id;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("region")
    private String region;

    @JsonProperty("city")
    private String city;

    @JsonProperty("settlement")
    private String settlement;

    @JsonProperty("street")
    private String street;

    @JsonProperty("house")
    private String house;

    private LocalDateTime dateTime;
}
