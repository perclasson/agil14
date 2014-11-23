package kth.game.othello.player;

import java.util.List;
import java.util.Random;

public class PlayerHandler {
	private int currentPlayerIndex;
	private List<Player> players;
	private Random random;

	public PlayerHandler(List<Player> players, Random random) {
		if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
			throw new IllegalArgumentException("Player index must be between 0 and " + (players.size() - 1) + ".");
		}
		this.players = players;
		this.currentPlayerIndex = 0;
		this.random = random;
	}

	public Player getPlayerInTurn() {
		if (currentPlayerIndex == -1) {
			return null;
		} else {
			return players.get(currentPlayerIndex);
		}
	}

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
