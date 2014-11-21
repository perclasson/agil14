package kth.game.othello.score;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import kth.game.othello.board.NodeImpl;

public class ScoreImpl extends Observable implements Score, Observer {
	List<NodeImpl> nodes;

	public ScoreImpl(List<NodeImpl> nodes) {
		this.nodes = nodes;
		for (NodeImpl node : nodes) {
			node.addObserver(this);
		}
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

	@Override
	public void update(Observable o, Object arg) {
		// we get updates of nodes that are updated....

	}

}
