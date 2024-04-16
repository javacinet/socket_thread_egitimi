package net.javaci.training.socketThread._02_echoServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class EchoServerClientExample2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame serverFrame = new ServerFrame();
                serverFrame.setTitle("Echo Server");
                serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                serverFrame.setVisible(true);

                JFrame clientFrame = new ClientFrame();
                clientFrame.setTitle("Echo Client");
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientFrame.setVisible(true);
            }
        });
    }
}

class ServerFrame extends JFrame {
    private JTextArea logArea;

    public ServerFrame() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
        setSize(300, 200);
        setLocationRelativeTo(null);

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            log("Server started.");
            log("Waiting for a client...");

            Socket clientSocket = serverSocket.accept();
            log("Client connected: " + clientSocket);

            new Thread(new ServerThread(clientSocket)).start();
        } catch (IOException e) {
            log("Error: " + e.getMessage());
        }
    }

    public void log(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                logArea.append(message + "\n");
            }
        });
    }
}

class ServerThread implements Runnable {
    private Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println("Server: " + inputLine);
                if (inputLine.equals("bye"))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientFrame extends JFrame {
    private JTextField inputField;
    private JTextArea logArea;

    public ClientFrame() {
        setLayout(new BorderLayout());

        inputField = new JTextField();
        add(inputField, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
        setSize(300, 200);
        setLocationRelativeTo(null);

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                log("Client: " + message);
                inputField.setText("");

                new Thread(new ClientThread(message)).start();
            }
        });
    }

    public void log(String message) {
        logArea.append(message + "\n");
    }
}

class ClientThread implements Runnable {
    private String message;

    public ClientThread(String message) {
        this.message = message;
    }

    public void run() {
        try {
            Socket socket = new Socket("localhost", 9999);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(message);
            String response = in.readLine();
            System.out.println(response);

            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
