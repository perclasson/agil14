package kth.game.othello;

import static org.junit.Assert.assertEquals;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloNode;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoardTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	// Test that OthelloBoard.getNodes throw exception and return a start Node
	@Test
	public void testOthelloBoard() {
		OthelloBoard board = new OthelloBoard("Black", "White", 8);
		OthelloNode node = new OthelloNode(3, 4);
		Node secondNode = board.getNode("x" + 3 + "y" + 4);
		assertEquals("Board should return a node", node.getId(), secondNode.getId());
		// Expected when there is no Node with that NodeID
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("There is no node with that ID.");
		board.getNode("x -" + 1 + "y -" + 1);
	}
}
