package main;

import controller.GameController;
import model.Game;
import ui.TextViewer;

public class Main {
	public static void main(String[] args) {
		Game model = new Game("Player1", "Player2");
		TextViewer view = new TextViewer();
		GameController controller = new GameController(model, view);
		controller.startGame();
	}
}
