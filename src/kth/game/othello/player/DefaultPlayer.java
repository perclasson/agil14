package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * A representation of a default player.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class DefaultPlayer implements Player {
	private String id;
	private String name;
	private Type type;
	private MoveStrategy moveStrategy;

	/**
	 * Creates a DefaultPlayer object that represents a Player in the game of Othello.
	 * 
	 * @param type
	 *            The type of the player.
	 * @param name
	 *            The name of the player.
	 * @param id
	 *            The id of the player
	 */
	public DefaultPlayer(Type type, String name, String id) {
		this.type = type;
		this.name = name;
		this.id = id;
	}

	/**
	 * Creates a DefaultPlayer object that represents a Player in the game of Othello.
	 * 
	 * @param type
	 *            The type of the player.
	 * @param name
	 *            The name of the player.
	 * @param id
	 *            The id of the player.
	 * @param moveStrategy
	 *            The move strategy of the player.
	 */
	public DefaultPlayer(Type type, String name, String id, MoveStrategy moveStrategy) {
		this.type = type;
		this.name = name;
		this.id = id;
		this.moveStrategy = moveStrategy;
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
		return moveStrategy;
	}

	@Override
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
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
		DefaultPlayer other = (DefaultPlayer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
