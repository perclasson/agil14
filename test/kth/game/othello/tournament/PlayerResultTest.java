package kth.game.othello.tournament;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerResultTest {

	@Test
	public void testAdd() {
		// Mock some players
		String playerOneId = "playerOne";
		String playerTwoId = "playerTwo";
		String playerThreeId = "playerThree";
		Player playerOne = mock(Player.class);
		Player playerTwo = mock(Player.class);
		Player playerThree = mock(Player.class);
		when(playerOne.getId()).thenReturn(playerOneId);
		when(playerTwo.getId()).thenReturn(playerTwoId);
		when(playerThree.getId()).thenReturn(playerThreeId);
		List<Player> players = new ArrayList<Player>();
		players.add(playerOne);
		players.add(playerTwo);
		players.add(playerThree);

		// Mock scores
		Score score = mock(Score.class);
		List<ScoreItem> scores = new ArrayList<ScoreItem>();
		when(score.getPlayersScore()).thenReturn(scores);

		ScoreItem scoreOne = mock(ScoreItem.class);
		ScoreItem scoreTwo = mock(ScoreItem.class);
		ScoreItem scoreThree = mock(ScoreItem.class);
		when(scoreOne.getPlayerId()).thenReturn(playerOneId);
		when(scoreTwo.getPlayerId()).thenReturn(playerTwoId);
		when(scoreThree.getPlayerId()).thenReturn(playerThreeId);

		// Create player result
		PlayerResult playerResult = new PlayerResult(players);

		// Round 1
		when(scoreOne.getScore()).thenReturn(10);
		when(scoreTwo.getScore()).thenReturn(5);
		when(scoreThree.getScore()).thenReturn(0);
		scores.add(scoreOne);
		scores.add(scoreTwo);
		scores.add(scoreThree);

		playerResult.add(score);

		assertEquals(1, playerResult.getPlayerScore(playerOneId));
		assertEquals(0, playerResult.getPlayerScore(playerTwoId));
		assertEquals(0, playerResult.getPlayerScore(playerThreeId));
		scores.clear();

		// Round 2
		when(scoreOne.getScore()).thenReturn(5);
		when(scoreTwo.getScore()).thenReturn(10);
		when(scoreThree.getScore()).thenReturn(5);
		scores.add(scoreOne);
		scores.add(scoreTwo);
		scores.add(scoreThree);

		playerResult.add(score);

		assertEquals(1, playerResult.getPlayerScore(playerOneId));
		assertEquals(1, playerResult.getPlayerScore(playerTwoId));
		assertEquals(0, playerResult.getPlayerScore(playerThreeId));
		scores.clear();

		// Round 3
		when(scoreOne.getScore()).thenReturn(10);
		when(scoreTwo.getScore()).thenReturn(9);
		when(scoreThree.getScore()).thenReturn(10);
		scores.add(scoreOne);
		scores.add(scoreTwo);
		scores.add(scoreThree);

		playerResult.add(score);

		assertEquals(1, playerResult.getPlayerScore(playerOneId));
		assertEquals(1, playerResult.getPlayerScore(playerTwoId));
		assertEquals(0, playerResult.getPlayerScore(playerThreeId));
		scores.clear();

		// Round 4
		when(scoreOne.getScore()).thenReturn(9);
		when(scoreTwo.getScore()).thenReturn(9);
		when(scoreThree.getScore()).thenReturn(10);
		scores.add(scoreOne);
		scores.add(scoreTwo);
		scores.add(scoreThree);

		playerResult.add(score);

		assertEquals(1, playerResult.getPlayerScore(playerOneId));
		assertEquals(1, playerResult.getPlayerScore(playerTwoId));
		assertEquals(1, playerResult.getPlayerScore(playerThreeId));
		scores.clear();

		// Round 5
		when(scoreOne.getScore()).thenReturn(11);
		when(scoreTwo.getScore()).thenReturn(9);
		when(scoreThree.getScore()).thenReturn(10);
		scores.add(scoreOne);
		scores.add(scoreTwo);
		scores.add(scoreThree);

		playerResult.add(score);

		assertEquals(2, playerResult.getPlayerScore(playerOneId));
		assertEquals(1, playerResult.getPlayerScore(playerTwoId));
		assertEquals(1, playerResult.getPlayerScore(playerThreeId));
		scores.clear();
	}
}
