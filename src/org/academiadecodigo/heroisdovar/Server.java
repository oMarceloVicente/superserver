package org.academiadecodigo.heroisdovar;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ArrayList<Socket> storeSockets;
    private final ExecutorService cachedPool;
    private final ServerSocket serverSocket;




    public Server() {
        try {
            serverSocket = new ServerSocket(8080);
            storeSockets = new ArrayList<>();
            cachedPool = Executors.newCachedThreadPool();
            connectServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectServer() {

                System.out.println("Waiting for connection...");
            while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                cachedPool.submit(new ServerWorker(clientSocket));
                storeSockets.add(clientSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendAll(String string) {
        for (Socket sockets : storeSockets) {
            try {
                PrintWriter outFor = new PrintWriter(sockets.getOutputStream(), true);
                outFor.println(string);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

public class ServerWorker implements Runnable {
    private final BufferedReader in;

    public ServerWorker (Socket socket) {

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receive () {
        try {
            String message = in.readLine();
            return message;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send (String string) {
            sendAll(string);
    }

    @Override
    public void run() {
        while (true) {
            send(receive());
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
