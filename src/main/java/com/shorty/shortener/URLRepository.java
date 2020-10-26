package com.shorty.shortener;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class URLRepository {
    // Mocking a DB implementation
    private static final Map<String, String> mockDB = new HashMap<>();

    /**
     * Saves an URL to the map
     * @param urlID the shortened URL
     * @param url the real URL
     */
    public void saveURL(final String urlID, final String url) {
        mockDB.put(urlID, url);
    }

    /**
     * Fetches an URL from the map
     * @param urlID the shortened URL
     * @return the URL or empty if not found
     */
    public String fetchURL(final String urlID) {
        return mockDB.getOrDefault(urlID, "");
    }
}
