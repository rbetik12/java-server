package io.rbetik12.multithreading;

import io.rbetik12.network.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class RequestHandlerTask extends RecursiveAction {

    private final DatagramPacket requestPacket;

    public RequestHandlerTask(DatagramPacket packet) {
        requestPacket = packet;
    }

    @Override
    protected void compute() {
        byte[] data = requestPacket.getData();

        try (ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));) {
            Request request = (Request) iStream.readObject();
            System.out.println("Server got: " + request);
        } catch (IOException e) {
            System.out.println("Can't read serialized object: " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Can't found class: " + e);
        }
    }
}
