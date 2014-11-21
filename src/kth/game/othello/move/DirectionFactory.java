package kth.game.othello.move;

import java.util.Arrays;
import java.util.List;

public class DirectionFactory {

	public Direction createDirectionNorth() {
		return new Direction(0, 1);
	}

	public Direction createDirectionNorthEast() {
		return new Direction(1, 1);
	}

	public Direction createDirectionEast() {
		return new Direction(1, 0);
	}

	public Direction createDirectionSouthEast() {
		return new Direction(1, -1);
	}

	public Direction createDirectionSouth() {
		return new Direction(0, -1);
	}

	public Direction createDirectionSouthWest() {
		return new Direction(-1, -1);
	}

	public Direction createDirectionWest() {
		return new Direction(-1, 0);
	}

	public Direction createDirectionNorthWest() {
		return new Direction(-1, 1);
	}

	public List<Direction> createAllDirections() {
		Direction[] list = { createDirectionNorth(),
				createDirectionNorthEast(), createDirectionEast(),
				createDirectionSouthEast(), createDirectionSouth(),
				createDirectionSouthWest(), createDirectionWest(),
				createDirectionNorthWest() };
		return Arrays.asList(list);
	}
}
