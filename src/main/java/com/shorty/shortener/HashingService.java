package com.shorty.shortener;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HashingService {
    // Characters being used to generate unique key
    private static final char[] ALPHABET = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'W', 'X', 'Y', 'Z',
    };
    private static final int ENCODE_LENGTH = ALPHABET.length;

    /**
     * Hashes a URL and encodes using the ALPHABET
     * @param url URL to be shortened
     * @return hashed URL
     */
    public String hashURL(final String url) {
        Integer absoluteHash = Math.abs(url.hashCode());
        final List<Character> characterList = new ArrayList<>();

        while (absoluteHash > 0) {
            characterList.add(ALPHABET[absoluteHash % ENCODE_LENGTH]);
            absoluteHash /= ENCODE_LENGTH;
        }

        Collections.reverse(characterList);
        return characterList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
