package io.rbetik12.multithreading.response;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResponseSenderManager {
    private static ResponseSenderManager instance;
    private final ExecutorService service;

    private ResponseSenderManager() {
        service = Executors.newCachedThreadPool();
    }

    public <T> Future<T> submit(Callable<T> task) {
        return service.submit(task);
    }

    public static ResponseSenderManager getManager() {
        if (instance == null)
            instance = new ResponseSenderManager();
        return instance;
    }
}
