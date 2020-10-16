package io.rbetik12;

import io.rbetik12.multithreading.RequestHandlerTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ForkJoinPool;

public class ServerThread extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer = new byte[256000];
    private ForkJoinPool forkJoinPool;

    public ServerThread() {
        try {
            socket = new DatagramSocket(7000);
            forkJoinPool = ForkJoinPool.commonPool();
        } catch (SocketException e) {
            System.out.println("Can't create socket: " + socket);
        }
    }

    public void run() {
        running = true;
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

        while (running) {
            try {
                socket.receive(incoming);
            } catch (IOException e) {
                System.out.println("Cannot receive message from client: " + e);
                continue;
            }

            forkJoinPool.execute(new RequestHandlerTask(incoming));
        }
    }
}
