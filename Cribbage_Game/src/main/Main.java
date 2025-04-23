package main;

import controller.GameController;
import model.Game;
import ui.GameStarter;
import ui.TextViewer;

public class Main {
	public static void main(String[] args) {
		GameStarter starter = new GameStarter();
		int mode = starter.GameMode();
		Game model;
		if (mode == 1) {
			model = new Game("Player1", "Player2", 0);
		}
		else if (mode == 2) {
			model = new Game("Player1", "Player2", 1);
		}
		else {
			model = new Game("Player1", "Player2", 2);
		}
		TextViewer view = new TextViewer();
		GameController controller = new GameController(model, view);
		controller.playCribbage();
	}
}
