package com.example.rent_garadge;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 8080; // Change as needed
    private static final List<PrintWriter> clientWriters = new ArrayList<>();
    private static final List<String> messageHistory = new LinkedList<>();
    private static final int MAX_HISTORY_SIZE = 30;
    private static int clientCount = 0; // Counter to name clients
    private static final String LOG_FILE = "server_log.txt";
    private static final String MESSAGE_HISTORY_FILE = "message_history.txt";

    public static void main(String[] args) {
        System.out.println("Server started on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            loadMessageHistory(); // Load history from file
            while (true) {
                new ClientHandler(serverSocket.accept(), "Client-" + clientCount++).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the message history from the file when server starts
    private static void loadMessageHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(MESSAGE_HISTORY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                messageHistory.add(line);
            }
        } catch (IOException e) {
            System.err.println("No history file found. Starting fresh.");
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket, String clientName) {
            this.socket = socket;
            this.clientName = clientName;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientWriters) {
                    clientWriters.add(out);
                    // Send previous messages to the new client
                    for (String message : messageHistory) {
                        out.println(message);
                    }
                }

                logEvent(clientName + " has joined the chat.");
                broadcastMessage(clientName + " has joined the chat.");

                String message;
                while ((message = in.readLine()) != null) {
                    String formattedMessage = clientName + ": " + message;
                    System.out.println("Received: " + formattedMessage);
                    addMessageToHistory(formattedMessage);
                    broadcastMessage(formattedMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                logEvent(clientName + " has left the chat.");
                broadcastMessage(clientName + " has left the chat.");
            }
        }

        // Broadcast message to all clients
        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }

        // Add message to the history and file
        private void addMessageToHistory(String message) {
            synchronized (messageHistory) {
                if (messageHistory.size() >= MAX_HISTORY_SIZE) {
                    messageHistory.remove(0); // Remove oldest message
                }
                messageHistory.add(message);

                try (FileWriter fw = new FileWriter(MESSAGE_HISTORY_FILE, true)) {
                    fw.write(message + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Log events to the server log file
        private void logEvent(String event) {
            System.out.println(event);
            try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
                fw.write(event + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
