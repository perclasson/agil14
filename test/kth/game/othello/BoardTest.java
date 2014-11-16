package kth.game.othello;

import kth.game.othello.board.OthelloBoard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BoardTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	// Test that
	@Test
	public void testOthelloBoard() {
		OthelloBoard board = new OthelloBoard("Black", "White", 8);
		// Expected when there is no Node with that NodeID
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("There is no node with that ID.");
		board.getNode("x -" + 1 + "y -" + 1);
	}

}
