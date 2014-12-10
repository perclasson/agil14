package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.tournament.gameinstance.ComputerPlayStrategy;
import kth.game.othello.tournament.gameinstance.GameInstance;
import kth.game.othello.tournament.gameinstance.GamePlayStrategy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class TournamentTest {

	public GameInstance mockGameInstance(Score score) {
		GameInstance game = mock(GameInstance.class);
		GamePlayStrategy strategy = mock(ComputerPlayStrategy.class);
		Othello othello = mock(Othello.class);
		Player player = mock(Player.class);

		when(game.getPlayStrategy()).thenReturn(strategy);
		when(game.getOthello()).thenReturn(othello);
		when(game.getStartingPlayer()).thenReturn(player);

		when(game.getPlayStrategy()).thenReturn(strategy);
		when(strategy.play(othello, game.getStartingPlayer())).thenReturn(score);
		return game;
	}

	@Test
	public void testPlay() {
		// Mock three games with three scores
		Score scoreOne = mock(Score.class);
		GameInstance gameOne = mockGameInstance(scoreOne);
		Score scoreTwo = mock(Score.class);
		GameInstance gameTwo = mockGameInstance(scoreTwo);
		Score scoreThree = mock(Score.class);
		GameInstance gameThree = mockGameInstance(scoreThree);

		List<GameInstance> gameInstances = new ArrayList<GameInstance>();
		gameInstances.add(gameOne);
		gameInstances.add(gameTwo);
		gameInstances.add(gameThree);

		// Play the tournament
		PlayerResult result = mock(PlayerResult.class);
		Tournament tournament = new Tournament(gameInstances, result);
		PlayerResult newResult = tournament.play();

		assertEquals(result, newResult);

		// Verify that three scores were added
		verify(newResult).add(scoreOne);
		verify(newResult).add(scoreTwo);
		verify(newResult).add(scoreThree);
	}
}
