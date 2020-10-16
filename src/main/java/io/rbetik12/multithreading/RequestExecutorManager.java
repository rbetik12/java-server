package io.rbetik12.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestExecutorManager {
    private static RequestExecutorManager instance;
    private final ExecutorService service;

    private RequestExecutorManager() {
        service = Executors.newFixedThreadPool(16);
    }


    public <T> Future<T> submit(Callable<T> task) {
        return service.submit(task);
    }

    public static RequestExecutorManager getManager() {
        if (instance == null)
            instance = new RequestExecutorManager();
        return instance;
    }
}
