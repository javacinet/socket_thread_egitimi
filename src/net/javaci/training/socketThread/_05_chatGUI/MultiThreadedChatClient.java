package net.javaci.training.socketThread._05_chatGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MultiThreadedChatClient extends JFrame {
    private JTextField serverAddressField;
    private JTextField portField;
    private JTextField messageField;
    private JTextArea chatArea;
    private Socket socket;
    private PrintWriter out;

    public MultiThreadedChatClient() {
        setTitle("MultiThreaded Chat Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridLayout(2, 2));

        serverAddressField = new JTextField("localhost");
        portField = new JTextField("12345");
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectButtonListener());

        connectionPanel.add(new JLabel("Server Address:"));
        connectionPanel.add(serverAddressField);
        connectionPanel.add(new JLabel("Port:"));
        connectionPanel.add(portField);
        connectionPanel.add(connectButton);

        add(connectionPanel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        messageField.addActionListener(new SendMessageListener());
        messagePanel.add(messageField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendMessageListener());
        messagePanel.add(sendButton, BorderLayout.EAST);

        add(messagePanel, BorderLayout.SOUTH);
    }

    private class ConnectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String serverAddress = serverAddressField.getText();
            int port = Integer.parseInt(portField.getText());

            try {
                socket = new Socket(serverAddress, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                appendMessage("Connected to server.");

                // Start a thread to listen for messages from the server
                new Thread(new ServerListener()).start();
            } catch (IOException ex) {
                appendMessage("Error: Unable to connect to server.");
            }
        }
    }

    private class SendMessageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (out != null) {
                String message = messageField.getText();
                out.println(message);
                appendMessage("Client: " + message);
                messageField.setText("");
            }
        }
    }

    private void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    private class ServerListener implements Runnable {
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    appendMessage(message);
                }
            } catch (IOException e) {
                appendMessage("Error: Connection to server lost.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MultiThreadedChatClient client = new MultiThreadedChatClient();
            client.setVisible(true);
        });
    }
}
