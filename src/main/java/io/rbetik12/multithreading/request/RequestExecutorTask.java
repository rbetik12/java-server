package io.rbetik12.multithreading.request;

import io.rbetik12.db.DBConnection;
import io.rbetik12.models.MusicBand;
import io.rbetik12.models.MusicCollection;
import io.rbetik12.models.MusicQueue;
import io.rbetik12.models.User;
import io.rbetik12.multithreading.CollectionManager;
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
    private final long userId;

    public RequestExecutorTask(Request request, UserAddress address, User user) {
        this.request = request;
        this.address = address;
        if (this.request.getCookie("UserId") == null) {
            userId = DBConnection.getInstance().getUserId(user);
        } else {
            userId = Long.parseLong(this.request.getCookie("UserId"));
        }
    }

    @Override
    public T call() throws Exception {
        Map<String, String> cookie = new HashMap<>();
        switch (request.getCommandType()) {
            case Auth:
                boolean res = authenticate();
                if (res) {
                    cookie.put("Auth", "yes");
                    cookie.put("UserId", String.valueOf(userId));
                    ResponseSenderManager.getManager().submit(new ResponseSenderTask(cookie, address, CollectionManager.getManager().getCollection()));
                } else {
                    cookie.put("Auth", "no");
                    ResponseSenderManager.getManager().submit(new ResponseSenderTask(cookie, address));
                }
                break;
            case Add:
                cookie.put("Auth", "yes");
                cookie.put("UserId", String.valueOf(userId));
                CollectionManager.getManager().getCollection().add((MusicBand) request.getBody(), new User(userId, "def", "def"));
                System.out.println("Sending response");
                ResponseSenderManager.getManager().submit(new ResponseSenderTask(cookie, address, CollectionManager.getManager().getCollection()));
                System.out.println("Sent response");
                break;
            case UpdateElement:
                cookie.put("Auth", "yes");
                cookie.put("UserId", String.valueOf(userId));
                CollectionManager.getManager().getCollection().update((int)((MusicBand)request.getBody()).getId(), (MusicBand) request.getBody());
                ResponseSenderManager.getManager().submit(new ResponseSenderTask(cookie, address, CollectionManager.getManager().getCollection()));
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
