package io.rbetik12.models;

import io.rbetik12.db.DBConnection;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.PriorityQueue;

public class MusicQueue implements MusicCollection {
    private final PriorityQueue<MusicBand> queue;

    public MusicQueue() {
        queue = new PriorityQueue<>();
    }

    @Override
    public void add(MusicBand e, User user) {
        e.setCreationDate(ZonedDateTime.now());
        e.setAuthor(user);
        System.out.println("Adding new band: " + e);
        DBConnection.getInstance().add(e);
    }

    @Override
    public void update(int id, MusicBand e) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void addIfMin(MusicBand e) {

    }

    @Override
    public void removeGreater(MusicBand e) {

    }

    @Override
    public void removeLower(MusicBand e) {

    }

    @Override
    public void minByCreationDate() {

    }

    @Override
    public void filterByNumberOfParticipants(int number) {

    }

    public void updateQueue() {

    }
}
