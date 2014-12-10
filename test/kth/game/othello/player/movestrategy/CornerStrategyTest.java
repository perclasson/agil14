package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;

/**
 * Unit tests of class CornerStrategy.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class CornerStrategyTest {
	private Node mockNode(int x, int y) {
		Node node = mock(Node.class);
		when(node.getId()).thenReturn("x" + x + "y" + y);
		when(node.getXCoordinate()).thenReturn(x);
		when(node.getYCoordinate()).thenReturn(y);
		return node;
	}

	@Test
	public void testMove() {
		MoveStrategy cornerStrategy = new CornerStrategy();
		String playerId = "player";
		Rules rules = mock(Rules.class);
		Board board = mock(Board.class);

		// Mock nodes for the board
		Node node1 = mockNode(0, 0); // This is the expected node as it is in the corner
		Node node2 = mockNode(1, 1);
		Node node3 = mockNode(0, 1);

		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		when(board.getNodes()).thenReturn(nodes);

		// Return all the nodes when getting nodes to swap
		for (Node node : nodes) {
			when(rules.getNodesToSwap(playerId, node.getId())).thenReturn(nodes);
		}

		// Board is 8x8
		when(board.getMaxX()).thenReturn(8);
		when(board.getMaxY()).thenReturn(8);

		// Corner strategy should return the corner node
		Node cornerNode = cornerStrategy.move(playerId, rules, board);
		assertEquals(cornerNode.getId(), node1.getId());
	}
}
