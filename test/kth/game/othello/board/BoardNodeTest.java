package kth.game.othello.board;

import static org.junit.Assert.assertEquals;

import java.util.Observer;

import org.junit.Test;
import org.mockito.Mockito;
/**
 * Unit tests of class BoardNode.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class BoardNodeTest {
	@Test
	public void setOccupantPlayerIdTest() {
		BoardNode node = new BoardNode(1, 1, "white");

		// Add observer to node
		Observer observer = Mockito.mock(Observer.class);
		node.addObserver(observer);

		assertEquals("white", node.getOccupantPlayerId());
		NodeOccupantNotification notification = Mockito.mock(NodeOccupantNotification.class);
		node.setOccupantPlayerId("test", notification);

		// Verify that observer is updated
		Mockito.verify(observer).update(node, notification);

		// If nothing has changed, observer will not be updated
		node.setOccupantPlayerId("test", Mockito.mock(NodeOccupantNotification.class));
		Mockito.verifyNoMoreInteractions(observer);
	}
}
