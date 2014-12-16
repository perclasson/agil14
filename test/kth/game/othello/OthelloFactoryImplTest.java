package kth.game.othello;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;

import org.junit.Assert;
import org.junit.Test;

public class OthelloFactoryImplTest {

	private OthelloFactoryImpl getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	@Test
	public void createComputerGameCreatesTwoComputerPlayers() {
		Othello game = getOthelloFactory().createComputerGame();
		List<Player> players = game.getPlayers();
		Player.Type expected = Player.Type.COMPUTER;
		for (Player player : players) {
			Player.Type actual = player.getType();
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void createHumanGameCreatesTwoHumanPlayers() {
		Othello game = getOthelloFactory().createHumanGame();
		List<Player> players = game.getPlayers();
		Player.Type expected = Player.Type.HUMAN;
		for (Player player : players) {
			Player.Type actual = player.getType();
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void createHumanVsComputerGameCreatesHumanAndComputerPlayers() {
		Othello game = getOthelloFactory().createHumanVersusComputerGame();
		List<Player> players = game.getPlayers();
		if (players.get(0).getType() == Type.HUMAN) {
			Assert.assertTrue(players.get(1).getType() == Type.COMPUTER);
		} else {
			Assert.assertTrue(players.get(0).getType() == Type.COMPUTER);
			Assert.assertTrue(players.get(1).getType() == Type.HUMAN);
		}
	}

	@Test
	public void translationWorksAsExpected() throws NoSuchMethodException {
		Set<NodeData> nodesData = new HashSet<NodeData>();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				nodesData.add(new NodeData(x, y));
			}
		}

		List<Node> nodes = getOthelloFactory().translateNodesData(nodesData);

		// Check that every node exists.
		for (Node node : nodes) {
			NodeData nodeData = new NodeData(node.getXCoordinate(), node.getYCoordinate());
			Assert.assertTrue(nodesData.remove(nodeData));
		}

		// Check that size is equal.
		Assert.assertEquals(0, nodesData.size());
	}

	@Test
	public void translateSortsNodesInCorrectOrder() {
		Set<NodeData> nodesData = new HashSet<NodeData>();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				nodesData.add(new NodeData(x, y));
			}
		}

		List<Node> nodes = getOthelloFactory().translateNodesData(nodesData);

		Node previousNode = nodes.get(0);
		for (int i = 1; i < nodes.size(); i++) {
			Node nextNode = nodes.get(i);
			int previousY = previousNode.getYCoordinate();
			int nextY = nextNode.getYCoordinate();
			int previousX = previousNode.getXCoordinate();
			int nextX = nextNode.getXCoordinate();

			if (previousY == nextY) {
				Assert.assertTrue(previousX < nextX);
			} else {
				Assert.assertTrue(previousY < nextY);
			}
			previousNode = nextNode;
		}
	}

}
