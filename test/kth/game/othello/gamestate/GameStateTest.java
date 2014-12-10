package kth.game.othello.gamestate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import kth.game.othello.Game;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import org.junit.Test;
import org.mockito.Mockito;

public class GameStateTest {

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

	@Test
	public void testGameStateFactory() {
		// Set up
		Game game = Mockito.mock(Game.class);
		Player player = Mockito.mock(Player.class);
		Board board = Mockito.mock(Board.class);
		Mockito.when(game.getPlayerInTurn()).thenReturn(player);
		Mockito.when(game.getBoard()).thenReturn(board);
		List<Node> nodes = new ArrayList<Node>();
		Mockito.when(board.getNodes()).thenReturn(nodes);

		GameStateFactory gameStateFactory = new GameStateFactory();
		GameState gameState = gameStateFactory.create(game);
		assertTrue(gameState instanceof GameState);
		assertTrue(gameState != null);
	}

}
