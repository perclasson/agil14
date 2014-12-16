package kth.game.othello.player.move.strategy;

import java.util.ArrayList;

import kth.game.othello.Othello;
import kth.game.othello.OthelloImpl;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class FirstMoveTest {
	@Test
	public void testGetFirstValidMove() {
		Othello game = Mockito.mock(OthelloImpl.class);
		String playerId = "1";
		Node comparisonNode = Mockito.mock(Node.class);
		Rules rules = Mockito.mock(RulesImpl.class);
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < 64; i++) {
			Node nodeToAdd = Mockito.mock(Node.class);
			Mockito.when(nodeToAdd.getId()).thenReturn("" + i);
			nodes.add(nodeToAdd);
			if (i < 40) {
				Mockito.when(rules.isMoveValid(playerId, "" + i)).thenReturn(false);
			} else if (i == 40) {
				Mockito.when(rules.isMoveValid(playerId, "" + i)).thenReturn(true);
				comparisonNode = nodeToAdd;
			} else {
				Mockito.when(rules.isMoveValid(playerId, "" + i)).thenReturn(true);
			}
		}
		BoardImpl board = new BoardImpl(nodes);
		Mockito.when(game.getBoard()).thenReturn(board);

		FirstMove strategy = new FirstMove();
		Assert.assertEquals(comparisonNode.getId(), strategy.move(playerId, rules, board).getId());
	}
}
