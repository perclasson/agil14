package kth.game.othello.history;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.TurnHandler;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class HistoryHandlerTest {

	@Test
	public void saveGameStateAddsObjectToStack() {
		HistoryHandler historyHandler = new HistoryHandler(null);
		Deque<GameState> gameStates = new ArrayDeque<GameState>();

		try {
			Field field = HistoryHandler.class.getDeclaredField("gameStates");
			field.setAccessible(true);
			field.set(historyHandler, gameStates);
		} catch (Exception e) {
			Assert.fail();
		}

		// perform action
		historyHandler.saveGameState(new BoardImpl(new ArrayList<Node>()), "1");

		Assert.assertEquals(gameStates.size(), 1);
	}

	@Test
	public void undoMoveChangesTopOfGameStateStack() {
		// mock necessary objects
		TurnHandler turnHandler = Mockito.mock(TurnHandler.class);
		BoardImpl playingBoard = Mockito.mock(BoardImpl.class);

		BoardImpl secondBoard = Mockito.mock(BoardImpl.class);
		Mockito.when(secondBoard.getNodes()).thenReturn(new ArrayList<Node>());

		GameState firstGameState = Mockito.mock(GameState.class);
		GameState secondGameState = Mockito.mock(GameState.class);
		Mockito.when(secondGameState.getBoard()).thenReturn(secondBoard);

		Deque<GameState> gameStates = new ArrayDeque<GameState>();
		gameStates.push(firstGameState);
		gameStates.push(secondGameState);

		Deque<GameState> compareStack = new ArrayDeque<GameState>();
		compareStack.push(firstGameState);

		HistoryHandler historyHandler = new HistoryHandler(turnHandler);

		try {
			// using reflection, set previousGameState
			Field field = HistoryHandler.class.getDeclaredField("gameStates");
			field.setAccessible(true);
			field.set(historyHandler, gameStates);

			// perform action
			historyHandler.undoPreviousMove(playingBoard);

			// compare equal size
			Assert.assertEquals(compareStack.size(), gameStates.size());
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void undoPreviousMoveChangesPlayingBoard() {
		TurnHandler turnHandler = Mockito.mock(TurnHandler.class);

		String playerId = "p1";
		String node1Id = "1";
		String node2Id = "2";

		Node previousNode1 = Mockito.mock(Node.class);
		Mockito.when(previousNode1.getId()).thenReturn(node1Id);
		Mockito.when(previousNode1.getOccupantPlayerId()).thenReturn(playerId);
		Node previousNode2 = Mockito.mock(Node.class);
		Mockito.when(previousNode2.getId()).thenReturn(node2Id);
		Mockito.when(previousNode2.getOccupantPlayerId()).thenReturn(playerId);
		List<Node> previousNodes = new ArrayList<Node>();
		previousNodes.add(previousNode1);
		previousNodes.add(previousNode2);

		BoardImpl previousBoard = Mockito.mock(BoardImpl.class);
		Mockito.when(previousBoard.getNodes()).thenReturn(previousNodes);
		BoardImpl playingBoard = Mockito.mock(BoardImpl.class);

		GameState previousGameState = Mockito.mock(GameState.class);
		Mockito.when(previousGameState.getBoard()).thenReturn(previousBoard);

		Deque<GameState> gameStates = new ArrayDeque<GameState>();
		gameStates.push(previousGameState);

		HistoryHandler historyHandler = new HistoryHandler(turnHandler);

		try {
			// using reflection, set the currentGameState
			Field field = HistoryHandler.class.getDeclaredField("gameStates");
			field.setAccessible(true);
			field.set(historyHandler, gameStates);
		} catch (Exception e) {
			Assert.fail();
		}

		// perform action
		historyHandler.undoPreviousMove(playingBoard);

		// verify playingBoard have been modified in the correct way
		Mockito.verify(playingBoard, Mockito.times(1)).setOccupantPlayerId(node1Id, playerId);
		Mockito.verify(playingBoard, Mockito.times(1)).setOccupantPlayerId(node2Id, playerId);
	}

	@Test
	public void undoPreviousMoveChangesPlayerInTurn() {
		// set up the stack
		String previousPlayerInTurn = "p2";
		Deque<GameState> gameStates = new ArrayDeque<GameState>();
		gameStates.add(new GameState(new BoardImpl(new ArrayList<Node>()), previousPlayerInTurn));

		// mock needed objects
		TurnHandler turnHandler = Mockito.mock(TurnHandler.class);
		BoardImpl playingBoard = Mockito.mock(BoardImpl.class);

		HistoryHandler historyHandler = new HistoryHandler(turnHandler);

		// use reflection to set local variable
		try {
			Field field = HistoryHandler.class.getDeclaredField("gameStates");
			field.setAccessible(true);
			field.set(historyHandler, gameStates);
		} catch (Exception e) {
			Assert.fail();
		}

		// perform action
		historyHandler.undoPreviousMove(playingBoard);

		// verify setPlayerInTurn have been called with correct id
		Mockito.verify(turnHandler, Mockito.times(1)).setPlayerInTurn(previousPlayerInTurn);
	}

	@Test
	public void undoStartingBoardDoesNotChangePlayingBoard() {
		// mock necessary objects
		TurnHandler turnHandler = Mockito.mock(TurnHandler.class);
		BoardImpl playingBoard = Mockito.mock(BoardImpl.class);

		HistoryHandler historyHandler = new HistoryHandler(turnHandler);
		historyHandler.undoPreviousMove(playingBoard);

		// check that the playing board is not modified in any way
		Mockito.verify(playingBoard, Mockito.times(0)).setOccupantPlayerId(Matchers.anyString(), Matchers.anyString());
	}
}
