package io.rbetik12.multithreading;

import io.rbetik12.models.MusicCollection;
import io.rbetik12.models.MusicQueue;
import io.rbetik12.multithreading.request.RequestExecutorManager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CollectionManager {
    private static CollectionManager instance;
    private final MusicCollection collection;

    private CollectionManager() {
        collection = new MusicQueue();
    }

    public MusicCollection getCollection() {
        return collection;
    }

    public static CollectionManager getManager() {
        if (instance == null)
            instance = new CollectionManager();
        return instance;
    }
}
