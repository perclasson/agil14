package kth.game.othello.player;

import kth.game.othello.player.Player.Type;
import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * A factory for producing players.
 *
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class PlayerFactory {

	private static int ID = 0;

	public Player createComputerPlayer(String name, MoveStrategy moveStrategy) {
		return new DefaultPlayer(Type.COMPUTER, name, generatePlayerId(), moveStrategy);
	}

	public Player createHumanPlayer(String name) {
		return new DefaultPlayer(Type.HUMAN, name, generatePlayerId());
	}

	private String generatePlayerId() {
		return Integer.toString(++ID);
	}
}
