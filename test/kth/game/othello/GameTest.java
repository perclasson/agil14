package kth.game.othello;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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

	@Test
	public void undoTest() {
		GameBoard board = mockBoard(3);

		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);

		MoveHandler movehandler = Mockito.mock(MoveHandler.class);
		when(movehandler.move()).thenCallRealMethod();

		Score score = Mockito.mock(Score.class);

		GameStateHandler gamestatehandler = Mockito.mock(GameStateHandler.class);

		Game game = new Game(board, playerhandler, movehandler, score, gamestatehandler);

		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		game.move("white", "x2y0");

		assertTrue(game.getBoard().getNode(2, 0).isMarked());
		game.undo();
		assertFalse(game.getBoard().getNode(2, 0).isMarked());

	}
}
