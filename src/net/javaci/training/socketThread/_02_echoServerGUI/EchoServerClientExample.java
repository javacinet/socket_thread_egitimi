package net.javaci.training.socketThread._02_echoServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class EchoServerClientExample {
    public static void main(String[] args) {
        JFrame serverFrame = new ServerFrame2();
        serverFrame.setTitle("Echo Server");
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.setVisible(true);

        JFrame clientFrame = new ClientFrame2();
        clientFrame.setTitle("Echo Client");
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setVisible(true);
    }
}

class ServerFrame2 extends JFrame {
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerFrame2() {
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

class ClientFrame2 extends JFrame {
    private JTextField inputField;
    private JTextArea logArea;

    public ClientFrame2() {
        setLayout(new BorderLayout());

        inputField = new JTextField();
        add(inputField, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                log("Client: " + message);
                inputField.setText("");

                try {
                    Socket socket = new Socket("localhost", 9999);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    out.println(message);
                    String response = in.readLine();
                    log(response);

                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException ex) {
                    log("Error: " + ex.getMessage());
                }
            }
        });
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }
}
