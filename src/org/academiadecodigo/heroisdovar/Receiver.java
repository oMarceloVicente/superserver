package org.academiadecodigo.heroisdovar;

import java.io.*;
import java.net.Socket;

public class Receiver implements Runnable{

    private BufferedReader socketIn;
    private PrintWriter systemOut;

    public Receiver(Socket socket) {
        try {

            this.socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.systemOut = new PrintWriter(System.out, true);

        } catch (IOException e) {
            System.out.println("Shit did go wrong in the Receiver!!!");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                systemOut.println(socketIn.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
