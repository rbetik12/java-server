package io.rbetik12.db;

import io.rbetik12.models.*;

import java.sql.*;
import java.time.*;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.PriorityQueue;

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

    public Connection getConnection() {
        return connection;
    }

    public void add(MusicBand e) {
        try {
            PreparedStatement ps = getConnection().prepareStatement("insert into Band (name, x, y, creationDate, numberOfParticipants, label, author_id, genre) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)");

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
            ps.setString(8, e.getGenre().toString());
            ps.execute();
        } catch (SQLException exception) {
            System.out.println("Can't create add query: " + exception);
        }
    }

    private MusicGenre stringToGenre(String genre){
        switch (genre){
            case "ROCK":
                return MusicGenre.ROCK;
            case "HIP_HOP":
                return MusicGenre.HIP_HOP;
            case "PSYCHEDELIC_CLOUD_RAP":
                return MusicGenre.PSYCHEDELIC_CLOUD_RAP;
            case "PUNK_ROCK":
                return MusicGenre.PUNK_ROCK;
            default:return MusicGenre.BLUES;
        }
    }

    public PriorityQueue<MusicBand> getAllBands() {
        PriorityQueue<MusicBand> queue = new PriorityQueue<>();
        try {
            ResultSet rs = DBConnection.getInstance().getConnection().createStatement().executeQuery("select * from Band");
            while (rs.next()) {
                MusicBand e = new MusicBand(
                        rs.getLong("id"),
                        rs.getString("name"),
                        new Coordinates(rs.getDouble("x"), rs.getDouble("y")),
                        rs.getInt("numberOfParticipants"),
                        stringToGenre(rs.getString("genre")),
                        new Label(rs.getString("label")),
                        new User(rs.getLong("author_id"), "", "")
                );
                e.setCreationDate(ZonedDateTime.of(rs.getDate("creationDate").toLocalDate(), LocalTime.now(), ZoneId.of("UTC+03:00")));
                queue.add(e);
            }
        }
        catch (SQLException e) {
            System.out.println("Can't create add query: " + e);
        }
        return queue;
    }

    public long getUserId(User user) {
        try {
            ResultSet rs = DBConnection.getInstance().getConnection().createStatement().executeQuery("select id from User where name= '" + user.getUsername() + "'");
            while (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            System.out.println("Can't create 'get user id' request: " + e);
        }
        return 0;
    }

    public void update(MusicBand e)  {
        try {
            PreparedStatement ps = getConnection().prepareStatement("update Band set name = ?, x = ?, y = ?, creationDate = ?, numberOfParticipants = ?, label = ?, genre = ? where id = " + e.getId());
            ps.setString(1, e.getName());
            ps.setDouble(2, e.getCoordinates().getX());
            ps.setDouble(3, e.getCoordinates().getY());
            ZonedDateTime now = ZonedDateTime.now();
            long offsetMillis = ZoneOffset.from(now).getTotalSeconds() * 1000;
            long isoMillis = now.toInstant().toEpochMilli();
            Date date = new Date(isoMillis + offsetMillis);
            ps.setDate(4, date);
            ps.setInt(5, e.getNumberOfParticipants());
            ps.setString(6, e.getLabel().getName());
            ps.setString(7, e.getGenre().toString());
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void remove(int id, int userId) {
        try {
            getConnection().createStatement().executeUpdate("delete from Band where id = " + id + " and author_id = " + userId);
        } catch (SQLException exception) {
            System.out.println("Can't execute delete request: " + exception);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

}
