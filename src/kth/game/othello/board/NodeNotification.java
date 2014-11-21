package kth.game.othello.board;

public class NodeNotification {
	public String prevPlayerId;
	public String newPlayerId;

	public NodeNotification(String prevPlayerId, String newPlayerId) {
		this.prevPlayerId = prevPlayerId;
		this.newPlayerId = newPlayerId;
	}
}
