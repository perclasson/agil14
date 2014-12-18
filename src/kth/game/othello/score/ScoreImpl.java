package kth.game.othello.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;

/**
 * An implementation of the {@link Score} interface. This class also extends {@link Observable} and can be used as an
 * observer of {@link NodeImpl}.
 *
 */
public class ScoreImpl extends Observable implements Score, Observer {

	List<ScoreItem> scores;
	private Comparator<ScoreItem> comparator;

	public ScoreImpl(List<ScoreItem> scores, Comparator<ScoreItem> comparator) {
		this.scores = scores;
		this.comparator = comparator;
	}

	@Override
	public void addObserver(Observer observer) {
		super.addObserver(observer);
	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		Collections.sort(scores, comparator);
		return scores;
	}

	@Override
	public int getPoints(String playerId) {
		for (ScoreItem score : scores) {
			if (score.getPlayerId().equals(playerId)) {
				return score.getScore();
			}
		}
		return 0;
	}

	/**
	 * Modifies the score by examining the {@link NodeImpl} passed as observable and the previously occupying player.
	 * 
	 * @param o
	 *            the node that has been modified.
	 * @param arg
	 *            the previous player id.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// Receives node as observable and previous player id as arg
		List<String> updatedPlayers = new ArrayList<String>();
		String newPlayerId = ((NodeImpl) o).getOccupantPlayerId();
		String oldPlayerId = null;
		if (arg != null) {
			oldPlayerId = arg.toString();
		}
		for (int i = 0; i < scores.size(); i++) {
			ScoreItem scoreItem = scores.get(i);
			if (scoreItem.getPlayerId().equals(newPlayerId)) {
				updateScore(i, newPlayerId, scoreItem.getScore() + 1);
				updatedPlayers.add(newPlayerId);
			}
			scoreItem = scores.get(i);
			if (scoreItem.getPlayerId().equals(oldPlayerId)) {
				updateScore(i, oldPlayerId, scoreItem.getScore() - 1);
				updatedPlayers.add(oldPlayerId);
			}
		}
		super.notifyObservers(updatedPlayers);
	}

	private void updateScore(int index, String playerId, int newScore) {
		scores.set(index, new ScoreItem(playerId, newScore));
		super.setChanged();
	}

	/**
	 * Change score for player id
	 * 
	 * @param playerId
	 *            The player to change score for.
	 * @param delta
	 *            The change in score we want to make for player.
	 */
	public void changeScore(String playerId, int delta) {
		List<String> updatedPlayers = new ArrayList<String>();
		for (int i = 0; i < scores.size(); i++) {
			ScoreItem scoreItem = scores.get(i);
			if (scoreItem.getPlayerId().equals(playerId)) {
				updateScore(i, playerId, scoreItem.getScore() + delta);
				updatedPlayers.add(playerId);
			}
		}
		super.notifyObservers(updatedPlayers);
	}

	/**
	 * Set the initial score.
	 * 
	 * @param nodes
	 *            a list of nodes used to calculate the score.
	 */
	public void setInitialScore(List<Node> nodes) {
		for (Node node : nodes) {
			update((Observable) node, null);
		}
	}
}
