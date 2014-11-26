package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.GameScore;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;

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
		for (int y = 1; y < nrOfPlayers + 1; y++) {
			Player player = mock(Player.class);
			when(player.getId()).thenReturn("player" + y);
			players.add(player);
		}
		return players;
	}

	private void setNode(String playerId, Node node) {
		when(node.getOccupantPlayerId()).thenReturn(playerId);
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
		List<ScoreItem> scoreitems = gameScore.getPlayersScore();
		for (int i = 0; i < scoreitems.size(); i++) {
			for (int j = i + 1; j < scoreitems.size(); j++) {
				assertTrue(scoreitems.get(i).getScore() <= scoreitems.get(j).getScore());
			}
		}
		// Player 1 have 1p
		// PLayer 2 have 2p
		// Player 4 have 3p
		// Player 3 have 4p
		assertEquals(scoreitems.get(0).getPlayerId(), players.get(0).getId());
		assertEquals(scoreitems.get(1).getPlayerId(), players.get(1).getId());
		assertEquals(scoreitems.get(3).getPlayerId(), players.get(2).getId());
		assertEquals(scoreitems.get(2).getPlayerId(), players.get(3).getId());
	}
}
