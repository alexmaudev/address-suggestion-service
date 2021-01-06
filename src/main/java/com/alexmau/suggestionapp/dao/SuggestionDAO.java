package com.alexmau.suggestionapp.dao;

import com.alexmau.suggestionapp.entity.SuggestionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SuggestionDAO extends CrudRepository<SuggestionEntity, Long> {

    SuggestionEntity findByCity(String city);

    List<SuggestionEntity> findByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query("DELETE FROM SuggestionEntity s1 " +
            "WHERE s1.id " +
            "IN (SELECT s2.id FROM SuggestionEntity s2, SuggestionEntity s3 " +
            "WHERE s2.city=s3.city AND s2.street=s3.street GROUP BY s2.id, s2.city, s2.street " +
            "HAVING COUNT(*)<3 OR  s2.dateTime <:expired)")
    void deleteAllSuggestionsOlderThanMonthOrHaveCountLessThanThree(
            @Param("expired") LocalDateTime expired);

    @Query("SELECT s1 FROM SuggestionEntity s1 " +
            "WHERE CASE WHEN s1.region IS NULL AND :region IS NULL THEN TRUE " +
            "WHEN s1.region IS NOT NULL AND :region!='NULL' THEN " +
            "CASE WHEN ((LOWER(s1.region) LIKE(LOWER(CONCAT('%',:region,'%'))))) THEN TRUE ELSE FAlSE END " +
            "ELSE FALSE END=TRUE " +
            "AND CASE WHEN s1.city IS NULL AND :city IS NULL THEN TRUE " +
            "WHEN s1.city IS NOT NULL AND :city!='NULL' THEN " +
            "CASE WHEN((LOWER(s1.city) LIKE(LOWER(CONCAT('%',:city,'%'))))) THEN TRUE ELSE FAlSE END " +
            "ELSE FALSE END=TRUE " +
            "AND CASE WHEN s1.settlement IS NULL AND :settlement IS NULL THEN TRUE " +
            "WHEN s1.settlement IS NOT NULL AND :settlement!='NULL' THEN " +
            "CASE WHEN(LOWER(s1.settlement) LIKE(LOWER(CONCAT('%',:settlement,'%')))) THEN TRUE ELSE FALSE END " +
            "ELSE FALSE END=TRUE " +
            "AND CASE WHEN s1.street IS NULL AND :street IS NULL THEN TRUE " +
            "WHEN s1.street IS NOT NULL AND :street!='NULL' THEN " +
            "CASE WHEN(LOWER(s1.street) LIKE(LOWER(CONCAT('%',:street,'%')))) THEN TRUE ELSE FALSE END " +
            "ELSE FALSE END=TRUE")
    List<SuggestionEntity> findAllSuggestionByRegionAndCityAndSettlemntAndStreet(
            @Param("region") String region, @Param("city") String city,
            @Param("settlement") String settlement, @Param("street") String street);
}