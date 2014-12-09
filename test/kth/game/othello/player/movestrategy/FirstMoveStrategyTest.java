package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

public class FirstMoveStrategyTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testMove() {
		Rules rules = mock(Rules.class);
		Board board = mock(Board.class);
		MinSwappedStrategy m = new MinSwappedStrategy();

		// No move possible, should return null
		assertEquals(m.move("player", rules, board), null);

		// Mock nodes for the board
		String nodeId1 = "x1y1";
		String nodeId2 = "x1y2";
		Node node1 = mock(Node.class);
		Node node2 = mock(Node.class);
		when(node1.getId()).thenReturn(nodeId1);
		when(node2.getId()).thenReturn(nodeId2);
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		when(board.getNodes()).thenReturn(nodes);

		// Make rule return different sizes i.e two different moves
		List<Node> nodes1 = mock(List.class);
		when(nodes1.size()).thenReturn(1);
		List<Node> nodes2 = mock(List.class);
		when(nodes2.size()).thenReturn(1);
		when(rules.getNodesToSwap("player", nodeId1)).thenReturn(nodes1);
		when(rules.getNodesToSwap("player", nodeId2)).thenReturn(nodes2);

		// Should get the first move
		assertEquals(m.move("player", rules, board), node1);
		assertNotEquals(m.move("player", rules, board), node2);
	}
}
