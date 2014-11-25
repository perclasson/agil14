package kth.game.othello.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import kth.game.othello.board.factory.NodeData;

public class GameBoard implements Board {
	private HashMap<String, BoardNode> board;

	public GameBoard(Set<NodeData> nodeData) {
		this.board = new HashMap<String, BoardNode>();
		// Create an array of the node data set
		for (NodeData n : nodeData) {
			BoardNode node = new BoardNode(n);
			board.put(node.getId(), node);
		}
	}

	@Override
	public Node getNode(int x, int y) {
		for (Node node : board.values()) {
			if (node.getXCoordinate() == x && node.getYCoordinate() == y) {
				return node;
			}
		}
		throw new IllegalArgumentException("There is no node with that coordinates");
	}

	@Override
	public List<Node> getNodes() {
		return new ArrayList<Node>(board.values());
	}

	public void changeOccupantOnNode(Node node, String playerId) {
		board.get(node.getId()).setOccupantPlayerId(playerId);
	}

	/**
	 * A string representing a matrix of the board
	 */
	@Override
	public String toString() {
		int xMax = 0;
		int yMax = 0;
		for (Node node : board.values()) {
			if (node.getXCoordinate() > xMax) {
				xMax = node.getXCoordinate();
			}
			if (node.getYCoordinate() > yMax) {
				yMax = node.getYCoordinate();
			}
		}
		int max = Math.max(xMax, yMax);
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < max; y++) {
			for (int x = 0; x < max; x++) {
				if (x < xMax && y < yMax) {
					try {
						sb.append(getNode(x, y) + " \t ");
					} catch (Exception e) {
						sb.append(". \t ");
					}
				}
				sb.append(". \t ");
			}
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	public void changeOccupantOnNodes(List<Node> nodesToBeChanged, String playerId) {
		for (Node n : nodesToBeChanged) {
			changeOccupantOnNode(n, playerId);
		}
	}

}