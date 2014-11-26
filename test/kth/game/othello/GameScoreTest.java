package kth.game.othello;

import static org.junit.Assert.assertEquals;

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

public class GameScoreTest {

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
		assertEquals(scores.get(0).getPlayerId(), playerTwoId);
		assertEquals(scores.get(1).getPlayerId(), playerOneId);
	}

}
