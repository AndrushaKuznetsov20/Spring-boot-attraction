package com.trueman.attractions.repositories;

import com.trueman.attractions.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий для работы с сущностями User.
 * Этот интерфейс расширяет JpaRepository, предоставляя стандартные методы
 * для выполнения операций CRUD над объектами класса User в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользователя по идентификатору.
     */
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User findByUserId(@Param("userId") Long userId);

    /**
     * Поиск пользователя по имени.
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверка существования пользователя по имени.
     */
    boolean existsByUsername(String username);

    /**
     * Проверка существования пользователя по email.
     */
    boolean existsByEmail(String email);

    /**
     * Проверка существования пользователя по номеру телефона.
     */
    boolean existsByNumber(String number);

    /**
     * Поиск пользователя по имени.
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByNameIsValidAuth(String username);
}
