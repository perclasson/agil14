package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;

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
		MoveStrategy m = new MaxSwappedStrategy();
		String playerId = "player";
		Rules rules = mock(Rules.class);
		Board board = mock(Board.class);

		// Empty board etc should return null
		assertEquals(m.move(playerId, rules, board), null);

		// Mock nodes for the board
		Node node1 = mockNode(2, 4);
		Node node2 = mockNode(1, 1);
		Node node3 = mockNode(3, 4);
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		when(board.getNodes()).thenReturn(nodes);
		// Board is 8x8
		when(board.getMaxX()).thenReturn(8);
		when(board.getMaxY()).thenReturn(8);

		// Make rule return different sizes
		List<Node> nodes1 = mock(List.class);
		when(nodes1.size()).thenReturn(1);

		when(rules.getNodesToSwap(playerId, node1.getId())).thenReturn(nodes1);
		when(rules.getNodesToSwap(playerId, node2.getId())).thenReturn(nodes1);
		when(rules.getNodesToSwap(playerId, node3.getId())).thenReturn(nodes1);

		assertEquals(m.move(playerId, rules, board).getId(), node2.getId());

	}
}
