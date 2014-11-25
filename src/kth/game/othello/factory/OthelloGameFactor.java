package kth.game.othello.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.Game;
import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.move.DirectionFactory;
import kth.game.othello.move.MoveCalculator;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.score.GameScore;

/**
 * This class consists of functions to create Othello games.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloGameFactor implements OthelloFactory {
	private static final int BOARD_ORDER = 8;

	private PlayerFactory playerFactory;
	private DirectionFactory directionFactory;

	public OthelloGameFactor() {
		this.playerFactory = new PlayerFactory();
		this.directionFactory = new DirectionFactory();
	}

	@Override
	public Othello createComputerGame() {
		Random random = new Random();

		MoveStrategy moveStrategy = new MoveRandomStrategy(random);

		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createComputerPlayer("Computer 1", moveStrategy));
		players.add(playerFactory.createComputerPlayer("Computer 2", moveStrategy));

		GameBoard board = new GameBoard(new Square().getNodes(BOARD_ORDER, players));
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, moveCalculator);
		GameScore score = new GameScore(players, board.getNodes());

		return new Game(board, playerHandler, moveHandler, score);
	}

	@Override
	public Othello createHumanGame() {
		Random random = new Random();
		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createHumanPlayer("Player 1"));
		players.add(playerFactory.createHumanPlayer("Player 2"));

		GameBoard board = new GameBoard(new Square().getNodes(BOARD_ORDER, players));
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, moveCalculator);
		GameScore score = new GameScore(players, board.getNodes());

		return new Game(board, playerHandler, moveHandler, score);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Random random = new Random();

		MoveStrategy moveStrategy = new MoveRandomStrategy(random);
		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createComputerPlayer("Computer", moveStrategy));
		players.add(playerFactory.createHumanPlayer("Human"));

		GameBoard board = new GameBoard(new Square().getNodes(BOARD_ORDER, players));
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, moveCalculator);
		GameScore score = new GameScore(players, board.getNodes());

		return new Game(board, playerHandler, moveHandler, score);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		Random random = new Random();
		GameBoard board = new GameBoard(nodesData);
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, moveCalculator);
		GameScore score = new GameScore(players, board.getNodes());
		return new Game(board, playerHandler, moveHandler, score);
	}

}
