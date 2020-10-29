package io.rbetik12.models;

import java.util.List;

public interface MusicCollection {
    void add(MusicBand e, User user);

    void update(int id, MusicBand e);

    void remove(int id, int userId);

    void clear();

    void addIfMin(MusicBand e);

    void removeGreater(MusicBand e, int userId);

    void removeLower(MusicBand e, int userId);

    void minByCreationDate();

    void filterByNumberOfParticipants(int number);

    MusicBand get(int index);

    List<MusicBand> toList();
}
