package kth.game.othello;

import java.util.LinkedList;
import java.util.List;

import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.move.strategy.TurtleMove;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * Just a demo of the view and the backend.
 */
public class Demo {
	public static void main(String[] args) {

		Player player1 = PlayerImpl.getHumanPlayer("1", "Human");
		Player player2 = PlayerImpl.getComputerPlayer("2", "Turtle", new TurtleMove());

		List<Player> players = new LinkedList<Player>();
		players.add(player1);
		players.add(player2);

		Othello othello = new OthelloFactoryImpl().createGame(new Square().getNodes(8, players), players);
		OthelloView othelloView = OthelloViewFactory.create(othello, 100, 1000);
		othelloView.start(player1.getId());
	}
}
