package kth.game.othello.player.move.strategy;

import java.util.ArrayList;
import java.util.Random;

import kth.game.othello.Othello;
import kth.game.othello.OthelloImpl;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class RandomMoveTest {
	@Test
	public void testGetRandomMove() {
		Othello game = Mockito.mock(OthelloImpl.class);
		String playerId = "1";
		Rules rules = Mockito.mock(RulesImpl.class);
		ArrayList<Node> nodes = new ArrayList<Node>();

		for (int i = 0; i < 64; i++) {
			Node nodeToAdd = Mockito.mock(Node.class);
			Mockito.when(nodeToAdd.getId()).thenReturn("" + i);
			nodes.add(nodeToAdd);
			if (i < 40) {
				Mockito.when(rules.isMoveValid(playerId, "" + i)).thenReturn(false);
			} else {
				Mockito.when(rules.isMoveValid(playerId, "" + i)).thenReturn(true);
			}
		}

		BoardImpl board = new BoardImpl(nodes);
		Mockito.when(game.getBoard()).thenReturn(board);
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(24)).thenReturn(1);
		RandomMove strategy = new RandomMove(random);
		Node comparisonNode = nodes.get(41);
		Assert.assertEquals(comparisonNode.getId(), strategy.move(playerId, rules, board).getId());
	}
}
