package com.trueman.attractions.repositories;

import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностями Attraction.
 * Этот интерфейс расширяет JpaRepository, предоставляя стандартные методы
 * для выполнения операций CRUD над объектами класса Attraction в базе данных.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long>, JpaSpecificationExecutor<Attraction> {
    /**
     * Запрос на сортировку списка по полю name.
     */
    @Query("SELECT a FROM Attraction a ORDER BY a.name")
    List<Attraction> findAllSortByName(Specification<Attraction> specification);
    /**
     * Запрос на фильтрацию списка по полю тип достопримечательности.
     */
    @Query("SELECT a FROM Attraction a WHERE a.typeAttraction = :typeAttraction ORDER BY a.name")
    List<Attraction> findByTypeAttractionSortByName(@Param("typeAttraction") TypeAttraction typeAttraction);

    /**
     * Запрос на вывод списка достопримечательностей конкретного местоположения
     */
    @Query("SELECT a FROM Attraction a WHERE a.locality = :locality ORDER BY a.name")
    List<Attraction> findByLocalityAttraction(@Param("locality") Locality locality);
}
