package kth.game.othello.board;

public class NodeNotification {
	public String oldPlayerId;
	public String newPlayerId;

	public NodeNotification(String prevPlayerId, String newPlayerId) {
		this.oldPlayerId = prevPlayerId;
		this.newPlayerId = newPlayerId;
	}
}
