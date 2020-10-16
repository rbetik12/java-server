package io.rbetik12.multithreading;

import io.rbetik12.network.Request;

import java.util.concurrent.Callable;

public class RequestExecutorTask<T> implements Callable<T> {
    private final Request request;

    public RequestExecutorTask(Request request) {
        this.request = request;
    }

    @Override
    public T call() throws Exception {
        System.out.println(request.getCommandType());
        return null;
    }
}
