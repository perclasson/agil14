package kth.game.othello.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.rules.MoveValidator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class TurnHandlerTest {

	public List<Player> getListOf6HumanPlayers() {
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getHumanPlayer("Player 1", "Player 1"));
		players.add(PlayerImpl.getHumanPlayer("Player 2", "Player 2"));
		players.add(PlayerImpl.getHumanPlayer("Player 3", "Player 3"));
		players.add(PlayerImpl.getHumanPlayer("Player 4", "Player 4"));
		players.add(PlayerImpl.getHumanPlayer("Player 5", "Player 5"));
		players.add(PlayerImpl.getHumanPlayer("Player 6", "Player 6"));
		return players;
	}

	public List<Player> getListOf2HumanPlayers() {
		List<Player> players = new ArrayList<Player>();
		PlayerImpl player1 = PlayerImpl.getHumanPlayer("123", "Bosse");
		PlayerImpl player2 = PlayerImpl.getHumanPlayer("321", "Sverker");
		players.add(player1);
		players.add(player2);
		return players;
	}

	@Test
	public void nextPlayerIncrementsWithOne() {

		// add players
		List<Player> players = getListOf6HumanPlayers();

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);
		Mockito.when(moveValidator.isActive(players)).thenReturn(true);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		turnHandler.setStartingPlayer(players.get(4).getId());
		Player[] expectedPlayersToHavePlayed = new Player[] { players.get(4), players.get(5), players.get(0),
				players.get(1) };
		Player[] playersThatHavePlayed = new Player[4];
		for (int i = 0; i < playersThatHavePlayed.length; i++) {
			playersThatHavePlayed[i] = turnHandler.getPlayerInTurn();
			turnHandler.nextPlayer();
		}
		Assert.assertArrayEquals(expectedPlayersToHavePlayed, playersThatHavePlayed);
	}

	@Test
	public void nextPlayerDoesNotChangeWhenEndOfGame() {
		List<Player> players = getListOf2HumanPlayers();

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(false);
		Mockito.when(moveValidator.isActive(players)).thenReturn(false);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		turnHandler.setStartingPlayer(players.get(0).getId());
		turnHandler.nextPlayer();

		Assert.assertEquals(players.get(0).getId(), turnHandler.getPlayerInTurn().getId());
	}

	@Test
	public void skipPlayersWithNoAvailableMoves() {
		List<Player> players = getListOf2HumanPlayers();
		players.add(PlayerImpl.getComputerPlayer("213", "Arnold", null));

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(players.get(0).getId())).thenReturn(true);
		Mockito.when(moveValidator.hasValidMove(players.get(1).getId())).thenReturn(false);
		Mockito.when(moveValidator.hasValidMove(players.get(2).getId())).thenReturn(false);

		Mockito.when(moveValidator.isActive(players)).thenReturn(true);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		turnHandler.setStartingPlayer(players.get(0).getId());
		turnHandler.nextPlayer();

		Assert.assertEquals(players.get(0).getId(), turnHandler.getPlayerInTurn().getId());
	}

	@Test
	public void startingPlayerRandomTest() {
		// mock random
		Random random = Mockito.mock(Random.class);
		int n = 2;
		Mockito.when(random.nextInt(n)).thenReturn(1);

		// players
		List<Player> players = getListOf2HumanPlayers();
		String player2Id = players.get(1).getId();

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		String randomPlayer = turnHandler.getRandomPlayer(random);

		Assert.assertEquals(player2Id, randomPlayer);
	}

	@Test(expected = IllegalStateException.class)
	public void multipleSetStartingPlayerThrowsException() {
		List<Player> players = getListOf2HumanPlayers();

		TurnHandler turnHandler = new TurnHandler(players, null);

		turnHandler.setStartingPlayer(players.get(0).getId());
		// second call should fail
		turnHandler.setStartingPlayer(players.get(0).getId());
	}

	@Test
	public void setStartingPlayerAssignsPlayerInTurn() {
		List<Player> players = new ArrayList<Player>();
		PlayerImpl player1 = PlayerImpl.getHumanPlayer("123", "Bosse");
		players.add(player1);

		TurnHandler turnHandler = new TurnHandler(players, null);

		turnHandler.setStartingPlayer(player1.getId());

		Assert.assertNotNull(turnHandler.getPlayerInTurn());
	}
}