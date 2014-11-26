package kth.game.othello.player;

import java.util.List;
import java.util.Random;

public class PlayerHandler {
	private Player playerInTurn;
	private List<Player> players;
	private Random random;

	/**
	 * Sets the player in turn to null.
	 * 
	 * @param players
	 * @param random
	 */
	public PlayerHandler(List<Player> players, Random random) {
		this.players = players;
		this.playerInTurn = null;
		this.random = random;
	}

	/**
	 * @return return player with currentPlayerIndex, it is that player in turn.
	 * @return null if currentPlayerIndex is -1.
	 */
	public Player getPlayerInTurn() {
		return playerInTurn;
	}

	/**
	 * Sets the current player in turn to given playerId.
	 * 
	 * @param playerId
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
	 * Change the player in turn to the next one given in our list of players.
	 */
	public void changePlayersTurn() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(playerInTurn)) {
				playerInTurn = players.get((i + 1) % players.size());
				break;
			}
		}
	}

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
