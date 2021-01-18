package com.alexmau.suggestionapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Builder
@Table(name = SuggestionEntity.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestionEntity {

    protected static final String TABLE_NAME = "suggestion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
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

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime dateTime;
}
