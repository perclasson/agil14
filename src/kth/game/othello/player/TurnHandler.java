package kth.game.othello.player;

import java.util.List;
import java.util.Random;

import kth.game.othello.rules.MoveValidator;

/**
 * The responsibility of the TurnHandler is to keep track of which player is in turn and to pass the turn to the correct
 * player. It is also responsible for the starting player.
 */
public class TurnHandler {

	private List<Player> players;
	private MoveValidator moveValidator;
	private Player playerInTurn;
	private boolean startingPlayerSet;

	public TurnHandler(List<Player> players, MoveValidator moveValidator) {
		this.players = players;
		this.moveValidator = moveValidator;
	}

	/**
	 * Sets the starting player to the given player
	 *
	 * @param startingPlayerId the id for the starting player
	 * @throws IllegalStateException is a starting player is already set
	 */
	public void setStartingPlayer(String startingPlayerId) {
		if (!startingPlayerSet) {
			for (Player player : players) {
				if (player.getId().equals(startingPlayerId)) {
					playerInTurn = player;
					startingPlayerSet = true;
					break;
				}
			}
		} else {
			throw new IllegalStateException("Game is already started");
		}
	}

	/**
	 *
	 * Sets the player in turn to the given player. Nothing will be done if the given player does not exist among
	 * players. Use nextPlayer() when possible.
	 *
	 * @param playerInTurnId the player to be set
	 */
	public void setPlayerInTurn(String playerInTurnId) {
		for (Player player : players) {
			if (player.getId().equals(playerInTurnId)) {
				playerInTurn = player;
				break;
			}
		}
	}

	/**
	 * Returns a randomly selected player.
	 *
	 * @param random the random used to determine the player.
	 */
	public String getRandomPlayer(Random random) {
		return players.get(random.nextInt(players.size())).getId();
	}

	/**
	 * Hands over the turn to the next player which can make a move. If the game is over, the player in turn will not be
	 * changed.
	 */
	public void nextPlayer() {

		if (!moveValidator.isActive(players)) {
			return;
		}

		do {
			int index = players.indexOf(playerInTurn);
			playerInTurn = players.get((index + 1) % players.size());
		} while (!moveValidator.hasValidMove(playerInTurn.getId()));
	}

	/**
	 *
	 * @return the player in turn
	 */
	public Player getPlayerInTurn() {
		return playerInTurn;
	}
}
