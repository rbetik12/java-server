package io.rbetik12.db;

import io.rbetik12.models.MusicBand;
import io.rbetik12.models.User;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class DBConnection {
    private static DBConnection instance;
    private final String url = "jdbc:mysql://localhost:3306/prog?serverTimezone=UTC";
    private final String user = "vitaliy";
    private final String password = "123456";

    private Connection connection;

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Can't create connection: " + e);
        }
    }

    public void add(MusicBand e) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("insert into Band (name, x, y, creationDate, numberOfParticipants, label, author_id) " +
                    "values (?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, e.getName());
            ps.setDouble(2, e.getCoordinates().getX());
            ps.setDouble(3, e.getCoordinates().getY());
            long offsetMillis = ZoneOffset.from(e.getCreationDate()).getTotalSeconds() * 1000;
            long isoMillis = e.getCreationDate().toInstant().toEpochMilli();
            Date date = new Date(isoMillis + offsetMillis);
            ps.setDate(4, date);
            ps.setInt(5, e.getNumberOfParticipants());
            ps.setString(6, e.getLabel().getName());
            ps.setLong(7, e.getAuthor().getId());
            ps.execute();
        } catch (SQLException exception) {
            System.out.println("Can't create add query: " + exception);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public long getUserId(User user) {
        try {
            ResultSet rs = DBConnection.getInstance().getConnection().createStatement().executeQuery("select id from User where name= " + user.getUsername());
            while (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            System.out.println("Can't create 'get user id' request");
        }
        return 0;
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

}
