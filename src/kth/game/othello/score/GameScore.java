package kth.game.othello.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeOccupantNotification;
import kth.game.othello.player.Player;

/**
 * The responsibility of this class is to observe nodes so that we can count the score.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class GameScore extends Observable implements Observer, Score {
	private HashMap<String, ScoreItem> scores;

	/**
	 * Creates a GameScore object that keeps track of the score of players by listening to the given nodes.
	 * 
	 * @param players
	 *            The players score to be tracked.
	 * @param nodes
	 *            The nodes that should be listened to for score changes.
	 */
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
		List<ScoreItem> scoreItems = new ArrayList<ScoreItem>(scores.values());
		Collections.sort(scoreItems);
		return scoreItems;

	}

	@Override
	public int getPoints(String playerId) {
		return scores.get(playerId).getScore();
	}

	@Override
	public void update(Observable o, Object arg) {
		NodeOccupantNotification notification = (NodeOccupantNotification) arg;
		if (notification.newOccupantPlayerId != null) {
			incrementScore(notification.newOccupantPlayerId);
		}
		if (notification.oldOccupantPlayerId != null) {
			decrementScore(notification.oldOccupantPlayerId);
		}
	}

	private void incrementScore(String playerId) {
		changeScore(playerId, 1);
	}

	private void decrementScore(String playerId) {
		changeScore(playerId, -1);
	}

	/**
	 * Notify observers with the new scores.
	 * 
	 * @param playerId
	 *            The id of the player.
	 * @param value
	 *            The new score for the player.
	 */
	private void changeScore(String playerId, int value) {
		ScoreItem oldScore = scores.get(playerId);
		setChanged();
		scores.put(playerId, new ScoreItem(playerId, oldScore.getScore() + value));
		List<String> changedPlayers = new ArrayList<String>();
		changedPlayers.add(playerId);
		notifyObservers(changedPlayers);
	}
}
