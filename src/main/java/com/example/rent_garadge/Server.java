package com.example.rent_garadge;


import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static List<String> messageHistory = new ArrayList<>(); // Store chat history

    private static File logFile = new File("server_events.log"); // Log file

    public static void main(String[] args) throws Exception {
        System.out.println("Chat server started...");
        logEvent("Server started");

        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            while (true) {
                // Accept new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                logEvent("New client connected: " + clientSocket);

                // Start a new thread for the client
                new ClientHandler(clientSocket).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    // Logs server events to a file
    private static synchronized void logEvent(String event) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(new Date() + ": " + event + "\n");
        } catch (IOException e) {
            System.out.println("Error logging event: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String clientName;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Create input and output streams for this client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request client's name
                out.println("Enter your name:");
                clientName = in.readLine();

                // Welcome message and send chat history
                out.println("Welcome " + clientName + "! Here are the previous messages:");

                synchronized (messageHistory) {
                    for (String message : messageHistory) {
                        out.println(message);  // Send chat history to the new client
                    }
                }

                // Notify others and add this writer to the list of clientWriters
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }
                broadcastMessage(clientName + " has joined the chat");

                // Handle messages from this client
                String message;
                while ((message = in.readLine()) != null) {
                    String fullMessage = clientName + ": " + message;
                    logEvent(fullMessage);
                    synchronized (messageHistory) {
                        messageHistory.add(fullMessage);  // Store message in history
                    }
                    broadcastMessage(fullMessage);  // Send message to all clients
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
                logEvent("Error handling client: " + e.getMessage());
            } finally {
                // Client disconnected, clean up
                if (out != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                }
                if (clientName != null) {
                    System.out.println(clientName + " has left the chat.");
                    logEvent(clientName + " has left the chat.");
                    broadcastMessage(clientName + " has left the chat.");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        // Sends a message to all connected clients
        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}

