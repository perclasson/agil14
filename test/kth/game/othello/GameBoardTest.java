package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
		HashSet<NodeData> nodes = new HashSet<NodeData>();
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
	    nodes.add(nodeDataOne);
	    nodes.add(nodeDataTwo);
	    nodes.add(nodeDataThree);
		
	    // Create board with node data and retrieve nodes
		GameBoard board = new GameBoard(nodes);
		Node nodeOne = board.getNode(0, 0);
		Node nodeTwo = board.getNode(1, 1);
		Node nodeThree = board.getNode(2, 2);
		
		// Assert
		for (Node node : board.getNodes()) {
			assertEquals(node.getOccupantPlayerId(), "white");
		}
		
		// Change all node occupant to black
		board.changeOccupantOnNode(nodeOne, "black");
		board.changeOccupantOnNode(nodeTwo, "black");
		board.changeOccupantOnNode(nodeThree, "black");
		
		// Assert
		for (Node node : board.getNodes()) {
			assertEquals(node.getOccupantPlayerId(), "black");
		}
		
		// Toggle occupant on node one
		board.changeOccupantOnNode(nodeOne, "black");
		assertEquals(nodeOne.getOccupantPlayerId(), "black");
		board.changeOccupantOnNode(nodeOne, "white");
		assertEquals(nodeOne.getOccupantPlayerId(), "white");
		board.changeOccupantOnNode(nodeOne, "black");
		assertEquals(nodeOne.getOccupantPlayerId(), "black");
	}
}
