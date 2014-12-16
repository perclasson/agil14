package kth.game.othello.history;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.TurnHandler;

/**
 * This class is responsible for handling the history of the othello game.
 */
public class HistoryHandler {

	private TurnHandler turnHandler;
	private Deque<GameState> gameStates;

	public HistoryHandler(TurnHandler turnHandler) {
		this.turnHandler = turnHandler;
		gameStates = new ArrayDeque<GameState>();
	}

	/**
	 * Undoes the previous move, modifying the given board with the previous game state.
	 * 
	 * @param playingBoard the board to be modified to the previous game state.
	 */
	public void undoPreviousMove(BoardImpl playingBoard) {
		try {
			GameState previousGameState = gameStates.pop();

			List<Node> previousNodes = previousGameState.getBoard().getNodes();

			for (Node node : previousNodes) {
				String nodeId = node.getId();
				String playerId = node.getOccupantPlayerId();
				playingBoard.setOccupantPlayerId(nodeId, playerId);
			}

			String previousPlayerInTurn = previousGameState.getPlayerInTurn();
			turnHandler.setPlayerInTurn(previousPlayerInTurn);

		} catch (NoSuchElementException e) {
			System.out.println("No move to undo");
		}
	}

	/**
	 * Creates and saves a new GameState with the given parameters. A copy of the board is made before saving.
	 *
	 * @param board the board associated with the game state
	 * @param playerInTurn the id for the player in turn
	 */
	public void saveGameState(BoardImpl board, String playerInTurn) {
		gameStates.push(new GameState(new BoardImpl(board), playerInTurn));
	}
}
