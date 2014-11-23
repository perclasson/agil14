package kth.game.othello.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import kth.game.othello.Othello;
import kth.game.othello.OthelloImpl;
import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.move.DirectionFactory;
import kth.game.othello.move.MoveCalculator;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.score.ScoreImpl;

/**
 * This class consists of functions to create Othello games.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloFactoryImpl implements OthelloFactory {
	private static final int BOARD_ORDER = 8;

	private PlayerFactory playerFactory;
	private BoardFactory boardFactory;
	private DirectionFactory directionFactory;

	public OthelloFactoryImpl() {
		this.playerFactory = new PlayerFactory();
		this.boardFactory = new BoardFactory();
		this.directionFactory = new DirectionFactory();
	}

	@Override
	public Othello createComputerGame() {
		Random random = new Random();

		MoveStrategy moveStrategy = new MoveRandomStrategy(random);

		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createComputerPlayer("Computer 1", moveStrategy));
		players.add(playerFactory.createComputerPlayer("Computer 2", moveStrategy));

		BoardImpl board = boardFactory.createSquareBoard(BOARD_ORDER, players);
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		BoardHandler boardHandler = new BoardHandler(board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(boardHandler, playerHandler, moveCalculator);
		ScoreImpl score = new ScoreImpl(players, board.getNodes());

		return new OthelloImpl(boardHandler, playerHandler, moveHandler, score);
	}

	@Override
	public Othello createHumanGame() {
		return null;
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Random random = new Random();

		MoveStrategy moveStrategy = new MoveRandomStrategy(random);
		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createComputerPlayer("Computer", moveStrategy));
		players.add(playerFactory.createHumanPlayer("Human"));

		BoardImpl board = boardFactory.createSquareBoard(BOARD_ORDER, players);
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		BoardHandler boardHandler = new BoardHandler(board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(boardHandler, playerHandler, moveCalculator);
		ScoreImpl score = new ScoreImpl(players, board.getNodes());

		return new OthelloImpl(boardHandler, playerHandler, moveHandler, score);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		Random random = new Random();
		BoardImpl board = new BoardImpl(nodesData);
		MoveCalculator moveCalculator = new MoveCalculator(directionFactory.getAllDirections(), board);
		BoardHandler boardHandler = new BoardHandler(board);
		PlayerHandler playerHandler = new PlayerHandler(players, random);
		MoveHandler moveHandler = new MoveHandler(boardHandler, playerHandler, moveCalculator);
		ScoreImpl score = new ScoreImpl(players, board.getNodes());
		return new OthelloImpl(boardHandler, playerHandler, moveHandler, score);
	}

}
