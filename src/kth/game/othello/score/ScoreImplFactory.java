package kth.game.othello.score;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

/**
 * Factory to create {@link ScoreImpl} objects.
 * 
 *
 */
public class ScoreImplFactory {

	public ScoreImpl createScoreController(List<Player> players, List<Node> nodes) {
		Comparator<ScoreItem> comparator = new DescendingComparator();
		List<ScoreItem> initialScores = new ArrayList<ScoreItem>();

		// setup score items
		for (Player player : players) {
			initialScores.add(new ScoreItem(player.getId(), 0));
		}

		ScoreImpl scoreImpl = new ScoreImpl(initialScores, comparator);

		// start observing nodes
		for (Node node : nodes) {
			node.addObserver(scoreImpl);
		}

		return scoreImpl;
	}

}
