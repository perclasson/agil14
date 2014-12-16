package kth.game.othello.score;

import java.util.Comparator;

/**
 * An implementation of {@link Comparator}, comparing two {@link ScoreItem}s in descending order.
 *
 */
public class DescendingComparator implements Comparator<ScoreItem> {

	@Override
	public int compare(ScoreItem o1, ScoreItem o2) {
		return o2.getScore() - o1.getScore();
	}

}
