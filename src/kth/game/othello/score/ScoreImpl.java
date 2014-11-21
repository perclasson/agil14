package kth.game.othello.score;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.NodeImpl;
import kth.game.othello.board.NodeNotification;

public class ScoreImpl extends Observable implements Score, Observer {
	private List<NodeImpl> nodes;
	private List<ScoreItem> scoreItems;

	public ScoreImpl(List<NodeImpl> nodes, List<ScoreItem> scoreItems) {
		this.nodes = nodes;
		this.scoreItems = scoreItems;
		for (NodeImpl node : nodes) {
			node.addObserver(this);
		}
	}

	@Override
	public List<ScoreItem> getPlayersScore() {
		// TODO: Sort
		return scoreItems;
	}

	@Override
	public int getPoints(String playerId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		NodeNotification notification = (NodeNotification) arg;
		// TODO
		// notification.prevPlayerId

	}
}
