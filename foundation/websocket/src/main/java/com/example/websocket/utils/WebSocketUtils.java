package com.example.websocket.utils;

import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class WebSocketUtils {

    public static Map<String, String> getParameterMap(URI url) {
        Map<String, String> maps = new LinkedHashMap<>();

        if (url == null) {
            return maps;
        }

        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            maps.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }

        return maps;
    }
}
