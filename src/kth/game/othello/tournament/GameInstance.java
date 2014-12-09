package kth.game.othello.tournament;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;

public class GameInstance {
	private Othello othello;
	private Player startingPlayer;

	public GameInstance(Othello othello, Player startingPlayer) {
		this.othello = othello;
		this.startingPlayer = startingPlayer;
	}

	public Othello getOthello() {
		return othello;
	}

	public Player getStartingPlayer() {
		return startingPlayer;
	}
}
