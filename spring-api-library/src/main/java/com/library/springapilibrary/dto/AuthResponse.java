package com.library.springapilibrary.dto;

/**
 * A DTO representing the response to a successful authentication request.
 *
 * @param token The generated JWT.
 */
public record AuthResponse(String token) {
}
