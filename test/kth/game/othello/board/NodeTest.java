package kth.game.othello.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import kth.game.othello.board.Node;
import kth.game.othello.board.BoardNode;

import org.junit.Test;

/**
 * Unit tests of class OthelloNode.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class NodeTest {

	@Test
	public void testIsMarkedNode() {
		Node unmarkedNode = new BoardNode(0, 0);
		assertFalse("Unmarked node must not have player id.", unmarkedNode.isMarked());

		Node markedNode = new BoardNode(0, 0, "player");
		assertTrue("Markned node must have player id.", markedNode.isMarked());
	}

	@Test
	public void testNodeId() {
		Node node = new BoardNode(1, 2);
		assertEquals("Node id must be x1y2.", node.getId(), "x1y2");
		assertNotEquals("Node id must be x1y2.", node.getId(), "x5y9");
	}

}
