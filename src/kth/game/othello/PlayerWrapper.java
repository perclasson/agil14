package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.Player;

public class PlayerWrapper {
	private Player black;
	private Player white;
	private boolean isBlackTurn;
	private Random random;
	
	public PlayerWrapper(PlayerImpl black, PlayerImpl white, Random random) {
		this.black = black;
		this.white = white;
		this.random = random;
	}
	
	public Player getPlayerInTurn() {
		return isBlackTurn ? black : white;
	}

	public void setPlayerInTurn(String playerId) {
		isBlackTurn = black.getId().equals(playerId);
	}

	void changePlayersTurn() {
		isBlackTurn = !isBlackTurn;
	}

	public List<Player> getPlayers() {
		return Arrays.asList(white, black);
	}

	public void setRandomPlayerInTurn() {
		isBlackTurn = random.nextBoolean();
	}
}
