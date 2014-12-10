package kth.game.othello.player;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.rules.Rules;

import org.junit.Test;

/**
 * Unit test of class PlayerHandler.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class PlayerHandlerTest {

	@Test
	public void testChangePlayerInTurn() {
		// Mock players
		Player playerOne = mock(Player.class);
		String playerOneId = "1";
		Player playerTwo = mock(Player.class);
		String playerTwoId = "2";
		Player playerThree = mock(Player.class);
		String playerThreeId = "3";
		when(playerOne.getId()).thenReturn(playerOneId);
		when(playerTwo.getId()).thenReturn(playerTwoId);
		when(playerThree.getId()).thenReturn(playerThreeId);
		List<Player> players = new ArrayList<Player>();
		players.add(playerOne);
		players.add(playerTwo);
		players.add(playerThree);

		// Create player handler
		Random random = mock(Random.class);
		Rules rules = mock(Rules.class);
		PlayerHandler playerHandler = new PlayerHandler(players, rules, random);

		playerHandler.setPlayerInTurn(playerOneId);
		assertEquals(playerOne, playerHandler.getPlayerInTurn());

		// Change the player in turn
		playerHandler.changePlayerInTurn();

		// No player had any valid move so player in turn should be null
		// afterwards
		assertEquals(playerHandler.getPlayerInTurn(), null);

		// Make the player have valid moves
		when(rules.hasValidMove("1")).thenReturn(true);
		when(rules.hasValidMove("2")).thenReturn(true);
		when(rules.hasValidMove("3")).thenReturn(true);

		// Change from null to player one
		playerHandler.changePlayerInTurn();
		assertEquals(playerOne, playerHandler.getPlayerInTurn());

		// Change from player one to player two
		playerHandler.changePlayerInTurn();
		assertEquals(playerTwo, playerHandler.getPlayerInTurn());

		// Change from player two to player three
		playerHandler.changePlayerInTurn();

		assertEquals(playerThree, playerHandler.getPlayerInTurn());

		// Change from player three to player one
		playerHandler.changePlayerInTurn();
		assertEquals(playerOne, playerHandler.getPlayerInTurn());

	}
}
