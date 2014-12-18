package kth.game.othello.player.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.history.HistoryHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.TurnHandler;
import kth.game.othello.rules.RulesImpl;
import kth.game.othello.score.ScoreImpl;

/**
 * The responsibility of MoveHandler is to perform moves, notify its observers and calling methods for handing over the
 * turn as well as saving the game state.
 */
public class MoveHandler extends Observable {

	private BoardImpl board;
	private RulesImpl rules;
	private TurnHandler turnHandler;
	private HistoryHandler historyHandler;
	private ScoreImpl scores;

	public MoveHandler(BoardImpl board, RulesImpl rules, TurnHandler turnHandler, HistoryHandler historyHandler,
			ScoreImpl scores) {
		this.board = board;
		this.rules = rules;
		this.turnHandler = turnHandler;
		this.historyHandler = historyHandler;
		this.scores = scores;
	}

	/**
	 * If the player in turn is a computer than this computer makes a move and updates the player in turn. All observers
	 * will be notified with the additional argument being the list of nodes that were swapped.
	 *
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 * @throws IllegalStateException
	 *             if there is not a computer in turn
	 */
	public List<Node> move() {
		if (turnHandler.getPlayerInTurn().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Computer not in turn.");
		}
		Player playerInTurn = turnHandler.getPlayerInTurn();
		Node node = playerInTurn.getMoveStrategy().move(playerInTurn.getId(), rules, board);
		if (node == null) {
			// no available moves, let's remove 2 points
			scores.changeScore(playerInTurn.getId(), -2);
			turnHandler.nextPlayer();
			return new ArrayList<Node>();
		}
		return move(playerInTurn.getId(), node.getId());
	}

	/**
	 * Validates if the move is correct and if the player is in turn. If so, then the move is made which updates the
	 * board and the player in turn. All observers will be notified with the additional argument being the list of nodes
	 * that were swapped.
	 *
	 * @param playerId
	 *            the id of the player that makes the move
	 * @param nodeId
	 *            the id of the node where the player wants to move
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 * @throws IllegalArgumentException
	 *             if the move is not valid, or if the player is not in turn
	 */
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		// check if move is being done for correct player
		if (!turnHandler.getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Incorrect player id.");
		}

		// check if move is valid
		if (!rules.isMoveValid(playerId, nodeId)) {
			throw new IllegalArgumentException("Move is not valid.");
		}

		// save the previous game state
		historyHandler.saveGameState(board, turnHandler.getPlayerInTurn().getId());

		// swap nodes
		List<Node> nodesToSwap = rules.getNodesToSwap(playerId, nodeId);
		for (Node node : nodesToSwap) {
			board.setOccupantPlayerId(node.getId(), playerId);
		}

		// place given node
		board.setOccupantPlayerId(nodeId, playerId);

		// add given node
		nodesToSwap.add(board.getNode(nodeId));

		// hand over the turn
		turnHandler.nextPlayer();

		// notifies all observers
		super.setChanged();
		super.notifyObservers(nodesToSwap);

		return nodesToSwap;
	}
}
