package kth.game.othello;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.gamestate.GameStateHandler;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.score.Score;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests of class Game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameTest {
	private GameBoard mockBoard(int order) {
		GameBoard board = mock(GameBoard.class);
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < order; y++) {
			for (int x = 0; x < order; x++) {
				Node node = mock(Node.class);
				when(node.getId()).thenReturn("x" + x + "y" + y);
				when(node.getXCoordinate()).thenReturn(x);
				when(node.getYCoordinate()).thenReturn(y);
				when(node.isMarked()).thenReturn(false);
				when(board.getNode(x, y)).thenReturn(node);
				nodes.add(node);
			}
		}
		when(board.getNodes()).thenReturn(nodes);
		return board;
	}

	private void setNode(int x, int y, String playerId, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(playerId);
		when(board.getNode(x, y).isMarked()).thenReturn(true);
	}

	@Test
	public void undoTest() {
		GameBoard board = mockBoard(3);

		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		MoveHandler moveHandler = Mockito.mock(MoveHandler.class);
		Score score = Mockito.mock(Score.class);
		GameStateHandler gameStateHandler = Mockito.mock(GameStateHandler.class);
		Game game = new Game(board, playerHandler, moveHandler, score, gameStateHandler);

		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);

		// Verify that game was added as a game state after move
		game.move("white", "x2y0");
		verify(gameStateHandler).add(game);

		// After undo make sure we pop GameState from GameStateHandler
		game.undo();
		verify(gameStateHandler).pop();
	}

	@Test
	public void testGameIsFinished() {
		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		MoveHandler moveHandler = Mockito.mock(MoveHandler.class);
		GameStateHandler gameStateHandler = Mockito.mock(GameStateHandler.class);
		Mockito.when(playerHandler.getPlayerInTurn()).thenReturn(null);

		Game game = new Game(null, playerHandler, moveHandler, null, gameStateHandler);

		// Verify that observer got update when game is finished and move is made
		Observer o = Mockito.mock(Observer.class);
		assertFalse(game.isActive());
		game.addGameFinishedObserver(o);
		game.move();
		Mockito.verify(o).update(game, null);
	}

	@Test
	public void testUniqueGameId() {
		List<Game> games = new ArrayList<Game>();
		for (int i = 0; i < 100; i++) {
			games.add(new Game(null, null, null, null, null));
		}

		// Make sure that no game id is equal to another in the list
		for (int i = 0; i < games.size() - 1; i++) {
			for (int j = i + 1; j < games.size(); j++) {
				assertNotEquals(games.get(i).getId(), games.get(j).getId());
			}
		}
	}
}
