package kth.game.othello.player.move.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.rules.MoveValidator;
import kth.game.othello.rules.SwapHandler;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesImpl;

/**
 * TurtleMove is a {@link kth.game.othello.player.move.strategy.MoveStrategy} that moves to the psition that results in
 * the fewest opponent swaps the next round.
 */
public class TurtleMove implements MoveStrategy {
	@Override
	public String getName() {
		return "Turtle";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		return turtle(playerId, rules, board);
	}

	/**
	 * Chooses the move that results in the least amount of nodes to swap for opponents.
	 *
	 * @param playerId the id of the player
	 * @param rules the rules of the game
	 * @param board the board
	 * @return the minimizing node
	 */
	private Node turtle(String playerId, Rules rules, Board board) {
		HashMap<Node, BoardImpl> moveResults = getResultsOfPossibleMoves(playerId, rules, board);
		ArrayList<String> playerIds = getOtherPlayersOnBoard(playerId, board);
		Node bestNode = null;
		int lowestNumberOfOpponentSwaps = Integer.MAX_VALUE;
		for (Node node : moveResults.keySet()) {
			BoardImpl moveBoard = moveResults.get(node);
			int numberOfOpponentSwaps = getNumberOfPossibleOpponentSwaps(moveBoard, playerIds);
			if (numberOfOpponentSwaps < lowestNumberOfOpponentSwaps) {
				lowestNumberOfOpponentSwaps = numberOfOpponentSwaps;
				bestNode = node;
			}
		}
		return bestNode;
	}

	/**
	 * Get all possible moves for a player on the board with the given rules
	 *
	 * @param playerId the id of the player
	 * @param rules the rules of the game
	 * @param board the board
	 * @return the possible moves for the player on the board
	 */
	private List<Node> getPossibleMoves(String playerId, Rules rules, Board board) {
		List<Node> possibleMoves = new ArrayList<Node>();
		for (Node node : board.getNodes()) {
			if (rules.isMoveValid(playerId, node.getId())) {
				possibleMoves.add(node);
			}
		}
		return possibleMoves;
	}

	/**
	 * Get the resulting boards from all possible moves respectively
	 *
	 * @param playerId the id of the player
	 * @param rules the rules of the game
	 * @param board the board
	 * @return a board mapped to a certain move by the node that was placed in the move
	 */
	private HashMap<Node, BoardImpl> getResultsOfPossibleMoves(String playerId, Rules rules, Board board) {
		List<Node> nodes = getPossibleMoves(playerId, rules, board);
		HashMap<Node, BoardImpl> moveResults = new HashMap<Node, BoardImpl>();
		for (Node node : nodes) {
			Node placedNode = new NodeImpl(node.getXCoordinate(), node.getYCoordinate(), playerId);
			List<Node> nodesToSwap = rules.getNodesToSwap(playerId, node.getId());
			ArrayList<Node> newNodes = new ArrayList<Node>(board.getNodes().size());
			for (Node oldNode : board.getNodes()) {
				newNodes.add(getNodeAfterSwap(oldNode, placedNode, nodesToSwap));
			}
			moveResults.put(node, new BoardImpl(newNodes));
		}
		return moveResults;
	}

	/**
	 * Get the maximum number of possible pieces that can be swapped by any of the given players on te given board
	 *
	 * @param moveBoard board to check for possible swaps
	 * @param playerIds the ids of the players on the board
	 * @return the maximum number of nodes that can be swapped by any of the given players on given board
	 */
	private int getNumberOfPossibleOpponentSwaps(BoardImpl moveBoard, List<String> playerIds) {
		SwapHandler swapHandler = new SwapHandler();
		MoveValidator validator = new MoveValidator(moveBoard, swapHandler);
		RulesImpl tempRules = new RulesImpl(moveBoard, validator, swapHandler);
		int maxNodesToSwap, tempNodesToSwap;
		maxNodesToSwap = 0;
		for (Node moveNode : moveBoard.getNodes()) {
			for (String otherPlayer : playerIds) {
				if (tempRules.isMoveValid(otherPlayer, moveNode.getId())) {
					tempNodesToSwap = tempRules.getNodesToSwap(otherPlayer, moveNode.getId()).size();
					maxNodesToSwap = Math.max(maxNodesToSwap, tempNodesToSwap);
				}
			}
		}
		return maxNodesToSwap;
	}

	/**
	 * Get a new Node that is either a replica of oldNode or the placedNode or a node that has been swapped
	 *
	 * @param oldNode previous node
	 * @param placedNode node that was placed
	 * @param nodesToSwap nodes that were swapped as a result of the placement of placedNode
	 * @return either replica of oldNode, placedNode or a node that has been swapped
	 */
	private Node getNodeAfterSwap(Node oldNode, Node placedNode, List<Node> nodesToSwap) {
		for (Node swapNode : nodesToSwap) {
			if (swapNode.getId().equals(oldNode.getId())) {
				return new NodeImpl(swapNode.getXCoordinate(), swapNode.getYCoordinate(),
						placedNode.getOccupantPlayerId());
			}
		}
		if (placedNode.getId().equals(oldNode.getId())) {
			return placedNode;
		} else {
			return new NodeImpl(oldNode);
		}
	}

	/**
	 * get the ids of the players with pieces on the board except for one given by playerId
	 *
	 * @param playerId the playerId of the player to be excluded
	 * @param board the board to search for players
	 * @return all players but the player given by playerId that have pieces on board
	 */
	private ArrayList<String> getOtherPlayersOnBoard(String playerId, Board board) {
		ArrayList<String> playerIds = new ArrayList<String>();
		for (Node node : board.getNodes()) {
			String otherPlayerId = node.getOccupantPlayerId();
			if (otherPlayerId != null && !playerIds.contains(otherPlayerId) && !otherPlayerId.equals(playerId)) {
				playerIds.add(otherPlayerId);
			}
		}
		return playerIds;
	}
}
