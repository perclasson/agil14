package kth.game.othello.player;

import kth.game.othello.player.move.strategy.MoveStrategy;

/**
 * An implementation of {@link kth.game.othello.player.Player}
 */
public class PlayerImpl implements Player {

	private Type type;
	private String name;
	private String id;
	private MoveStrategy strategy;

	private PlayerImpl(Type type, String id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
	}

	private PlayerImpl(Type type, String id, String name, MoveStrategy strategy) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.strategy = strategy;
	}

	public static PlayerImpl getHumanPlayer(String id, String name) {
		return new PlayerImpl(Type.HUMAN, id, name);
	}

	public static PlayerImpl getComputerPlayer(String id, String name, MoveStrategy strategy) {
		return new PlayerImpl(Type.COMPUTER, id, name, strategy);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public MoveStrategy getMoveStrategy() {
		if (Type.HUMAN.equals(type)) {
			throw new UnsupportedOperationException("Cannot get strategy from human players.");
		}
		return strategy;
	}

	@Override
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		if (Type.HUMAN.equals(type)) {
			throw new UnsupportedOperationException("Cannot set strategy on human players.");
		}
		strategy = moveStrategy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerImpl other = (PlayerImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
