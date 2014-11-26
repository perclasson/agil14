package kth.game.othello;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.MoveCalculator;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.PlayerHandler;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class MoveCalculatorTest {

	@Test
	public void testGetNodesToSwapException() {
		GameBoard board = mock(GameBoard.class);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		MoveCalculator movecalculator = Mockito.mock(MoveCalculator.class);
		MoveHandler movehandler = new MoveHandler(board, playerhandler, movecalculator);
		when(movecalculator.getNodesToSwap(Matchers.anyString(), Matchers.anyString())).thenReturn(
				new ArrayList<Node>());
		// Empty board should not have any nodes to swap
		exception.expect(IllegalArgumentException.class);
		movehandler.move("black", "x0y0");
	}

}
