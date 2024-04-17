package net.javaci.training.socketThread._02_ServerGUI._2_echo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientFrame extends JFrame {
    private JTextField inputField;
    private JTextArea logArea;

    public static void main(String[] args) {

        JFrame clientFrame = new ClientFrame();
        clientFrame.setTitle("Echo Client");
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public ClientFrame() {
        setLayout(new BorderLayout());

        inputField = new JTextField();
        add(inputField, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);


        setSize(300, 200);
        setVisible(true);

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
