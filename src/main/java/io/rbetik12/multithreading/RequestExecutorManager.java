package io.rbetik12.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestExecutorManager {
    private static RequestExecutorManager instance;
    private ExecutorService service;

    private RequestExecutorManager() {
        service = Executors.newFixedThreadPool(16);
    }


    public <T> void submit(Callable<T> task) {
        service.submit(task);
    }

    public static RequestExecutorManager getManager() {
        if (instance == null)
            instance = new RequestExecutorManager();
        return instance;
    }
}
