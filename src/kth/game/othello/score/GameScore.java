package kth.game.othello.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeNotification;
import kth.game.othello.player.Player;

public class GameScore implements Observer, Score {
	private HashMap<String, ScoreItem> scores;

	public GameScore(List<Player> players, List<Node> nodes) {
		scores = new HashMap<String, ScoreItem>();
		for (Player player : players) {
			scores.put(player.getId(), new ScoreItem(player.getId(), 0));
		}
		for (Node node : nodes) {
			node.addObserver(this);
			String playerOnNode = node.getOccupantPlayerId();
			if (playerOnNode != null) {
				incrementScore(playerOnNode);
			}
		}
	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		ArrayList<ScoreItem> scores = new ArrayList<ScoreItem>();
		Collections.sort(scores);
		return scores;
	}

	@Override
	public int getPoints(String playerId) {
		return scores.get(playerId).getScore();
	}

	@Override
	public void update(Observable o, Object arg) {
		NodeNotification notification = (NodeNotification) arg;
		incrementScore(notification.newPlayerId);
		if (notification.oldPlayerId != null) {
			decrementScore(notification.oldPlayerId);
		}
	}

	private void incrementScore(String playerId) {
		changeScore(playerId, 1);
	}

	private void decrementScore(String playerId) {
		changeScore(playerId, -1);
	}

	private void changeScore(String playerId, int value) {
		ScoreItem oldScore = scores.get(playerId);
		scores.put(playerId, new ScoreItem(playerId, oldScore.getScore() + value));
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO ?????????
	}

}
