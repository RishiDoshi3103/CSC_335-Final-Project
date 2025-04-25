package main;

import controller.GameController;
import model.Game;
import ui.GameStarter;
import ui.GuiViewer;

/**
 * The entry point for the Cribbage game application.
 *
 * This class initializes the game by prompting the user to select a game mode
 * and player names, sets up the game model, view, and controller, and starts
 * the game loop.
 *
 */
public class Main {

    /**
     * The main method that launches the Cribbage game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize the game starter for input prompts
        GameStarter starter = new GameStarter();        
        // Get the selected game mode (1: Human vs Human, 2: Human vs Computer, 3: Computer vs Computer)
        int mode = starter.GameMode();
        // Get player names based on selected mode
        String[] names = starter.getPlayerNames(mode);

        // Initialize the game model based on the selected mode
        Game model;
        if (mode == 1) {
            model = new Game(names[0], names[1], 0);
        } else if (mode == 2) {
            model = new Game(names[0], names[1], 1);
        } else {
            model = new Game(names[0], names[1], 2);
        }

        // Set up the GUI view
        GuiViewer view = new GuiViewer();
        // Create the controller to manage the game logic
        GameController controller = new GameController(model, view);
        // Register the GUI as an observer to receive game state updates
        controller.registerObserver(view);
        // Begin the game loop
        controller.playCribbage();
    }
}
