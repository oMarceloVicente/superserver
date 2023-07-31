package org.academiadecodigo.heroisdovar;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private int port = 8080;
    private final Socket socket;
    private final ExecutorService fixedPool;
    private String name;
    public String getName() {
        return name;
    }

    public Client() {
        try {
            this.socket = new Socket("localhost", port);
            System.out.println("Connected");
            setName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fixedPool = Executors.newFixedThreadPool(2);
    }

    private void setName() {
        try {
            System.out.println("Choose your name: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            name = br.readLine();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startConnection () {
        fixedPool.submit(new Receiver(socket));
        fixedPool.submit(new Sender(socket, this));
    }
}
