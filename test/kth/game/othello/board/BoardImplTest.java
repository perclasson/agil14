package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BoardImplTest {

	private BoardImpl getSquareBoard(int size) {

		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				nodes.add(new NodeImpl(i, j, 1));
			}
		}

		return new BoardImpl(nodes);
	}

	@Test
	public void getNodesInRightDirectionAtEdge() {
		int size = 8;
		BoardImpl board = getSquareBoard(size);
		Node node = board.getNode(7, 0);
		List<Node> nodesInDirection = board.getNodesInDirection(Direction.RIGHT, node.getXCoordinate(),
				node.getYCoordinate());
		Assert.assertEquals(0, nodesInDirection.size());
	}

	@Test
	public void getNodesInDownDirectionAtEdge() {
		int size = 8;
		BoardImpl board = getSquareBoard(size);
		int lastNodeOnBoard = board.getNodes().size() - 1;
		Node node = board.getNodes().get(lastNodeOnBoard);
		List<Node> nodesInDirection = board.getNodesInDirection(Direction.DOWN, node.getXCoordinate(),
				node.getYCoordinate());
		// finding pieces down from the most lower right node should return 0
		Assert.assertEquals(0, nodesInDirection.size());
	}

	@Test
	public void getNodesInLeftDirectionAtEdge() {
		int size = 8;
		BoardImpl board = getSquareBoard(size);
		Node node = board.getNode(0, 0);
		List<Node> nodesInDirection = board.getNodesInDirection(Direction.LEFT, node.getXCoordinate(),
				node.getYCoordinate());
		// finding pieces left from the most upper left node should return 0
		Assert.assertEquals(0, nodesInDirection.size());
	}

	@Test
	public void getNodesInLeftDirection() {
		int size = 8;
		BoardImpl board = getSquareBoard(size);
		Node node = board.getNode(7, 0);
		List<Node> nodesInDirection = board.getNodesInDirection(Direction.LEFT, node.getXCoordinate(),
				node.getYCoordinate());
		// finding pieces left from the most upper right node should return 0
		Assert.assertEquals(7, nodesInDirection.size());
	}

	@Test
	public void getNodesInRightDirection() {
		int size = 8;
		BoardImpl board = getSquareBoard(size);
		Node node = board.getNode(0, 0);
		List<Node> nodesInDirection = board.getNodesInDirection(Direction.RIGHT, node.getXCoordinate(),
				node.getYCoordinate());
		// finding pieces right from the most upper left node should return 0
		Assert.assertEquals(7, nodesInDirection.size());
	}

}