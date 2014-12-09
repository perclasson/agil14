package kth.game.othello.player;

import java.util.List;
import java.util.Random;

import kth.game.othello.rules.Rules;

/**
 * The responsibility of this class is to handle the players of the game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class PlayerHandler {
	private Player playerInTurn;
	private List<Player> players;
	private Random random;
	private Rules rules;

	/**
	 * Creates a PlayerHandler object that keeps track of the players and player in turn.
	 * 
	 * @param players
	 *            A list of Player objects to handle.
	 * @param random
	 *            A random generator.
	 */
	public PlayerHandler(List<Player> players, Rules rules, Random random) {
		this.players = players;
		this.playerInTurn = null;
		this.random = random;
		this.rules = rules;
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public Player getPlayerInTurn() {
		return playerInTurn;
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the current player in turn to the Player with the given playerId or null if it the player id is not found.
	 * 
	 * @param playerId
	 *            The player id of the player that is set to be in turn or null if playerId does not match any player.
	 */
	public void setPlayerInTurn(String playerId) {
		for (Player player : players) {
			if (player.getId().equals(playerId)) {
				playerInTurn = player;
				return;
			}
		}
		playerInTurn = null;
	}

	/**
	 * Change the player in turn to the next one. If no player can move, the player in turn will be set to null.
	 */
	public void changePlayerInTurn() {
		int playerInTurnIndex = getPlayerInTurnIndex();
		int nextPlayerIndex = 0;

		// If there is a player in turn start with next index
		if (playerInTurnIndex != -1) {
			nextPlayerIndex = getNextPlayerIndex(playerInTurnIndex);
		}

		// Rotate until we have tried all players or found one
		Player nextPlayer = null;
		while (nextPlayerIndex != playerInTurnIndex) {
			Player potentialPlayer = players.get(nextPlayerIndex);
			// Check if the player can move
			if (rules.hasValidMove(potentialPlayer.getId())) {
				nextPlayer = potentialPlayer;
				break;
			}
			nextPlayerIndex = getNextPlayerIndex(nextPlayerIndex);
		}

		// Set player in turn to found player or null
		playerInTurn = nextPlayer;

		// If game is finished
		if (playerInTurn == null) {
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Puts a random player in turn.
	 */
	public void setRandomPlayerInTurn() {
		int playerInTurnIndex = random.nextInt(players.size());
		playerInTurn = players.get(playerInTurnIndex);
	}

	private int getNextPlayerIndex(int id) {
		return (id + 1) % players.size();
	}

	private int getPlayerInTurnIndex() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(playerInTurn)) {
				return i;
			}
		}
		return -1;
	}
}
