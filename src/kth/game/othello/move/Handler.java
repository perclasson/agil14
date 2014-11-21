package kth.game.othello.move;

import java.util.List;
import kth.game.othello.OthelloImpl;
import kth.game.othello.PlayerWrapper;
import kth.game.othello.board.Node;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.player.Player;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * This is a helper class that contains the logic when players make moves.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class Handler {

	private BoardImpl board;
	private PlayerWrapper playerWrapper;
	private MoveCalculator moveCalculator;

	public Handler(BoardImpl board, PlayerWrapper playerWrapper) {
		this.board = board;
		this.playerWrapper = playerWrapper;
		
		this.moveCalculator = new MoveCalculator(board); // TODO
	}

	public List<Node> move(OthelloImpl othello) {
		// If the current player is not a computer
		if (playerWrapper.getPlayerInTurn().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Player in turn is not a computer.");
		}

		Player player = playerWrapper.getPlayerInTurn();
		
		MoveStrategy moveStrategy = player.getMoveStrategy();
		Node node = moveStrategy.move(player.getId(), othello); // TODO wtf?
		
		return move(player.getId(), node.getId());
	}
	
	/**
	 * Makes a move given a player, node id and updates the board.
	 * 
	 * @param playerId
	 * 		the player's id
	 * @param nodeId
	 * 		the node's id
	 * @return Empty list if the move is invalid
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 */
	public List<Node> move(String playerId, String nodeId) {
		if (!playerWrapper.getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}
	
		List<Node> nodes = moveCalculator.getNodesToSwap(playerId, nodeId);
		if (nodes.isEmpty()) {
			throw new IllegalArgumentException("Move is not valid.");
		}

		playerWrapper.changePlayersTurn();
		board.changeOccupantOnNodes(nodes, playerId);

		return nodes;
	}
	
	/**
	 * Checks if a player has a valid move.
	 * 
	 * @param playerId
	 * @return true if the player has a valid move
	 */
	public boolean hasValidMove(String playerId) {
		for (Node node : board.getNodes()) {
			List<Move> moves = moveCalculator.getMoves(playerId, node.getId());
			if (moves.size() > 0) {
				return true;
			}
		}
		return false;
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
