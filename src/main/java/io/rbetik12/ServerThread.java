package io.rbetik12;

import io.rbetik12.network.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerThread extends Thread {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer = new byte[256000000];

    public ServerThread() {
        try {
            socket = new DatagramSocket(7000);
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
            }
            byte[] data = incoming.getData();

            try ( ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));) {
                Request request = (Request) iStream.readObject();
                System.out.println("Server got: " + request);
            } catch (IOException e) {
                System.out.println("Can't read serialized object: " + e);
            } catch (ClassNotFoundException e) {
                System.out.println("Can't found class: " + e);
            }
        }
    }
}
