package kth.game.othello.gamestate;

import java.util.EmptyStackException;
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
		gameStates.push(gameStateFactory.create(game));
	}

	public GameState pop() {
		try {
			return gameStates.pop();
		} catch (EmptyStackException e) {
			return null;
		}

	}
}
