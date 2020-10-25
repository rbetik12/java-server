package io.rbetik12.multithreading.response;

import io.rbetik12.network.Response;
import io.rbetik12.network.UserAddress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;
import java.util.concurrent.Callable;

public class ResponseSenderTask implements Callable {
    private final Map<String, String > cookies;
    private final UserAddress userAddress;
    private final Object body;

    public ResponseSenderTask(Map<String, String> cookies, UserAddress userAddress) {
        this.cookies = cookies;
        this.userAddress = userAddress;
        this.body = null;
    }

    public ResponseSenderTask(Map<String, String> cookies, UserAddress userAddress, Object body) {
        this.cookies = cookies;
        this.userAddress = userAddress;
        this.body = body;
    }

    @Override
    public Object call() {
        Response response;
        if (body == null)
             response = new Response("Auth");
        else
            response = new Response(body);


        for (Map.Entry<String, String> entry: cookies.entrySet()) {
            response.addCookie(entry.getKey(), entry.getValue());
        }

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        try {
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(response);
            oo.close();
        } catch (IOException e) {
            System.out.println("Can't serialize object: " + e);
            return null;
        }

        byte[] serializedResponse = bStream.toByteArray();

        DatagramPacket dp = new DatagramPacket(serializedResponse, serializedResponse.length, userAddress.getIp(), userAddress.getPort());

        try {
            userAddress.getSocket().send(dp);
        } catch (IOException e) {
            System.out.println("Can't send response to user");
        }
        return null;
    }
}
