package io.rbetik12.multithreading;

import io.rbetik12.db.DBConnection;
import io.rbetik12.models.User;
import io.rbetik12.network.Request;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

public class RequestExecutorTask<T> implements Callable<T> {
    private final Request request;

    public RequestExecutorTask(Request request) {
        this.request = request;
    }

    @Override
    public T call() throws Exception {
        switch (request.getCommandType()) {
            case Auth:
                authenticate();
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
