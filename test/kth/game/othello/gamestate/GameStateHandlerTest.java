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
		GameState oldState = Mockito.mock(GameState.class);
		Stack<GameState> gameStates = new Stack<GameState>();
		gameStates.add(oldState);
		GameStateFactory gameStateFactory = Mockito.mock(GameStateFactory.class);
		Game game = Mockito.mock(Game.class);

		// GameStateHandler return latest GameState or null if no state exists
		GameStateHandler gameStateHandler = new GameStateHandler(gameStates, gameStateFactory);
		GameState currentState = Mockito.mock(GameState.class);
		Mockito.when(gameStateFactory.create(game)).thenReturn(currentState);

		// Add currentState
		gameStateHandler.add(game);

		// Make a move and add another
		Mockito.when(gameStateFactory.create(game)).thenReturn(Mockito.mock(GameState.class));
		gameStateHandler.add(game);

		// We then expect currentState
		assertEquals(currentState, gameStateHandler.pop());
		assertEquals(oldState, gameStateHandler.pop());
	}

}
