package com.shorty.shortener;

public interface URLService {
    String shortURL(String url);
    String getURL(String shortURL);
}
