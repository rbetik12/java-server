package io.rbetik12.multithreading;

import java.util.concurrent.Callable;

public class RequestExecutorTask implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("Task");
        return "Task";
    }
}
