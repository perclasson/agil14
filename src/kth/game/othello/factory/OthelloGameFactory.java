package kth.game.othello.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import kth.game.othello.Game;
import kth.game.othello.Othello;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.gamestate.GameState;
import kth.game.othello.gamestate.GameStateFactory;
import kth.game.othello.gamestate.GameStateHandler;
import kth.game.othello.move.DirectionFactory;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.rules.DefaultRules;
import kth.game.othello.rules.Rules;
import kth.game.othello.score.GameScore;

/**
 * This class responsibility is to create a game of Othello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloGameFactory implements OthelloFactory {
	private static final int BOARD_ORDER = 8;

	private PlayerFactory playerFactory;
	private DirectionFactory directionFactory;

	public OthelloGameFactory() {
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
		Rules rules = new DefaultRules(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, rules, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);
		GameScore score = new GameScore(players, board.getNodes());
		GameStateHandler gameStateHandler = new GameStateHandler(new Stack<GameState>(), new GameStateFactory());
		return new Game(board, playerHandler, moveHandler, score, "gameId", gameStateHandler);
	}

	@Override
	public Othello createHumanGame() {
		Random random = new Random();
		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createHumanPlayer("Player 1"));
		players.add(playerFactory.createHumanPlayer("Player 2"));

		GameBoard board = new GameBoard(new Square().getNodes(BOARD_ORDER, players));
		Rules rules = new DefaultRules(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, rules, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);
		GameScore score = new GameScore(players, board.getNodes());
		GameStateHandler gameStateHandler = new GameStateHandler(new Stack<GameState>(), new GameStateFactory());
		return new Game(board, playerHandler, moveHandler, score, "gameId", gameStateHandler);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Random random = new Random();

		MoveStrategy moveStrategy = new MoveRandomStrategy(random);
		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createComputerPlayer("Computer", moveStrategy));
		players.add(playerFactory.createHumanPlayer("Human"));

		GameBoard board = new GameBoard(new Square().getNodes(BOARD_ORDER, players));
		Rules rules = new DefaultRules(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, rules, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);
		GameScore score = new GameScore(players, board.getNodes());
		GameStateHandler gameStateHandler = new GameStateHandler(new Stack<GameState>(), new GameStateFactory());
		return new Game(board, playerHandler, moveHandler, score, "gameId", gameStateHandler);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		Random random = new Random();
		GameBoard board = new GameBoard(nodesData);
		Rules rules = new DefaultRules(directionFactory.getAllDirections(), board);
		PlayerHandler playerHandler = new PlayerHandler(players, rules, random);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);
		GameScore score = new GameScore(players, board.getNodes());
		GameStateHandler gameStateHandler = new GameStateHandler(new Stack<GameState>(), new GameStateFactory());
		return new Game(board, playerHandler, moveHandler, score, "gameId", gameStateHandler);
	}

}
