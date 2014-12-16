package kth.game.othello.history;

import kth.game.othello.board.Board;

/**
 * The roll of this class is to contain a copy of an othello board as well as the id of the player in turn for a certain
 * state of an othello game.
 *
 */
public class GameState {

	private Board boardCopy;
	private String playerInTurn;

	/**
	 * Creates a GameState from a board and player id. Note that the board must a copy of the playing board.
	 * 
	 * @param boardCopy a copy of the game's board.
	 * @param playerInTurn the id of the player in turn.
	 */
	public GameState(Board boardCopy, String playerInTurn) {
		this.boardCopy = boardCopy;
		this.playerInTurn = playerInTurn;
	}

	public Board getBoard() {
		return boardCopy;
	}

	public String getPlayerInTurn() {
		return playerInTurn;
	}

	@Override
	public String toString() {
		return "Player in turn: " + playerInTurn + "\n" + boardCopy.toString();
	}
}
