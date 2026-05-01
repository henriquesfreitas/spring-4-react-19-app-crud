package com.library.springapilibrary.repository;

import com.library.springapilibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Book entities.
 *
 * @Repository: A Spring annotation that indicates this is a Data Access Object.
 *              It enables Spring's exception translation mechanism, converting
 *              JPA exceptions into Spring's unified DataAccessException hierarchy.
 *              It also makes the bean eligible for component scanning.
 *
 *              - Jakarta EE Equivalent: @ApplicationScoped or @Stateless. In Jakarta EE,
 *                you would typically create a concrete class (e.g., BookDAO) that
 *                injects an EntityManager. There isn't a direct equivalent to Spring
 *                Data's repository generation, so you'd write the implementation manually.
 *
 * JpaRepository<Book, Long>: By extending this, Spring Data automatically provides
 * implementations for standard CRUD, pagination, and sorting methods.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // No need to implement methods like save(), findById(), findAll(), deleteById(), count().
    // Spring Data provides them automatically.
    // Custom query methods can be defined here if needed by following Spring Data's naming conventions.
}
