package com.library.springapilibrary.dto;

/**
 * A DTO representing the user's login request.
 * Using a record for a simple, immutable data carrier.
 *
 * @param username The user's username.
 * @param password The user's password.
 */
public record AuthRequest(String username, String password) {
}
