package kth.game.othello.board;

import static org.junit.Assert.assertEquals;

import java.util.Observer;

import org.junit.Test;
import org.mockito.Mockito;

public class BoardNodeTest {
	@Test
	public void setOccupantPlayerIdTest() {
		BoardNode whiteNode = new BoardNode(1, 1, "white");

		// Add observer to node
		Observer observer = Mockito.mock(Observer.class);
		whiteNode.addObserver(observer);

		assertEquals(whiteNode.getOccupantPlayerId(), "white");
		NodeOccupantNotification notification = Mockito.mock(NodeOccupantNotification.class);
		whiteNode.setOccupantPlayerId("test", notification);

		// Verify that observer is updated
		Mockito.verify(observer).update(whiteNode, notification);

		// If nothing has changed, observer will not be updated
		whiteNode.setOccupantPlayerId("test", Mockito.mock(NodeOccupantNotification.class));
		Mockito.verifyNoMoreInteractions(observer);
	}
}
