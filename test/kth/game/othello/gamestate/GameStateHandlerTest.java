package kth.game.othello.gamestate;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import kth.game.othello.Game;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class tests GameStateHandler.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameStateHandlerTest {

	@Test
	public void testGameStateHandler() {
		// Set up
		Stack<GameState> gameStates = new Stack<GameState>();
		GameStateFactory factory = Mockito.mock(GameStateFactory.class);
		GameState gameState = Mockito.mock(GameState.class);
		Game game = Mockito.mock(Game.class);
		gameStates.add(gameState);

		// GameStateHandler return latest GameState or null if no state exists
		GameStateHandler gameStateHandler = new GameStateHandler(gameStates, factory);
		assertEquals(gameState, gameStateHandler.pop());
		assertEquals(null, gameStateHandler.pop());

		GameState newGameState = Mockito.mock(GameState.class);
		Mockito.when(factory.create(game)).thenReturn(newGameState);

		// GameStateHandler gets latest GameState from factory and returns it on pop
		gameStateHandler.add(game);
		assertEquals(newGameState, gameStateHandler.pop());
	}

}
