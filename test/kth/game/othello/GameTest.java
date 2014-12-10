package kth.game.othello;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.gamestate.GameStateHandler;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.score.Score;

import org.junit.Test;
import org.mockito.Mockito;

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

	private void resetOccupantsOnBoard(GameBoard board) {
		for (int x = 0; x < board.getMaxX(); x++) {
			for (int y = 0; y < board.getMaxY(); y++) {
				setNode(x, y, null, board);
			}
		}
	}

	private void setNode(int x, int y, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(null);
		when(board.getNode(x, y).isMarked()).thenReturn(false);
	}

	private void setNode(int x, int y, String playerId, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(playerId);
		when(board.getNode(x, y).isMarked()).thenReturn(true);
	}

	public void testUndo() {
		GameBoard board = mockBoard(5);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		MoveHandler movehandler = Mockito.mock(MoveHandler.class);
		Score score = Mockito.mock(Score.class);
		GameStateHandler gamestatehandler = Mockito.mock(GameStateHandler.class);
		Game game = new Game(board, playerhandler, movehandler, score, gamestatehandler);

	}

	@Test
	public void testGameIsFinished() {
		fail("test that the observable is used");
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
