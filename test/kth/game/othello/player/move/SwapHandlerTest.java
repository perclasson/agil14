package kth.game.othello.player.move;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactory;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.rules.SwapHandler;

import org.junit.Assert;
import org.junit.Test;

public class SwapHandlerTest {

	private OthelloFactory getGameFactory() {
		return new OthelloFactoryImpl();
	}

	@Test
	public void testGetNodesToSwap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		int[] whitePositions = { 7, 12, 14, 17, 18, 19, 20, 22, 28, 29, 30, 35, 37, 39, 42, 49 };
		int[] blackPositions = { 3, 16, 23, 45, 56 };
		int[] expectedSwapPositions = { 17, 18, 19, 20, 22, 29, 37 };

		Othello game = getGameFactory().createComputerGame();
		BoardImpl board = (BoardImpl) game.getBoard();
		List<Node> nodes = board.getNodes();

		Player player1 = game.getPlayers().get(0);
		Player player2 = game.getPlayers().get(1);

		// place black pieces
		for (int position : blackPositions) {
			Node node = board.getNodes().get(position);
			board.setOccupantPlayerId(node.getId(), player1.getId());
		}

		// place white pieces
		for (int position : whitePositions) {
			Node node = board.getNodes().get(position);
			board.setOccupantPlayerId(node.getId(), player2.getId());
		}
		SwapHandler swapHandler = new SwapHandler();
		String nodeId = nodes.get(21).getId();
		List<Node> nodesToSwap = swapHandler.getNodesToSwap(player1.getId(), nodeId, board);

		Assert.assertTrue(nodesToSwap.size() == expectedSwapPositions.length);

		for (int i = 0; i < expectedSwapPositions.length; i++) {

			Node expectedNode = nodes.get(expectedSwapPositions[i]);

			Assert.assertTrue(nodesToSwap.contains(expectedNode));
		}
	}
}
