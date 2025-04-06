package network;

import java.io.*;
import java.net.*;

public class Server {

    private static final int PORT = 12345;  // The port where the server listens for connections
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Server() {
        try {
            // Start the server and listen for a client connection
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for client to connect...");
            
            // Accept the client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Set up input and output streams
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Start the game communication
            startGame();
            
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to handle the game logic between server and client
    private void startGame() {
        try {
        	// message information communication
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client says: " + message);
                out.println("Server: " + message);
                if (message.equals("endGame")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error in game communication: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
