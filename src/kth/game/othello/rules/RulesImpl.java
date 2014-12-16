package kth.game.othello.rules;

import java.util.List;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

/**
 * An implementation of the {@link Rules} interface.
 *
 */
public class RulesImpl implements Rules {

	SwapHandler swapHandler;
	MoveValidator validator;
	BoardImpl board;

	public RulesImpl(BoardImpl board, MoveValidator validator, SwapHandler swapHandler) {
		this.board = board;
		this.validator = validator;
		this.swapHandler = swapHandler;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return swapHandler.getNodesToSwap(playerId, nodeId, board);
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return validator.isMoveValid(playerId, nodeId);
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return validator.hasValidMove(playerId);
	}

	/**
	 * Determines if the game is active or not
	 *
	 * @param players the players of the game
	 * @return true if the game is still active
	 */
	public boolean isActive(List<Player> players) {
		return validator.isActive(players);
	}
}
