package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * Class contains information about the player.
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

	public DefaultPlayer(Type type, String name, String id) {
		this.type = type;
		this.name = name;
		this.id = id;
	}

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

}