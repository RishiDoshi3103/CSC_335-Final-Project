package main;

import controller.GameController;
import Player.ComputerPlayer;
import Player.HumanPlayer;
import strategy.EasyStrategy;
import strategy.HardStrategy;
import ui.GameView;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Computer will randomly choose between easy and hard discard logic each round
        ComputerPlayer ai = new ComputerPlayer(
            "Computer",
            List.of(new EasyStrategy(), new HardStrategy())
        );
        GameController ctrl = new GameController(
            new HumanPlayer("You"),
            ai
        );
        ctrl.startNewGame();
        new GameView();
    }
}