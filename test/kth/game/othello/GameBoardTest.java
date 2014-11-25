package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import kth.game.othello.board.Node;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.BoardNode;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests of class OthelloBoard.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class BoardTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	// Test that OthelloBoard.getNodes throw exception and get node by id and
	// get node by coordinations give the same
	// result for the same argument
	@Test
	public void testGetNodess() {
		GameBoard board = new GameBoard("Black", "White", 8);
		BoardNode node = mock(BoardNode.class);
		when(node.getId()).thenReturn("x3y4");
		Node secondNode = board.getNode("x" + 3 + "y" + 4);
		Node thirdNode = board.getNodeByCoordinates(3, 4);
		assertEquals("Board should return a node", node.getId(), secondNode.getId());
		assertEquals("Get node by ID or coordinates shoulde return same node for same argument", secondNode, thirdNode);
		assertNotEquals("It should not be the same node on different locations on the board", secondNode,
				board.getNodeByCoordinates(4, 4));
		// Expected when there is no Node with that NodeID
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("There is no node with that ID.");
		board.getNode("x -" + 1 + "y -" + 1);
	}

	// Add some Nodes to board and look if Board.changeOccupantOnNodes() change
	// occupants player on those nodes
	@Test
	public void testChangeOccupantOnNodes() {
		GameBoard board = new GameBoard("Black", "White", 8);
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < 8; i++) {
			Node node = new BoardNode(i, 3, "Black");
			nodes.add(node);
		}
		board.changeOccupantOnNodes(nodes, "White");
		for (int i = 0; i < 8; i++) {
			Node node = board.getNodeByCoordinates(i, 3);
			assertEquals("The nodes on the board should be changed from black to white", "White",
					node.getOccupantPlayerId());
			assertNotEquals("The nodes on the board should be changed from black to white", "Black",
					node.getOccupantPlayerId());
		}
	}
}
