package net.javaci.training.socketThread._01_echoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(EchoCommon.SERVER_PORT_NUMBER)) {
            System.out.println("Echo Server is running on port " + EchoCommon.SERVER_PORT_NUMBER);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                out.println(inputLine); // Echo back to the client
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
