package kth.game.othello.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeNotification;
import kth.game.othello.player.Player;

public class ScoreImpl implements Observer, Score {
	private HashMap<String, ScoreItem> scores;

	public ScoreImpl(List<Player> players, List<Node> nodes) {
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
		return new ArrayList<ScoreItem>(scores.values()); // TODO sort
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
