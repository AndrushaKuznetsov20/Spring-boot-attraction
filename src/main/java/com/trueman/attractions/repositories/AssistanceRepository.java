package com.trueman.attractions.repositories;

import com.trueman.attractions.models.Assistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями Assistance.
 * Этот интерфейс расширяет JpaRepository, предоставляя стандартные методы
 * для выполнения операций CRUD над объектами класса Assistance в базе данных.
 */
@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
}
