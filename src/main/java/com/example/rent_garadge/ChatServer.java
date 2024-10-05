package com.example.rent_garadge;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ChatServer {
    private static int clientCount = 0;
    private static Hashtable<Socket, String> clients = new Hashtable<>();
    private static List<String> messageHistory = new ArrayList<>();
    private static final String LOG_FILE = "server_log.txt";

//    public static void main(String[] args) {
//        try (ServerSocket serverSocket = new ServerSocket(8080)) {
//            logEvent("Server started on port 8080");
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                String clientName = "Client-" + clientCount++;
//                clients.put(clientSocket, clientName);
//
//                logEvent(clientName + " connected");
//
//                new ClientHandler(clientSocket, clientName).start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void logEvent(String event) {
        System.out.println(event);
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(event + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all clients
    private static synchronized void broadcastMessage(String message, String sender) {
        messageHistory.add(sender + ": " + message);
        for (Socket clientSocket : clients.keySet()) {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(sender + ": " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Send message history to a new client
    private static void sendHistory(Socket clientSocket) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Message history:");
            for (String message : messageHistory) {
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to handle each client connection
    static class ClientHandler extends Thread {
        private Socket clientSocket;
        private String clientName;

        public ClientHandler(Socket socket, String name) {
            this.clientSocket = socket;
            this.clientName = name;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                sendHistory(clientSocket); // Send message history to new client
                String input;
                while ((input = in.readLine()) != null) {
                    logEvent(clientName + " sent: " + input);
                    broadcastMessage(input, clientName); // Broadcast message to all clients
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logEvent(clientName + " disconnected");
                clients.remove(clientSocket);
            }
        }
    }
}
