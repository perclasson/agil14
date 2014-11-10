package kth.game.othello;

import java.util.List;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class BoardTest {

	@Test
	public void testFoo() {
		// mock creation
		List mockedList = mock(List.class);

		// using mock object
		mockedList.add("one");
		mockedList.clear();

		// verification
		verify(mockedList).add("one");
		verify(mockedList).clear();

	}
}
