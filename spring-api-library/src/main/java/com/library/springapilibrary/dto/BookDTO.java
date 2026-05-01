package com.library.springapilibrary.dto;

import com.library.springapilibrary.entity.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for conveying book information between the
 * client (e.g., React frontend) and the server (Spring Boot application).
 *
 * Using a DTO prevents exposing the database entity model directly
 * to the API layer, allowing for changes in the database without immediately
 * affecting the API structure. It also encapsulates validation rules specifically
 * for incoming requests.
 */
public class BookDTO {

    // Unique identifier of the book. Nullable because a new book being created won't have an ID yet.
    private Long id;

    /**
     * @NotBlank: Validation annotation ensuring the string is not null and its trimmed length is greater than zero.
     * @Size: Validation annotation ensuring the string's length falls within a specific range.
     * These validations run automatically when a request body is mapped to this DTO and verified.
     */
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    /**
     * @NotNull: Validation annotation ensuring the value is not null. Use this for primitive wrappers.
     * @Min: Validation annotation ensuring the numeric value is greater than or equal to the specified minimum.
     */
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    /**
     * @Pattern: Validation annotation checking if the string matches a regular expression.
     * In this case, it ensures the ISBN is exactly 13 digits long.
     */
    @Pattern(regexp = "\\d{13}", message = "ISBN must be exactly 13 digits")
    private String isbn;

    // The status of the book, defaulting to AVAILABLE for new entries.
    private BookStatus status = BookStatus.AVAILABLE;

    // Getters and Setters

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
