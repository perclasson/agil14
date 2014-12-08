package kth.game.othello;

import java.util.Set;

import kth.game.othello.board.factory.NodeData;

public class GameData {
	private Set<NodeData> nodeData;
	private String playerInTurnId;

	public GameData(Set<NodeData> nodeData, String playerInTurnId) {
		this.nodeData = nodeData;
		this.playerInTurnId = playerInTurnId;
	}

	public Set<NodeData> getNodeData() {
		return nodeData;
	}

	public String getPlayerInTurnId() {
		return playerInTurnId;
	}
}
