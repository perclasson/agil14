package kth.game.othello.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardNodeTest {

	@Test
	public void setOccupantPlayerIdTest() {
		BoardNode node = new BoardNode(1, 1, "white");
		assertEquals(node.getOccupantPlayerId(), "white");
		node.setOccupantPlayerId("black");
		assertEquals(node.getOccupantPlayerId(), "black");
		node.setOccupantPlayerId("white");
		assertEquals(node.getOccupantPlayerId(), "white");
	}
}
