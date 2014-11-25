package kth.game.othello.move;

import java.util.List;

import kth.game.othello.Game;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * This is a helper class that contains the logic when players make moves.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveHandler {

	private GameBoard gameBoard;
	private PlayerHandler playerHandler;
	private MoveCalculator moveCalculator;

	public MoveHandler(GameBoard gameBoard, PlayerHandler playerHandler, MoveCalculator moveCalculator) {
		this.gameBoard = gameBoard;
		this.playerHandler = playerHandler;
		this.moveCalculator = moveCalculator;
	}

	/**
	 * If the player is a computer, this function do a move for that player
	 * 
	 * @param othello
	 * @return List<Node> Corresponding to that move.
	 */

	public List<Node> move(Game othello) {
		// If the current player is not a computer
		if (playerHandler.getPlayerInTurn().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Player in turn is not a computer.");
		}
		Player currentComputer = playerHandler.getPlayerInTurn();
		MoveStrategy moveStrategy = currentComputer.getMoveStrategy();
		Node node = moveStrategy.move(currentComputer.getId(), othello);
		return move(currentComputer.getId(), node.getId());
	}

	/**
	 * Makes a move given a player, node id and updates the board.
	 * 
	 * @param playerId
	 *            the player's id
	 * @param nodeId
	 *            the node's id
	 * @return Empty list if the move is invalid
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 */
	public List<Node> move(String playerId, String nodeId) {
		if (!playerHandler.getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}

		List<Node> nodes = moveCalculator.getNodesToSwap(playerId, nodeId);

		if (nodes.isEmpty()) {
			throw new IllegalArgumentException("Move is not valid.");
		}

		gameBoard.changeOccupantOnNodes(nodes, playerId);

		playerHandler.changePlayersTurn();
		if (!hasValidMove(playerHandler.getPlayerInTurn().getId())) {
			playerHandler.setPlayerInTurn(null);
		}

		return nodes;
	}

	/**
	 * Checks if a player has a valid move.
	 * 
	 * @param playerId
	 * @return true if the player has a valid move
	 */
	public boolean hasValidMove(String playerId) {
		return moveCalculator.getAllPossibleMoves(playerId).size() > 0;
	}

	/**
	 * Checks if a move is valid for a player.
	 * 
	 * @param playerId
	 * @param nodeId
	 * @return if it's valid for playerId to move to NodeId or not.
	 */
	public boolean isMoveValid(String playerId, String nodeId) {
		return moveCalculator.getMoves(playerId, nodeId).size() > 0;
	}

	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return moveCalculator.getNodesToSwap(playerId, nodeId);
	}
}
