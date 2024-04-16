package net.javaci.training.socketThread._05_multiThreadedTicTacToeGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedChatServer extends JFrame {
    private JTextArea chatArea;
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public MultiThreadedChatServer() {
        setTitle("MultiThreaded Chat Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

        try {
            serverSocket = new ServerSocket(12345);
            appendMessage("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                appendMessage("Client connected: " + socket.getInetAddress());

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    appendMessage("Client: " + inputLine);
                    broadcastMessage("Client: " + inputLine);
                }

                in.close();
                out.close();
                socket.close();
                clients.remove(this);
                appendMessage("Client disconnected: " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendMessage(String message) {
            out.println(message);
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MultiThreadedChatServer server = new MultiThreadedChatServer();
            server.setVisible(true);
        });
    }
}
