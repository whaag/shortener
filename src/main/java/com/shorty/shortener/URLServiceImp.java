package com.shorty.shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLServiceImp implements URLService{

    @Autowired
    private URLRepository urlRepository;

    @Autowired
    private HashingService hashingService;

    /**
     * Hashes an URL and saves in the DB
     * @param url the url to be hashed
     * @return the shortened version of the URL
     */
    @Override
    public String shortURL(String url) {
        final String shortenedURL = shortUniqueURL(url);
        if (!shortenedURL.isEmpty()) {
            urlRepository.saveURL(shortenedURL, url);
        }

        return shortenedURL;
    }

    /**
     * Shorts the URL whilst trying to avoid collisions for this POC the app is going to append a letter at the
     * end of the URL to get a different hash until it gets an unique one, or abort after 100 times
     * @param url the url
     * @return shortened version of the url or empty if not possible
     */
    private String shortUniqueURL(String url) {
        Integer counter = 100;
        final String originalURL = url;
        while (counter > 0) {
            String shortenedURL = hashingService.hashURL(url);
            String savedURL = urlRepository.fetchURL(shortenedURL);

            if (savedURL.isEmpty() || savedURL.equals(originalURL)) {
                return shortenedURL;
            }

            url += "a";
            counter--;
        }

        return "";
    }

    /**
     * Fetches URL from DB
     * @param shortURL shortened url
     * @return saved url from DB
     */
    @Override
    public String getURL(String shortURL) {
        return urlRepository.fetchURL(shortURL);
    }
}
