package kth.game.othello.gamestate;

import java.util.HashSet;
import java.util.Set;

import kth.game.othello.Game;
import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;

/**
 * The responsibility of this class is to create GameStates from Game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class GameStateFactory {
	/**
	 * Create a GameState object from an object Game. It saves the current nodes and playerInTurn.
	 * 
	 * @param game
	 *            The game object.
	 * @return GameState
	 */
	public GameState create(Game game) {
		Set<NodeData> nodeData = new HashSet<NodeData>();

		for (Node node : game.getBoard().getNodes()) {
			nodeData.add(new NodeData(node.getXCoordinate(), node.getYCoordinate(), node.getOccupantPlayerId()));
		}

		Player playerInTurn = game.getPlayerInTurn();
		String playerInTurnId = null;

		if (playerInTurn != null) {
			playerInTurnId = playerInTurn.getId();
		}

		return new GameState(nodeData, playerInTurnId);
	}
}
