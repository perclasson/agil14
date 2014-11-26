package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;

import kth.game.othello.board.Node;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.factory.NodeData;

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
public class GameBoardTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testGetNodes() {
		GameBoard board = new GameBoard(new HashSet<NodeData>());
		// Expected when there is no Node with that NodeID
		exception.expect(IllegalArgumentException.class);
		board.getNode(0, 0);
	}

	@Test
	public void testChangeOccupantOnNodes() {
		// Mock node data for the board
		NodeData nodeDataOne = mock(NodeData.class);
		when(nodeDataOne.getXCoordinate()).thenReturn(0);
		when(nodeDataOne.getYCoordinate()).thenReturn(0);
		when(nodeDataOne.getOccupantPlayerId()).thenReturn("white");
		NodeData nodeDataTwo = mock(NodeData.class);
		when(nodeDataTwo.getXCoordinate()).thenReturn(1);
		when(nodeDataTwo.getYCoordinate()).thenReturn(1);
		when(nodeDataTwo.getOccupantPlayerId()).thenReturn("white");
		NodeData nodeDataThree = mock(NodeData.class);
		when(nodeDataThree.getXCoordinate()).thenReturn(2);
		when(nodeDataThree.getYCoordinate()).thenReturn(2);
		when(nodeDataThree.getOccupantPlayerId()).thenReturn("white");
		HashSet<NodeData> nodeData = new HashSet<NodeData>();
		nodeData.add(nodeDataOne);
		nodeData.add(nodeDataTwo);
		nodeData.add(nodeDataThree);

		// Create board with node data and retrieve nodes
		GameBoard board = new GameBoard(nodeData);
		Node nodeOne = board.getNode(0, 0);
		Node nodeTwo = board.getNode(1, 1);
		Node nodeThree = board.getNode(2, 2);

		// Assert
		for (Node node : board.getNodes()) {
			assertEquals(node.getOccupantPlayerId(), "white");
		}

		ArrayList<Node> nodes = new ArrayList<Node>();

		// Change all node occupant to black
		nodes.add(nodeOne);
		nodes.add(nodeTwo);
		nodes.add(nodeThree);
		board.changeOccupantOnNodes(nodes, "black");

		// Assert
		for (Node node : board.getNodes()) {
			assertEquals(node.getOccupantPlayerId(), "black");
		}

		// Toggle occupant on node one
		nodes.clear();
		nodes.add(nodeOne);
		board.changeOccupantOnNodes(nodes, "black");
		assertEquals(nodeOne.getOccupantPlayerId(), "black");
		board.changeOccupantOnNodes(nodes, "white");
		assertEquals(nodeOne.getOccupantPlayerId(), "white");
		board.changeOccupantOnNodes(nodes, "black");
		assertEquals(nodeOne.getOccupantPlayerId(), "black");
	}
}
