package kth.game.othello.score;

import java.util.List;
import java.util.Observer;

import kth.game.othello.player.Player;

public class ScoreImpl implements Score {
	List<Player> players;

	public ScoreImpl(List<Player> players) {
		this.players = players;
	}

	@Override
	public void addObserver(Observer observer) {

	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPoints(String playerId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
