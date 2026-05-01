package com.library.springapilibrary.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents the Book entity in the database.
 *
 * @Entity: Marks this class as a JPA entity, making it eligible for persistence.
 *          This is a standard JPA annotation.
 *          - Jakarta EE Equivalent: Same - jakarta.persistence.Entity
 *
 * @Table: Specifies the details of the table that this entity maps to.
 *         This is a standard JPA annotation.
 *         - Jakarta EE Equivalent: Same - jakarta.persistence.Table
 */
@Entity
@Table(name = "books")
public class Book {

    /**
     * The unique identifier for the book.
     * @Id marks this field as the primary key.
     * @GeneratedValue specifies how the primary key is generated.
     * GenerationType.IDENTITY indicates that the persistence provider (e.g., Hibernate)
     * should delegate the key generation to the database's auto-increment column.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the book.
     * @Column maps this field to a database column.
     * - nullable = false: This column cannot be null.
     * - length = 100: Sets the maximum length of the string.
     */
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    /**
     * The author of the book.
     */
    @Column(name = "author", nullable = false)
    private String author;

    /**
     * The International Standard Book Number.
     */
    @Column(name = "isbn")
    private String isbn;

    /**
     * The price of the book.
     */
    @Column(name = "price", nullable = false)
    private Double price;

    /**
     * The current status of the book (e.g., AVAILABLE, SOLD).
     * @Enumerated(EnumType.STRING) tells JPA to persist the enum's name as a string
     * in the database, which is more readable than persisting its ordinal value.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    /**
     * The timestamp when the book record was created.
     * @CreationTimestamp is a Hibernate annotation that automatically sets this field
     * to the current timestamp when the entity is first persisted.
     * - updatable = false: Ensures this timestamp is never changed on updates.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the book record was last updated.
     * @UpdateTimestamp is a Hibernate annotation that automatically updates this field
     * to the current timestamp whenever the entity is updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * A version number for optimistic locking.
     * @Version tells JPA to use this field to detect concurrent modifications,
     * preventing lost updates. The value is automatically incremented on each update.
     */
    @Version
    private Long version;

    // Default constructor required by JPA.
    public Book() {
    }

    // Getters and Setters for all fields.
    // These are necessary for the persistence provider and other frameworks
    // to access and manipulate the entity's state.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
