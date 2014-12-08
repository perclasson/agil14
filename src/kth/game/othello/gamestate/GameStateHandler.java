package kth.game.othello.gamestate;

import java.util.Stack;

import kth.game.othello.Game;

public class GameStateHandler {
	Stack<GameState> gameStates;
	GameStateFactory gameStateFactory;

	public GameStateHandler(Stack<GameState> gameStates, GameStateFactory gameStateFactory) {
		this.gameStates = gameStates;
		this.gameStateFactory = gameStateFactory;
	}

	public void add(Game game) {
		gameStates.add(gameStateFactory.create(game));
	}

	public GameState pop() {
		return gameStates.pop();
	}
}
