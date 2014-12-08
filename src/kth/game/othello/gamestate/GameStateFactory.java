package kth.game.othello.gamestate;

import java.util.HashSet;
import java.util.Set;

import kth.game.othello.Game;
import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;

public class GameStateFactory {
	public GameState create(Game game) {
		Set<NodeData> nodeData = new HashSet<NodeData>();

		for (Node node : game.getBoard().getNodes()) {
			nodeData.add(new NodeData(node.getXCoordinate(), node.getYCoordinate(), node.getOccupantPlayerId()));
		}

		String playerInTurnId = game.getPlayerInTurn().getId();

		return new GameState(nodeData, playerInTurnId);
	}
}
