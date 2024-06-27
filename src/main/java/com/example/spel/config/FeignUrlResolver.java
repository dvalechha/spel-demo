package com.example.spel.config;

import org.springframework.stereotype.Component;

@Component
public class FeignUrlResolver {
    private final ThreadLocal<String> urlHolder = new ThreadLocal<>();

    public void setUrl(String url) {
        urlHolder.set(url);
    }

    public String getUrl() {
        return urlHolder.get();
    }

    public void clear() {
        urlHolder.remove();
    }
}
