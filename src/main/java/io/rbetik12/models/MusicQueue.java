package io.rbetik12.models;

import io.rbetik12.db.DBConnection;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MusicQueue implements MusicCollection, Serializable {
    private PriorityQueue<MusicBand> queue;

    public MusicQueue() {
        queue = new PriorityQueue<>();
    }

    @Override
    public List<MusicBand> toList() {
        return new ArrayList<>(queue);
    }

    @Override
    public void add(MusicBand e, User user) {
        e.setCreationDate(ZonedDateTime.now());
        e.setAuthor(user);
        DBConnection.getInstance().add(e);
        updateQueue();
    }

    @Override
    public void update(int id, MusicBand e) {
        DBConnection.getInstance().update(e);
        updateQueue();
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

    @Override
    public MusicBand get(int index) {
        int i = 0;
        for (MusicBand e: queue) {
            if (i == index) return e;
            i += 1;
        }
        return null;
    }

    public void updateQueue() {
        queue = DBConnection.getInstance().getAllBands();
    }
}
