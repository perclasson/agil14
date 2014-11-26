package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

import org.junit.Test;

public class MoveCalculatorTest {

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

	private void setNode(int x, int y, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(null);
		when(board.getNode(x, y).isMarked()).thenReturn(false);
	}

	private void setNode(int x, int y, String playerId, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(playerId);
		when(board.getNode(x, y).isMarked()).thenReturn(true);
	}

	@Test
	public void testGetNodesToSwap() {
		GameBoard board = mockBoard(3);
		MoveCalculator moveCalculator = new MoveCalculator(board);
		List<Node> swap = null;

		// Scenario:
		// | white black target |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);

		// Should be:
		// | white white white |
		// | empty empty empty |
		// | empty empty empty |
		swap = moveCalculator.getNodesToSwap("white", "x2y0");
		assertEquals(swap.size(), 3);
		assertEquals(swap.get(1), board.getNode(1, 0));
		assertEquals(swap.get(2), board.getNode(2, 0));

		// Scenario:
		// | white black empty |
		// | empty target empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		swap = moveCalculator.getNodesToSwap("white", "x1y1");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = moveCalculator.getNodesToSwap("white", "x1y0");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = moveCalculator.getNodesToSwap("white", "x1y0");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white black white |
		// | black black black |
		// | target black white |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		setNode(2, 0, "white", board);
		setNode(0, 1, "black", board);
		setNode(1, 1, "black", board);
		setNode(2, 1, "black", board);
		setNode(0, 2, board);
		setNode(1, 2, "black", board);
		setNode(2, 2, "white", board);

		// Should be:
		// | white black white |
		// | white white black |
		// | white white white |
		swap = moveCalculator.getNodesToSwap("white", "x0y2");
		assertEquals(swap.size(), 5);
	}

	@Test
	public void getAllPossibleMovesTest() {
		GameBoard board = mockBoard(5);
		MoveCalculator moveCalculator = new MoveCalculator(board);
		List<Move> moves = null;

		// Only one possible move
		// Scenario:
		// | white black empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.get(0).getEndNode(), board.getNode(2, 0));
		assertEquals(moves.get(0).getIntermediateNodes().get(0), board.getNode(1, 0));
		assertEquals(moves.get(0).getStartNode(), board.getNode(0, 0));
		assertEquals(moves.size(), 1);

		// Two possible moves
		// Scenario:
		// | white black empty empty empty |
		// | black empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |

		setNode(0, 1, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.get(0).getEndNode(), board.getNode(2, 0));
		assertEquals(moves.get(0).getIntermediateNodes().get(0), board.getNode(1, 0));
		assertEquals(moves.get(0).getStartNode(), board.getNode(0, 0));
		assertEquals(moves.get(1).getEndNode(), board.getNode(0, 2));
		assertEquals(moves.get(1).getIntermediateNodes().get(0), board.getNode(0, 1));
		assertEquals(moves.get(1).getStartNode(), board.getNode(0, 0));

		assertEquals(moves.size(), 2);

		// three possible moves
		// Scenario:
		// | white black empty empty empty |
		// | black black empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(1, 1, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.get(0).getEndNode(), board.getNode(2, 0));
		assertEquals(moves.get(0).getIntermediateNodes().get(0), board.getNode(1, 0));
		assertEquals(moves.get(0).getStartNode(), board.getNode(0, 0));

		assertEquals(moves.get(1).getEndNode(), board.getNode(0, 2));
		assertEquals(moves.get(1).getIntermediateNodes().get(0), board.getNode(0, 1));
		assertEquals(moves.get(1).getStartNode(), board.getNode(0, 0));

		assertEquals(moves.get(2).getEndNode(), board.getNode(2, 2));
		assertEquals(moves.get(2).getIntermediateNodes().get(0), board.getNode(1, 1));
		assertEquals(moves.get(2).getStartNode(), board.getNode(0, 0));

		assertEquals(moves.size(), 3);

		// Reset the board
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				setNode(i, j, board);
			}
		}

		// four possible moves
		// Scenario:
		// | empty empty empty empty empty |
		// | empty empty black empty empty |
		// | empty black white black empty |
		// | empty empty black empty empty |
		// | empty empty empty empty empty |
		setNode(2, 2, "white", board);
		setNode(2, 1, "black", board);
		setNode(1, 2, "black", board);
		setNode(2, 3, "black", board);
		setNode(3, 2, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.size(), 4);

		// five possible moves
		// Scenario:
		// | empty empty empty empty empty |
		// | empty black black empty empty |
		// | empty black white black empty |
		// | empty empty black empty empty |
		// | empty empty empty empty empty |
		setNode(1, 1, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.size(), 5);

		// six possible moves
		// Scenario:
		// | empty empty empty empty empty |
		// | empty black black black empty |
		// | empty black white black empty |
		// | empty empty black empty empty |
		// | empty empty empty empty empty |
		setNode(3, 1, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.size(), 6);

		// seven possible moves
		// Scenario:
		// | empty empty empty empty empty |
		// | empty black black black empty |
		// | empty black white black empty |
		// | empty black black empty empty |
		// | empty empty empty empty empty |
		setNode(1, 3, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.size(), 7);

		// seven possible moves
		// Scenario:
		// | empty empty empty empty empty |
		// | empty black black black empty |
		// | empty black white black empty |
		// | empty black black black empty |
		// | empty empty empty empty empty |
		setNode(3, 3, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.size(), 8);

		// Reset the board
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				setNode(i, j, board);
			}
		}

		// Test when there are several IntermediateNodes

		// Scenario:
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | white black black black empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(0, 2, "white", board);
		setNode(1, 2, "black", board);
		setNode(2, 2, "black", board);
		setNode(3, 2, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		
		assertEquals(moves.get(0).getStartNode(), board.getNode(0, 2));
		// Between startNode and endNode
		int k = 1;
		for (Node node : moves.get(0).getIntermediateNodes()) {
			assertEquals(node, board.getNode(k, 2));
			k++;
		}
		assertEquals(moves.get(0).getEndNode(), board.getNode(4, 2));
		assertEquals(moves.size(), 1);

		// No moves should be find
		// Scenario:
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | white black black black black |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(4, 2, "black", board);
		moves = moveCalculator.getAllPossibleMoves("white");
		assertEquals(moves.size(), 0);
	}
}
