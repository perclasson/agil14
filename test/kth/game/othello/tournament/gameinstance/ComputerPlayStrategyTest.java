package kth.game.othello.tournament.gameinstance;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.tournament.gameinstance.ComputerPlayStrategy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class ComputerPlayStrategyTest {

	@Test
	public void testPlay() {
		// Mock dependencies
		Othello othello = mock(Othello.class);
		Score score = mock(Score.class);
		Player player = mock(Player.class);
		when(othello.isActive()).thenReturn(true, true, true, false);
		when(othello.getScore()).thenReturn(score);

		// Create and play strategy
		ComputerPlayStrategy computerPlayStrategy = new ComputerPlayStrategy();
		Score returnedScore = computerPlayStrategy.play(othello, player);

		// Move should have been called
		verify(othello, times(3)).move();
		assertEquals(score, returnedScore);
	}
}
