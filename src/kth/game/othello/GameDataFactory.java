package kth.game.othello;

import java.util.HashSet;
import java.util.Set;

import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;

public class GameDataFactory {
	public GameData createGameData(Game game) {
		Set<NodeData> nodeData = new HashSet<NodeData>();

		for (Node node : game.getBoard().getNodes()) {
			nodeData.add(new NodeData(node.getXCoordinate(), node.getYCoordinate(), node.getOccupantPlayerId()));
		}

		String playerInTurnId = game.getPlayerInTurn().getId();

		return new GameData(nodeData, playerInTurnId);
	}
}
