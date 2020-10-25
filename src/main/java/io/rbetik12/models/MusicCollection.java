package io.rbetik12.models;

public interface MusicCollection {
    void add(MusicBand e, User user);

    void update(int id, MusicBand e);

    void remove(int id);

    void clear();

    void addIfMin(MusicBand e);

    void removeGreater(MusicBand e);

    void removeLower(MusicBand e);

    void minByCreationDate();

    void filterByNumberOfParticipants(int number);

    MusicBand get(int index);
}
