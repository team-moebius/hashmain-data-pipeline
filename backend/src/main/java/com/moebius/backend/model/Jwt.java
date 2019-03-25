package com.moebius.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jwt {
    /**
     * Secret for signing JWT
     */
    private String secret;

    /**
     * Default expiration milliseconds
     */
    private long expirationMillis = 864000000L; // 10 days

    /**
     * Expiration milliseconds for short-lived tokens and cookies
     */
    private int shortLivedMillis = 120000; // Two minutes
}
