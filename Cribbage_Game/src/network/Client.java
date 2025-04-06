package network;

import java.io.*;
import java.net.*;

public class Client {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to the server.");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start game
            startGame();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server says: " + message);

                /** Handle messages from the server - Example
                if (message.startsWith("startGame")) {
                    // Initialize game
                    System.out.println("Game started between: " + message.split(":")[1]);
                } else if (message.startsWith("scoreUpdate")) {
                    // Update the score
                    System.out.println("Score Update: " + message.split(":")[1]);
                } else if (message.startsWith("playerHand")) {
                    // Update player's hand
                    System.out.println("Your Hand: " + message.split(":")[1]);
                } else if (message.equals("endGame")) {
                    // Handle game end
                    System.out.println("Game Over: " + message.split(":")[1]);
                    break;
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
