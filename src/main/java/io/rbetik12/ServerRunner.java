package io.rbetik12;

public class ServerRunner {
    public static void main(String[] args) {
        Thread serverThread = new ServerThread();
        serverThread.start();
    }
}
