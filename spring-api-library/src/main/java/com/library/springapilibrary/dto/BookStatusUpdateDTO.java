package com.library.springapilibrary.dto;

/**
 * A Data Transfer Object for sending book purchase status updates via WebSocket.
 * Using a record is a concise way to create an immutable data carrier class.
 *
 * @param bookId The ID of the book being processed.
 * @param status The final status of the operation ('SUCCESS' or 'FAILURE').
 * @param error  An optional error message if the operation failed.
 */
public record BookStatusUpdateDTO(Long bookId, String status, String error) {

    /**
     * An overloaded constructor for the success case, where there is no error message.
     * @param bookId The ID of the book.
     * @param status The status of the operation.
     */
    public BookStatusUpdateDTO(Long bookId, String status) {
        this(bookId, status, null);
    }
}
