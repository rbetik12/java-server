package io.rbetik12.multithreading.request;

import io.rbetik12.models.User;
import io.rbetik12.network.Request;
import io.rbetik12.network.UserAddress;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.RecursiveAction;

public class RequestHandlerTask extends RecursiveAction {

    private final UserAddress userAddress;
    private final byte[] data;
    private boolean isExecuted = false;
    private final Object lock = new Object();

    public RequestHandlerTask(byte[] data, UserAddress userAddress) {
        this.userAddress = userAddress;
        this.data = data;
        this.exec();
    }

    @Override
    protected void compute() {
        synchronized (lock) {
            if (isExecuted) return;
            isExecuted = true;
        }
        try (ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));) {
            Request request = (Request) iStream.readObject();
            executeRequest(request);
        } catch (IOException e) {
            System.out.println("Can't read serialized object: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't found class: " + e);
        }
    }

    private void executeRequest(Request request) {
        try {
            RequestExecutorManager.getManager().submit(new RequestExecutorTask<>(request, userAddress, (User) request.getBody()));
        }
        catch (ClassCastException e) {
            RequestExecutorManager.getManager().submit(new RequestExecutorTask<>(request, userAddress, null));
        }
    }
}
