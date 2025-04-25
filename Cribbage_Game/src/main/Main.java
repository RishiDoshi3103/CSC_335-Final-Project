package main;

import controller.GameController;
import model.Game;
import ui.GameStarter;
import ui.GuiViewer;

public class Main {
	public static void main(String[] args) {
		GameStarter starter = new GameStarter();
		int mode = starter.GameMode();
		String[] names = starter.getPlayerNames(mode);

		Game model;
		if (mode == 1) {
			model = new Game(names[0], names[1], 0);
		}
		else if (mode == 2) {
			model = new Game(names[0], names[1], 1);
		}
		else {
			model = new Game(names[0], names[1], 2);
		}

		GuiViewer view = new GuiViewer();
		GameController controller = new GameController(model, view);
		controller.registerObserver(view); // ← Make sure observer is registered
		controller.playCribbage();
	}
}
