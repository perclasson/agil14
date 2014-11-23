package kth.game.othello.player;

import kth.game.othello.player.Player.Type;
import kth.game.othello.player.movestrategy.MoveStrategy;

public class PlayerFactory {

	private int id = 0;

	public Player createComputerPlayer(String name, MoveStrategy moveStrategy) {
		return new PlayerImpl(Type.COMPUTER, name, generatePlayerId(), moveStrategy);
	}

	public Player createHumanPlayer(String name) {
		return new PlayerImpl(Type.HUMAN, name, generatePlayerId());
	}

	private String generatePlayerId() {
		return Integer.toString(id++);
	}
}
