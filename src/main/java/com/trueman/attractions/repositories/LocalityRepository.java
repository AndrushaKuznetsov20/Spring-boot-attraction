package com.trueman.attractions.repositories;

import com.trueman.attractions.models.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями Locality.
 * Этот интерфейс расширяет JpaRepository, предоставляя стандартные методы
 * для выполнения операций CRUD над объектами класса Locality в базе данных.
 */
@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
}
