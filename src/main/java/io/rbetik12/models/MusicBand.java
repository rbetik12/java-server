package io.rbetik12.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class MusicBand implements Comparable, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    private final MusicGenre genre; //Поле может быть null
    private final Label label; //Поле не может быть null
    private User author;

    public MusicBand() {
        name = "Name";
        coordinates = new Coordinates();
        genre = MusicGenre.ROCK;
        label = new Label("Label");
    }

    public MusicBand(String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Label label, User user) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.label = label;
        this.author = user;
    }

    public MusicBand(long id, String name, Coordinates coordinates, int numberOfParticipants, MusicGenre genre, Label label, User user) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.label = label;
        this.author = user;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Label getLabel() {
        return label;
    }

    public User getAuthor() {
        return author;
    }

    public void setCreationDate(ZonedDateTime time) {
        creationDate = time;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " +
                id +
                '\n' +
                "Name: " +
                name +
                '\n' +
                "Coordinates: " +
                coordinates +
                '\n' +
                "Creation date: " +
                creationDate +
                '\n' +
                "Number of participants: " +
                numberOfParticipants +
                '\n' +
                "Genre: " +
                genre.toString() +
                '\n' +
                "Label: " +
                label +
                '\n';

    }

    @Override
    public int compareTo(Object o) {
        MusicBand musicBand = (MusicBand) o;

        int result = name.compareTo(musicBand.name);
        if (result == 0) {
            result = coordinates.compareTo(musicBand.coordinates);
        }

        if (result == 0) {
            result = creationDate.compareTo(musicBand.creationDate);
        }

        if (result == 0) {
            result = numberOfParticipants.compareTo(musicBand.numberOfParticipants);
        }

        if (result == 0) {
            result = genre.compareTo(musicBand.genre);
        }

        if (result == 0) {
            result = label.compareTo(musicBand.label);
        }

        return result;
    }
}
