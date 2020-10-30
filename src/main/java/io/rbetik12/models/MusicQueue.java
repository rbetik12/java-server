package io.rbetik12.models;

import io.rbetik12.db.DBConnection;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class MusicQueue implements MusicCollection, Serializable {
    private PriorityQueue<MusicBand> queue;
    private ReentrantLock lock;

    public MusicQueue() {
        queue = new PriorityQueue<>();
        lock = new ReentrantLock();
        updateQueue();
    }

    @Override
    public List<MusicBand> toList() {
        return new ArrayList<>(queue);
    }

    @Override
    public void add(MusicBand e, User user) {
        lock.lock();
        e.setCreationDate(ZonedDateTime.now());
        e.setAuthor(user);
        DBConnection.getInstance().add(e);
        updateQueue();
        lock.unlock();
    }

    @Override
    public void update(int id, MusicBand e) {
        lock.lock();
        DBConnection.getInstance().update(e);
        updateQueue();
        lock.unlock();
    }

    @Override
    public void remove(int id, int userId) {
        lock.lock();
        DBConnection.getInstance().remove(id, userId);
        updateQueue();
        lock.unlock();
    }

    @Override
    public void clear() {

    }

    @Override
    public void addIfMin(MusicBand e) {
        lock.lock();
        if (queue.peek() != null) {
            if (queue.peek().compareTo(e) < 0) {
                DBConnection.getInstance().add(e);
            }
        }
        else {
            DBConnection.getInstance().add(e);
        }
        updateQueue();
        lock.unlock();
    }

    @Override
    public void removeGreater(MusicBand e, int userId) {
        lock.lock();
        for (MusicBand el: queue) {
            if (el.compareTo(e) > 0) {
                DBConnection.getInstance().remove((int) el.getId(), userId);
            }
        }
        updateQueue();
        lock.unlock();
    }

    @Override
    public void removeLower(MusicBand e, int userId) {
        lock.lock();
        for (MusicBand el: queue) {
            if (el.compareTo(e) < 0) {
                DBConnection.getInstance().remove((int) el.getId(), userId);
            }
        }
        updateQueue();
        lock.unlock();
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
        System.out.println("Locked here");
        System.out.println(lock);
        lock.lock();
        queue = DBConnection.getInstance().getAllBands();
        System.out.println("Waiting for unlock");
        lock.unlock();
        System.out.println("Unlocked here");
    }
}
