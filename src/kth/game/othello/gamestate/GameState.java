package kth.game.othello.gamestate;

import java.util.Set;

import kth.game.othello.board.factory.NodeData;

/**
 * This class represent a state (in history) in the game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameState {
	private Set<NodeData> nodeData;
	private String playerInTurnId;

	public GameState(Set<NodeData> nodeData, String playerInTurnId) {
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
