package io.rbetik12.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {
    private final CommandType commandType;
    private final Map<String, String> cookies;
    private final Object body;

    public Request(CommandType commandType, Object body) {
        this.commandType = commandType;
        this.body = body;

        cookies = new HashMap<>();
    }

    public void AddCookie(String key, String value) {
        cookies.put(key, value);
    }

    public String GetCookie(String key) {
        return cookies.get(key);
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return commandType.toString() + " " + cookies.toString() + " " + body.toString();
    }
}
