package kth.game.othello.board;

public class OthelloNode implements Node {

	private String id;
	private String playerId;
	private int x, y;

	public OthelloNode(String playerId, int x, int y) {
		this.id = "x" + x + "y" + y;
		this.playerId = playerId;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getOccupantPlayerId() {
		return playerId;
	}

	@Override
	public int getXCoordinate() {
		return x;
	}

	@Override
	public int getYCoordinate() {
		return y;
	}

	@Override
	public boolean isMarked() {
		return getOccupantPlayerId() != null;
	}
}
