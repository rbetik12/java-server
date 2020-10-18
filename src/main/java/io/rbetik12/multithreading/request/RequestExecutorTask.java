package io.rbetik12.multithreading.request;

import io.rbetik12.db.DBConnection;
import io.rbetik12.models.User;
import io.rbetik12.multithreading.response.ResponseSenderManager;
import io.rbetik12.multithreading.response.ResponseSenderTask;
import io.rbetik12.network.Request;
import io.rbetik12.network.UserAddress;

import java.net.DatagramPacket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class RequestExecutorTask<T> implements Callable<T> {
    private final Request request;
    private final UserAddress address;

    public RequestExecutorTask(Request request, UserAddress address) {
        this.request = request;
        this.address = address;
    }

    @Override
    public T call() throws Exception {
        Map<String, String> cookie = new HashMap<>();
        switch (request.getCommandType()) {
            case Auth:
                boolean res = authenticate();
                if (res) {
                    cookie.put("Auth", "yes");
                    ResponseSenderManager.getManager().submit(new ResponseSenderTask(cookie, address));
                }
                else {
                    cookie.put("Auth", "no");
                    ResponseSenderManager.getManager().submit(new ResponseSenderTask(cookie, address));
                }
                break;
        }
        return null;
    }

    private boolean authenticate() {
        User user = (User) request.getBody();
        user.setPassword(hashPassword(user.getPassword()));

        try {
            ResultSet resultSet = DBConnection
                    .getInstance()
                    .getConnection()
                    .createStatement()
                    .executeQuery("select * from User where name = '" + user.getUsername() + "' and password = '" + user.getPassword() + "'");
            while (resultSet.next()) {
                System.out.println("User exists!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Can't create db request: " + e);
        }
        System.out.println("User doesn't exists!");
        return false;
    }

    private String hashPassword(String password) {
        return password;
    }
}
