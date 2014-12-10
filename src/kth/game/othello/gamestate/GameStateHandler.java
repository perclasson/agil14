package kth.game.othello.gamestate;

import java.util.EmptyStackException;
import java.util.Stack;

import kth.game.othello.Game;

/**
 * The responsibility of this class is to save and pop GameStates, used in the Game class.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameStateHandler {
	Stack<GameState> gameStates;
	GameStateFactory gameStateFactory;

	public GameStateHandler(Stack<GameState> gameStates, GameStateFactory gameStateFactory) {
		this.gameStates = gameStates;
		this.gameStateFactory = gameStateFactory;
	}

	/**
	 * Saves current game state.
	 * 
	 * @param game
	 *            The game to add.
	 */
	public void add(Game game) {
		gameStates.push(gameStateFactory.create(game));
	}

	/**
	 * Get the latest game states.
	 * 
	 * @return GameState or null if no old states exist.
	 */
	public GameState pop() {
		try {
			return gameStates.pop();
		} catch (EmptyStackException e) {
			return null;
		}

	}
}
