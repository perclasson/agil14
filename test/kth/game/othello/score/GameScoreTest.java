package kth.game.othello.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeOccupantNotification;
import kth.game.othello.player.Player;
import kth.game.othello.score.GameScore;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests of class GameScore.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameScoreTest {
	private List<Node> mockNodes(int order) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < order; y++) {
			for (int x = 0; x < order; x++) {
				Node node = mock(Node.class);
				when(node.getId()).thenReturn("x" + x + "y" + y);
				when(node.getOccupantPlayerId()).thenReturn(null);
				nodes.add(node);
			}
		}
		return nodes;
	}

	private List<Player> mockPlayers(int nrOfPlayers) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (int playerId = 1; playerId <= nrOfPlayers; playerId++) {
			Player player = mock(Player.class);
			when(player.getId()).thenReturn("player" + playerId);
			players.add(player);
		}
		return players;
	}

	private void setNode(String playerId, Node node) {
		when(node.getOccupantPlayerId()).thenReturn(playerId);
	}

	@Test
	public void testGameScore() {
		List<Player> players = new ArrayList<Player>();
		Player playerOne = Mockito.mock(Player.class);
		Player playerTwo = Mockito.mock(Player.class);

		String playerOneId = "1";
		String playerTwoId = "2";

		Mockito.when(playerOne.getId()).thenReturn(playerOneId);
		Mockito.when(playerTwo.getId()).thenReturn(playerTwoId);

		players.add(playerOne);
		players.add(playerTwo);

		GameScore score = new GameScore(players, new ArrayList<Node>());

		// Let's add one point to playerTwo
		NodeOccupantNotification not = Mockito.mock(NodeOccupantNotification.class);
		not.newOccupantPlayerId = playerTwoId;
		not.oldOccupantPlayerId = null;

		Observable o = Mockito.mock(Observable.class);
		score.update(o, not);

		// This means 1 score for playerTwo and 0 for playerOne
		assertEquals(0, score.getPoints(playerOneId));
		assertEquals(1, score.getPoints(playerTwoId));

		// When we get the scores in descending order, playerTwo is first
		List<ScoreItem> scores = score.getPlayersScore();
		assertEquals(playerTwoId, scores.get(0).getPlayerId());
		assertEquals(playerOneId, scores.get(1).getPlayerId());
	}

	@Test
	public void getPlayerScoretest() {
		List<Node> nodes = mockNodes(4);
		List<Player> players = mockPlayers(4);
		setNode(players.get(0).getId(), nodes.get(0));
		setNode(players.get(1).getId(), nodes.get(1));
		setNode(players.get(1).getId(), nodes.get(2));
		setNode(players.get(2).getId(), nodes.get(3));
		setNode(players.get(2).getId(), nodes.get(4));
		setNode(players.get(2).getId(), nodes.get(5));
		setNode(players.get(2).getId(), nodes.get(6));
		setNode(players.get(3).getId(), nodes.get(7));
		setNode(players.get(3).getId(), nodes.get(8));
		setNode(players.get(3).getId(), nodes.get(9));
		GameScore gameScore = new GameScore(players, nodes);
		List<ScoreItem> scoreItems = gameScore.getPlayersScore();

		// When we get the scores in descending order
		for (int i = 0; i < scoreItems.size() - 1; i++) {
			assertTrue(scoreItems.get(i).getScore() >= scoreItems.get(i + 1).getScore());
		}

		// Player 3 have 4p
		// Player 4 have 3p
		// PLayer 2 have 2p
		// Player 1 have 1p
		assertEquals(scoreItems.get(0).getPlayerId(), players.get(2).getId());
		assertEquals(scoreItems.get(1).getPlayerId(), players.get(3).getId());
		assertEquals(scoreItems.get(2).getPlayerId(), players.get(1).getId());
		assertEquals(scoreItems.get(3).getPlayerId(), players.get(0).getId());
	}
}
