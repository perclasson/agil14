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

public class MinSwappedStrategyTest {

	@Test
	public void testMove() {
		MoveStrategy m = new MinSwappedStrategy();
		String playerId = "player";
		Rules rules = mock(Rules.class);
		Board board = mock(Board.class);

		// Empty board etc should return null
		assertEquals(m.move(playerId, rules, board), null);

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
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		when(board.getNodes()).thenReturn(nodes);

		// Make rule return different sizes
		List<Node> nodes1 = mock(List.class);
		when(nodes1.size()).thenReturn(1);
		List<Node> nodes2 = mock(List.class);
		when(nodes2.size()).thenReturn(3);
		List<Node> nodes3 = mock(List.class);
		when(nodes3.size()).thenReturn(5);
		when(rules.getNodesToSwap(playerId, nodeId1)).thenReturn(nodes1);
		when(rules.getNodesToSwap(playerId, nodeId2)).thenReturn(nodes2);
		when(rules.getNodesToSwap(playerId, nodeId3)).thenReturn(nodes3);

		// Should make the move that gives smallest amount of swapped nodes
		assertEquals(m.move(playerId, rules, board).getId(), nodeId1);
	}
}
