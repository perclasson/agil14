package kth.game.othello.move;

import java.util.List;

import kth.game.othello.Game;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.rules.Rules;

/**
 * The responsibility of this class is to handle moves of the game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveHandler {

	private GameBoard gameBoard;
	private PlayerHandler playerHandler;
	private Rules rules;

	/**
	 * Creates a MoveHandler object that validates if moves are correct.
	 * 
	 * @param gameBoard
	 *            The board of the game.
	 * @param playerHandler
	 *            The player handler of the game.
	 * @param moveCalculator
	 *            A move calculator that uses the given board.
	 */
	public MoveHandler(GameBoard gameBoard, PlayerHandler playerHandler, Rules rules) {
		this.gameBoard = gameBoard;
		this.playerHandler = playerHandler;
		this.rules = rules;
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface for other details.
	 * 
	 * @param Game
	 *            The current game of othello.
	 */
	public List<Node> move() {
		// If the current player is not a computer
		if (playerHandler.getPlayerInTurn().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Player in turn is not a computer.");
		}
		Player currentComputer = playerHandler.getPlayerInTurn();
		MoveStrategy moveStrategy = currentComputer.getMoveStrategy();
		Node node = moveStrategy.move(currentComputer.getId(), rules, gameBoard);
		return move(currentComputer.getId(), node.getId());
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public List<Node> move(String playerId, String nodeId) {
		if (!playerHandler.getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}

		List<Node> nodes = getNodesToSwap(playerId, nodeId);

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
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public boolean hasValidMove(String playerId) {
		return rules.hasValidMove(playerId);
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public boolean isMoveValid(String playerId, String nodeId) {
		return rules.isMoveValid(playerId, nodeId);
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return rules.getNodesToSwap(playerId, nodeId);
	}
}
