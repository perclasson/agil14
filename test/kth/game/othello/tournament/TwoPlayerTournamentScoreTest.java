package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreImpl;
import kth.game.othello.score.ScoreItem;

import org.junit.Assert;
import org.junit.Test;

public class TwoPlayerTournamentScoreTest {

	private final String player1Id = "1";
	private final String player2Id = "2";

	private final int game1ScorePlayer1 = 32;
	private final int game1ScorePlayer2 = 16;
	private final int game2ScorePlayer1 = 1;
	private final int game2ScorePlayer2 = 61;

	private List<ScoreItem> getGameScoreList(int player1Score, int player2Score) {
		List<ScoreItem> scores = new ArrayList<ScoreItem>();
		scores.add(new ScoreItem(player1Id, player1Score));
		scores.add(new ScoreItem(player2Id, player2Score));
		return scores;
	}

	@Test
	public void testSetScoreFromGame() {
		Score game1Score = new ScoreImpl(getGameScoreList(game1ScorePlayer1, game1ScorePlayer2), null);
		Score game2Score = new ScoreImpl(getGameScoreList(game2ScorePlayer1, game2ScorePlayer2), null);

		Player player1 = PlayerImpl.getComputerPlayer(player1Id, player1Id, null);
		Player player2 = PlayerImpl.getComputerPlayer(player2Id, player2Id, null);

		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		TwoPlayerTournamentScore twoPlayerTournamentScore = new TwoPlayerTournamentScore(players);

		twoPlayerTournamentScore.updateScoreFromGame(player1, player2, game1Score, new TwoPlayerTournamentAnnouncer());
		int expectedPlayer1Score = Math.max(game1ScorePlayer1 - game1ScorePlayer2, 0);
		int expectedPlayer2Score = Math.max(game1ScorePlayer2 - game1ScorePlayer1, 0);
		Assert.assertEquals(expectedPlayer1Score, twoPlayerTournamentScore.getScore().get(player1Id).intValue());
		Assert.assertEquals(expectedPlayer2Score, twoPlayerTournamentScore.getScore().get(player2Id).intValue());

		twoPlayerTournamentScore.updateScoreFromGame(player1, player2, game2Score, new TwoPlayerTournamentAnnouncer());
		expectedPlayer1Score = expectedPlayer1Score + Math.max(game2ScorePlayer1 - game2ScorePlayer2, 0);
		expectedPlayer2Score = expectedPlayer2Score + Math.max(game2ScorePlayer2 - game2ScorePlayer1, 0);
		Assert.assertEquals(expectedPlayer1Score, twoPlayerTournamentScore.getScore().get(player1Id).intValue());
		Assert.assertEquals(expectedPlayer2Score, twoPlayerTournamentScore.getScore().get(player2Id).intValue());

	}

}