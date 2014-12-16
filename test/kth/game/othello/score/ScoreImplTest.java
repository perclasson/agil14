package kth.game.othello.score;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ScoreImplTest {

	private final String player1 = "1";
	private final String player2 = "2";
	private final String player3 = "3";
	private final String player4 = "4";
	private final String player5 = "5";
	private final int initialPointsPlayer1 = 0;
	private final int initialPointsPlayer2 = 0;
	private final int initialPointsPlayer3 = 1;
	private final int initialPointsPlayer4 = 10;
	private final int initialPointsPlayer5 = 7;

	private List<ScoreItem> getInitialScores() {
		List<ScoreItem> scores = new ArrayList<ScoreItem>();
		scores.add(new ScoreItem(player1, initialPointsPlayer1));
		scores.add(new ScoreItem(player2, initialPointsPlayer2));
		return scores;
	}

	private List<ScoreItem> getBigScoreList() {
		List<ScoreItem> scores = new ArrayList<ScoreItem>();
		scores.add(new ScoreItem(player1, initialPointsPlayer1));
		scores.add(new ScoreItem(player2, initialPointsPlayer2));
		scores.add(new ScoreItem(player3, initialPointsPlayer3));
		scores.add(new ScoreItem(player4, initialPointsPlayer4));
		scores.add(new ScoreItem(player5, initialPointsPlayer5));
		return scores;
	}

	@Test
	public void updateAddsPointToPlayer() {
		// mock Node
		NodeImpl node = Mockito.mock(NodeImpl.class);
		Mockito.when(node.getOccupantPlayerId()).thenReturn(player2);

		ScoreImpl scoreImpl = new ScoreImpl(getInitialScores(), null);

		// update node previously occupied by null, now occupied by player2
		scoreImpl.update(node, null);

		int newPointsPlayer2 = scoreImpl.getPoints(player2);
		Assert.assertEquals(initialPointsPlayer2 + 1, newPointsPlayer2);
	}

	@Test
	public void updateRemovesPointFromPreviousPlayer() {
		// mock Node
		NodeImpl node = Mockito.mock(NodeImpl.class);
		Mockito.when(node.getOccupantPlayerId()).thenReturn(null);

		ScoreImpl scoreImpl = new ScoreImpl(getInitialScores(), null);

		// update node previously occupied by player1, now occupied by null
		scoreImpl.update(node, player1);

		int newPointsPlayer1 = scoreImpl.getPoints(player1);
		Assert.assertEquals(initialPointsPlayer1 - 1, newPointsPlayer1);
	}

	@Test
	public void observersAreNotifiedOnScoreChange() {
		// mock Node
		NodeImpl node = Mockito.mock(NodeImpl.class);
		Mockito.when(node.getOccupantPlayerId()).thenReturn(player2);

		List<String> expectedUpdatedPlayerIds = new ArrayList<String>();
		expectedUpdatedPlayerIds.add(player1);
		expectedUpdatedPlayerIds.add(player2);

		// mock Observer
		Observer observer = Mockito.mock(Observer.class);

		ScoreImpl scoreImpl = new ScoreImpl(getInitialScores(), null);
		scoreImpl.addObserver(observer);

		// update node previously occupied by player1, now occupied by player 2
		scoreImpl.update(node, player1);

		// verify that update has been called
		Mockito.verify(observer, Mockito.times(1)).update(scoreImpl, expectedUpdatedPlayerIds);
	}

	@Test
	public void observersAreNotNotifiedWhenNoScoreChange() {
		// mock Node
		NodeImpl node = Mockito.mock(NodeImpl.class);
		Mockito.when(node.getOccupantPlayerId()).thenReturn(player1);

		// if update was called it would be with an empty list
		List<String> expectedUpdatedPlayerIds = new ArrayList<String>();

		// mock Observer
		Observer observer = Mockito.mock(Observer.class);

		ScoreImpl scoreImpl = new ScoreImpl(getInitialScores(), null);
		scoreImpl.addObserver(observer);

		// update node previously occupied by player 1, now occupied by player 1
		scoreImpl.update(node, player1);

		// verify that update has NOT been called
		Mockito.verify(observer, Mockito.times(0)).update(scoreImpl, expectedUpdatedPlayerIds);
	}

	@Test
	public void playerScoresAreSortedInDecreasingOrder() {
		Comparator<ScoreItem> comparator = new DescendingComparator();
		ScoreImpl scoreImpl = new ScoreImpl(getBigScoreList(), comparator);
		List<ScoreItem> playersScore = scoreImpl.getPlayersScore();
		ScoreItem previousScore = playersScore.get(0);
		for (int i = 1; i < playersScore.size(); i++) {
			ScoreItem score = playersScore.get(i);
			Assert.assertTrue(previousScore.getScore() > score.getScore());
		}
	}

	@Test
	public void setInitialScoreUpdatesScore() {
		Comparator<ScoreItem> comparator = new DescendingComparator();
		ScoreImpl scoreImpl = new ScoreImpl(getInitialScores(), comparator);
		List<Node> initialNodes = new ArrayList<Node>();

		// mock nodes
		NodeImpl node1 = Mockito.mock(NodeImpl.class);
		Mockito.when(node1.getOccupantPlayerId()).thenReturn(player1);

		NodeImpl node2 = Mockito.mock(NodeImpl.class);
		Mockito.when(node2.getOccupantPlayerId()).thenReturn(player2);

		NodeImpl node3 = Mockito.mock(NodeImpl.class);
		Mockito.when(node3.getOccupantPlayerId()).thenReturn(player2);

		NodeImpl node4 = Mockito.mock(NodeImpl.class);
		Mockito.when(node4.getOccupantPlayerId()).thenReturn(null);

		initialNodes.add(node1);
		initialNodes.add(node2);
		initialNodes.add(node3);
		initialNodes.add(node4);

		scoreImpl.setInitialScore(initialNodes);

		int player1expectedScore = 1;
		int player2expectedScore = 2;

		List<ScoreItem> playersScore = scoreImpl.getPlayersScore();
		for (ScoreItem score : playersScore) {
			if (score.getPlayerId().equals(player1)) {
				Assert.assertEquals(player1expectedScore, score.getScore());
			} else if (score.getPlayerId().equals(player2)) {
				Assert.assertEquals(player2expectedScore, score.getScore());
			} else {
				// should not happen
				Assert.fail();
			}
		}
	}
}
