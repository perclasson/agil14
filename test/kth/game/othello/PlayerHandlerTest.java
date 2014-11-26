package kth.game.othello;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test of class PlayerHandler.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class PlayerHandlerTest {

	@Test
	public void testPlayerInTurn() {
		List<Player> players = new ArrayList<Player>();

		// Init players
		Player playerOne = Mockito.mock(Player.class);
		String playerOneId = "1";
		Mockito.when(playerOne.getId()).thenReturn(playerOneId);
		Player playerTwo = Mockito.mock(Player.class);
		String playerTwoId = "2";
		Mockito.when(playerTwo.getId()).thenReturn(playerTwoId);

		players.add(playerOne);
		players.add(playerTwo);

		Random random = Mockito.mock(Random.class);

		PlayerHandler handler = new PlayerHandler(players, random);

		handler.setPlayerInTurn(playerOneId);
		assertEquals(playerOne, handler.getPlayerInTurn());

		handler.changePlayersTurn();
		assertEquals(playerTwo, handler.getPlayerInTurn());

		// Mock random to first player
		Mockito.when(random.nextInt(players.size())).thenReturn(0);

		handler.setRandomPlayerInTurn();
		assertEquals(playerOne, handler.getPlayerInTurn());
	}
}
