package org.academiadecodigo.heroisdovar;

import java.io.*;
import java.net.Socket;

public class Sender implements Runnable{

    private BufferedReader systemIn;
    private PrintWriter socketOut;

    private Client client;

    public Sender(Socket socket, Client client) {
        this.client = client;
        try {
            this.socketOut = new PrintWriter(socket.getOutputStream(), true);
            this.systemIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                socketOut.println(client.getName() + ": " + systemIn.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
