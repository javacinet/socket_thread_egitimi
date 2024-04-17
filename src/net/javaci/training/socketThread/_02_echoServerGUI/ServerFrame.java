package net.javaci.training.socketThread._02_echoServerGUI;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ServerFrame extends JFrame {
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public static void main(String[] args) {
        JFrame serverFrame = new ServerFrame();
        serverFrame.setTitle("Echo Server");
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.setVisible(true);
        // serverFrame.show(true);
    }

    public ServerFrame() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        try {
            serverSocket = new ServerSocket(9999);
            log("Server started.");
            log("Waiting for a client...");

            clientSocket = serverSocket.accept();
            log("Client connected: " + clientSocket);

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                log("Client: " + inputLine);
                out.println("Server: " + inputLine);
                if (inputLine.equals("bye"))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            log("Error: " + e.getMessage());
        }
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }
}
