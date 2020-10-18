package io.rbetik12.network;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UserAddress {
    private final InetAddress ip;
    private final int port;
    private DatagramSocket socket;

    public UserAddress(InetAddress ip, int port, DatagramSocket socket) {
        this.ip = ip;
        this.port = port;
        this.socket = socket;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
