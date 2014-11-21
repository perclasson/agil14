package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * Class contains information about the player.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class PlayerImpl implements Player {

	private String id;
	private String name;
	private Type type;

	public PlayerImpl(Type type, String name, String id) {
		this.type = type;
		this.name = name;
		this.id = id;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		// TODO Auto-generated method stub

	}

}
