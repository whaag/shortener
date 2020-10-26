package com.shorty.shortener;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/")
public class URLController {

    @Autowired
    private URLService urlService;

    @PostMapping("url")
    public ResponseEntity<String> shortURL(@RequestBody String url) {
        final String shortenedURL = urlService.shortURL(url);

        if (shortenedURL.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create short URL");
        }

        if (validateURL(url)) {
            return ResponseEntity.ok(shortenedURL);
        } else {
            return ResponseEntity.badRequest().body("Invalid URL");
        }
    }

    @GetMapping("url")
    public ResponseEntity<String> getURL(@RequestParam String shortenedURL) {
        final String url = urlService.getURL(shortenedURL);

        if (url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(url);
    }

    private Boolean validateURL(final String url) {
        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(url);
    }
}
