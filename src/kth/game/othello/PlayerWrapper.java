package kth.game.othello;

import java.util.List;
import java.util.Random;

import kth.game.othello.player.Player;

public class PlayerWrapper {
	private int playerIndexInTurn;
	private List<Player> players;
	private Random random;

	public PlayerWrapper(List<Player> players, int playerIndexInTurn, Random random) {
		if (playerIndexInTurn < 0 || playerIndexInTurn >= players.size()) {
			throw new IllegalArgumentException("PlayerIndexInTurn must be between 0 and " + (players.size() - 1) + ".");
		}
		this.players = players;
		this.random = random;
		this.playerIndexInTurn = playerIndexInTurn;
	}

	public Player getPlayerInTurn() {
		return players.get(playerIndexInTurn);
	}

	public void setPlayerInTurn(String playerId) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getId().equals(playerId)) {
				playerIndexInTurn = i;
				return;
			}
		}
	}

	public void changePlayersTurn() {
		playerIndexInTurn = (playerIndexInTurn + 1) % players.size();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setRandomPlayerInTurn() {
		playerIndexInTurn = random.nextInt(players.size());
	}
}
