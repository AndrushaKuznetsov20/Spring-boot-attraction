package com.trueman.attractions.repositories;

import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    @Query("SELECT a FROM Attraction a ORDER BY a.name")
    List<Attraction> findAllSortByName();

    @Query("SELECT a FROM Attraction a WHERE a.typeAttraction = :typeAttraction ORDER BY a.name")
    List<Attraction> findByTypeAttractionSortByName(@Param("typeAttraction") TypeAttraction typeAttraction);

    @Query("SELECT a FROM Attraction a WHERE a.locality = :locality")
    List<Attraction> findByLocalityAttraction(@Param("locality") Locality locality);
}
