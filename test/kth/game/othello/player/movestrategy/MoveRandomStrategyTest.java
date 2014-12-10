package kth.game.othello.player.movestrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

import org.junit.Test;

public class MoveRandomStrategyTest {

	@Test
	public void testMove() {
		Rules rules = mock(Rules.class);
		Board board = mock(Board.class);
		Random rand = mock(Random.class);
		MoveStrategy randomMoveStragey = new MoveRandomStrategy(rand);
		String playerId = "player";

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

		for (Node node : nodes) {
			when(rules.isMoveValid(playerId, node.getId())).thenReturn(true);
		}

		// Test random that we get random moves
		when(rand.nextInt(nodes.size())).thenReturn(1);
		assertEquals(randomMoveStragey.move(playerId, rules, board).getId(), nodeId2);

		when(rand.nextInt(3)).thenReturn(0);
		assertEquals(randomMoveStragey.move(playerId, rules, board).getId(), nodeId1);

	}
}
