package net.javaci.training.socketThread._02_echoServerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class EchoServerClientExample3 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame serverFrame = new ServerFrame3();
                serverFrame.setTitle("Echo Server");
                serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                serverFrame.pack();
                serverFrame.setVisible(true);

                JFrame clientFrame = new ClientFrame3();
                clientFrame.setTitle("Echo Client");
                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                clientFrame.pack();
                clientFrame.setVisible(true);
            }
        });
    }
}

class ServerFrame3 extends JFrame {
    private JTextArea logArea;

    public ServerFrame3() {
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

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

class ServerThread3 implements Runnable {
    private Socket clientSocket;

    public ServerThread3(Socket clientSocket) {
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

class ClientFrame3 extends JFrame {
    private JTextField inputField;
    private JTextArea logArea;

    public ClientFrame3() {
        setLayout(new BorderLayout());

        inputField = new JTextField(20);
        add(inputField, BorderLayout.NORTH);

        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                logArea.append(message + "\n");
            }
        });
    }
}

class ClientThread3 implements Runnable {
    private String message;

    public ClientThread3(String message) {
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
