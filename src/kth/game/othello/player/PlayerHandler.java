package kth.game.othello.player;

import java.util.List;
import java.util.Random;

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

	/**
	 * Creates a PlayerHandler object that keeps track of the players and player in turn.
	 * 
	 * @param players
	 *            A list of Player objects to handle.
	 * @param random
	 *            A random generator.
	 */
	public PlayerHandler(List<Player> players, Random random) {
		this.players = players;
		this.playerInTurn = null;
		this.random = random;
	}

	/**
	 * The player in turn.
	 * 
	 * @return the player in turn or null if there is none.
	 */
	public Player getPlayerInTurn() {
		return playerInTurn;
	}

	/**
	 * Sets the current player in turn to the given playerId.
	 * 
	 * @param playerId
	 *            The player id of the player that is set to be in turn.
	 */
	public void setPlayerInTurn(String playerId) {
		if (playerId == null) {
			// Game is ended
			playerInTurn = null;
			return;
		}

		for (Player player : players) {
			if (player.getId().equals(playerId)) {
				playerInTurn = player;
				return;
			}
		}
	}

	/**
	 * Change the player in turn to the next one.
	 */
	public void changePlayersTurn() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(playerInTurn)) {
				playerInTurn = players.get((i + 1) % players.size());
				break;
			}
		}
	}

	/**
	 * A list of players in this handler.
	 * 
	 * @return the players.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Puts a random player in turn.
	 */
	public void setRandomPlayerInTurn() {
		int playerInTurnIndex = random.nextInt(players.size());
		playerInTurn = players.get(playerInTurnIndex);
	}
}
