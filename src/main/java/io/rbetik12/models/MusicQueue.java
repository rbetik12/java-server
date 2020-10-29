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
        updateQueue();
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
    public void remove(int id, int userId) {
        DBConnection.getInstance().remove(id, userId);
        updateQueue();
    }

    @Override
    public void clear() {

    }

    @Override
    public void addIfMin(MusicBand e) {
        if (queue.peek() != null) {
            if (queue.peek().compareTo(e) < 0) {
                DBConnection.getInstance().add(e);
            }
        }
        else {
            DBConnection.getInstance().add(e);
        }
        updateQueue();
    }

    @Override
    public void removeGreater(MusicBand e, int userId) {
    }

    @Override
    public void removeLower(MusicBand e, int userId) {
        for (MusicBand el: queue) {
            if (el.compareTo(e) < 0) {
                DBConnection.getInstance().remove((int) el.getId(), userId);
            }
        }
        updateQueue();
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
