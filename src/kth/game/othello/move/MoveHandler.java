package kth.game.othello.move;

import java.util.List;
import java.util.Observable;

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
public class MoveHandler extends Observable {

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
	 * @param rules
	 *            TODO
	 */
	public MoveHandler(GameBoard gameBoard, PlayerHandler playerHandler, Rules rules) {
		this.gameBoard = gameBoard;
		this.playerHandler = playerHandler;
		this.rules = rules;
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface for other details.
	 */
	public List<Node> move() {
		// If the current player is nnot a computer
		Player playerInTurn = playerHandler.getPlayerInTurn();
		if (playerInTurn == null || playerInTurn.getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Player in turn is not a computer.");
		}
		MoveStrategy moveStrategy = playerInTurn.getMoveStrategy();
		Node node = moveStrategy.move(playerInTurn.getId(), rules, gameBoard);
		return move(playerInTurn.getId(), node.getId());
	}

	/**
	 * See the {@link kth.game.othello.Othello} interface.
	 */
	public List<Node> move(String playerId, String nodeId) {
		if (!playerHandler.getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}

		List<Node> swappedNodes = getNodesToSwap(playerId, nodeId);

		if (swappedNodes.isEmpty()) {
			throw new IllegalArgumentException("Move is not valid.");
		}

		gameBoard.changeOccupantOnNodes(swappedNodes, playerId);
		playerHandler.changePlayerInTurn();

		setChanged();
		notifyObservers(swappedNodes);

		return swappedNodes;
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
