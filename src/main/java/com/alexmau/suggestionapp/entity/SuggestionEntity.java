package com.alexmau.suggestionapp.entity;

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
public class SuggestionEntity {

    protected static final String TABLE_NAME = "suggestion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;

    private String postalCode;

    private String region;

    private String city;

    private String settlement;

    private String street;

    private String house;

    @EqualsAndHashCode.Exclude
    @CreationTimestamp
    private LocalDateTime dateTime;
}
