package kth.game.othello.player;

import java.util.List;
import java.util.Random;

public class PlayerHandler {
	private int currentPlayerIndex;
	private List<Player> players;
	private Random random;

	/**
	 * Initializes a PlayerHandler, who take cares of the players.
	 * 
	 * @param players
	 * @param random
	 * @throw IllegalArgumentException if one of the players index is negative or bigger than the amount of players.
	 */
	public PlayerHandler(List<Player> players, Random random) {
		if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
			throw new IllegalArgumentException("Player index must be between 0 and " + (players.size() - 1) + ".");
		}
		this.players = players;
		this.currentPlayerIndex = 0;
		this.random = random;
	}

	/**
	 * @return return player with currentPlayerIndex, it is that player in turn.
	 * @return null if currentPlayerIndex is -1.
	 */
	public Player getPlayerInTurn() {
		if (currentPlayerIndex == -1) {
			return null;
		} else {
			return players.get(currentPlayerIndex);
		}
	}

	/**
	 * Sets the currentPlayerIndex to -1, if playerID is null, else switch currentPlayerIndex to the one that
	 * corresponds to playerID
	 * 
	 * @param playerId
	 */
	public void setPlayerInTurn(String playerId) {
		if (playerId == null) {
			currentPlayerIndex = -1;
		} else {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getId().equals(playerId)) {
					currentPlayerIndex = i;
					return;
				}
			}
		}
	}

	public void changePlayersTurn() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setRandomPlayerInTurn() {
		currentPlayerIndex = random.nextInt(players.size());
	}
}
