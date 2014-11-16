package kth.game.othello.player;

/**
 * Class containts information about the player.
 * 
 * @author Ludvig Axelsson, Per Classon & Tommy Roshult
 */
public class OthelloPlayer implements Player {

	private String id;
	private String name;
	private Type type;

	public OthelloPlayer(Type type, String name, String id) {
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

}
