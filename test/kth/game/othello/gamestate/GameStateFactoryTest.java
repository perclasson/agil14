package kth.game.othello.gamestate;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Game;
import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * This class test the class GameStateFactory.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameStateFactoryTest {

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
