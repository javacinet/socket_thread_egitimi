package net.javaci.training.socketThread._04_echoServerWithThread;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";

        try (
                Socket socket = new Socket(SERVER_ADDRESS, EchoCommon.SERVER_PORT_NUMBER);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to Echo Server.");

            // Read input from user and send to server
            String userInput;
            while (true) {
                System.out.print("Enter a message (type 'exit' to quit): ");
                userInput = scanner.nextLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                out.println(userInput);

                // Receive and display response from server
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

