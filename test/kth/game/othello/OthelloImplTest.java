package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import kth.game.othello.board.Node;
import kth.game.othello.player.move.MoveHandler;
import kth.game.othello.rules.RulesImpl;

import org.junit.Test;
import org.mockito.Mockito;

public class OthelloImplTest {

	@Test
	public void whenGameFinishedNotifyObservers() {
		RulesImpl rules = Mockito.mock(RulesImpl.class);
		Mockito.when(rules.isActive(null)).thenReturn(false);

		MoveHandler moveHandler = Mockito.mock(MoveHandler.class);

		Observer observer1 = Mockito.mock(Observer.class);
		Observer observer2 = Mockito.mock(Observer.class);

		OthelloImpl othello = new OthelloImpl(null, null, null, rules, null, null, moveHandler, null);

		othello.addGameFinishedObserver(observer1);
		othello.update(null, null);
		othello.addGameFinishedObserver(observer2);
		othello.update(null, null);

		Mockito.verify(observer1, Mockito.times(2)).update(othello, null);
		Mockito.verify(observer2, Mockito.times(1)).update(othello, null);
	}

	@Test
	public void whenGameNotFinishedDoNotNotifyObservers() {
		RulesImpl rules = Mockito.mock(RulesImpl.class);
		Mockito.when(rules.isActive(null)).thenReturn(true);

		MoveHandler moveHandler = Mockito.mock(MoveHandler.class);

		Observer observer = Mockito.mock(Observer.class);

		OthelloImpl othello = new OthelloImpl(null, null, null, rules, null, null, moveHandler, null);

		othello.addGameFinishedObserver(observer);
		othello.update(null, null);

		Mockito.verify(observer, Mockito.times(0)).update(othello, null);
	}

	@Test
	public void whenMoveNotifyObservers() {
		RulesImpl rules = Mockito.mock(RulesImpl.class);
		Mockito.when(rules.isActive(null)).thenReturn(true);

		MoveHandler moveHandler = Mockito.mock(MoveHandler.class);

		Observer observer = Mockito.mock(Observer.class);

		OthelloImpl othello = new OthelloImpl(null, null, null, rules, null, null, moveHandler, null);

		List<Node> exampleList = new ArrayList<Node>(); // just a list to verify that it's being forwarded

		othello.addMoveObserver(observer);
		othello.update(null, exampleList);

		Mockito.verify(observer, Mockito.times(1)).update(othello, exampleList);
	}
}