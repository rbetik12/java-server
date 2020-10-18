package io.rbetik12.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response implements Serializable {
    private final Map<String, String> cookies;
    private final Object body;

    public Response(Object body) {
        this.body = body;

        cookies = new HashMap<>();
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }

    public String getCookie(String key) {
        return cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return cookies.toString() + " " + body.toString();
    }
}