package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;

/**
 * Unit tests of class MaxSwappedStrategy.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class MaxSwappedStrategyTest {

	@Test
	public void testMove() {
		MoveStrategy maxSwappedStrategy = new MaxSwappedStrategy();
		String playerId = "player";
		Rules rules = mock(Rules.class);
		Board board = mock(Board.class);

		// Mock nodes for the board
		String nodeId1 = "x1y1";
		String nodeId2 = "x1y2";
		String nodeId3 = "x1y3";

		Node node1 = mock(Node.class);
		when(node1.getId()).thenReturn(nodeId1);

		Node node2 = mock(Node.class);
		when(node2.getId()).thenReturn(nodeId2);

		Node node3 = mock(Node.class);
		when(node3.getId()).thenReturn(nodeId3);

		// Create list of nodes for board
		List<Node> nodes = Arrays.asList(node1, node2, node3);
		when(board.getNodes()).thenReturn(nodes);

		when(rules.getNodesToSwap(playerId, nodeId1)).thenReturn(Arrays.asList(node1));
		when(rules.getNodesToSwap(playerId, nodeId2)).thenReturn(Arrays.asList(node1, node2));
		when(rules.getNodesToSwap(playerId, nodeId3)).thenReturn(Arrays.asList(node1, node2, node3));

		// We expected the move that gives most swapped nodes
		assertEquals(nodeId3, maxSwappedStrategy.move(playerId, rules, board).getId());
	}
}
