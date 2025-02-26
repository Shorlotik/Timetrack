package org.example.timetrack.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = new HashSet<>();

    // Adds a token to the blacklist
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    // Checks if the token is blacklisted
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
