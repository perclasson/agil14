package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

import org.junit.Test;
import org.mockito.Mockito;

public class MoveLogicTest {

	@Test
	public void testGetNodesToSwap() {
		// TODO
	}
	
	private List<Node> mockBoardNodes(int n) {
		List<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				Node node = Mockito.mock(Node.class);
				when(node.getId()).thenReturn("x" + x + "y" + y);
				when(node.getXCoordinate()).thenReturn(x);
				when(node.getYCoordinate()).thenReturn(y);
				nodes.add(node);
			}
		}
		return nodes;
	}

}
