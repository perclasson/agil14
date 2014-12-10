package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import kth.game.othello.board.factory.NodeData;

/**
 * This class represents a game board of Othello. The responsibility of the board is to gather the nodes included in the
 * game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class GameBoard implements Board {
	private HashMap<String, BoardNode> boardMap;

	/**
	 * Creates a new GameBoard that represents a board of the game.
	 * 
	 * @param nodeData
	 *            A set of NodeData that will be used as nodes in the board.
	 */
	public GameBoard(Set<NodeData> nodeData) {
		this.boardMap = new HashMap<String, BoardNode>();
		for (NodeData n : nodeData) {
			BoardNode node = new BoardNode(n);
			boardMap.put(node.getId(), node);
		}
	}

	@Override
	public Node getNode(int x, int y) {
		for (Node node : boardMap.values()) {
			if (node.getXCoordinate() == x && node.getYCoordinate() == y) {
				return node;
			}
		}
		throw new IllegalArgumentException("There is no node with that coordinates");
	}

	@Override
	public List<Node> getNodes() {
		ArrayList<Node> nodes = new ArrayList<Node>(boardMap.values());
		sortNodesInNaturalOrder(nodes);
		return nodes;
	}

	@Override
	public int getMaxX() {
		int maxX = 0;
		for (Node n : boardMap.values()) {
			if (n.getXCoordinate() > maxX) {
				maxX = n.getXCoordinate();
			}
		}
		return maxX;
	}

	@Override
	public int getMaxY() {
		int maxY = 0;
		for (Node n : boardMap.values()) {
			if (n.getYCoordinate() > maxY) {
				maxY = n.getYCoordinate();
			}
		}
		return maxY;

	}

	@Override
	public boolean hasNode(int x, int y) {
		for (Node n : boardMap.values()) {
			if (n.getXCoordinate() == x && n.getYCoordinate() == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Changes the occupant player on nodes in the board. The list of nodes given will have their occupant changed to
	 * the given player id.
	 * 
	 * @param nodesToBeChanged
	 *            A list of nodes have their occupant player changed.
	 * @param playerId
	 *            The player id that should be set on the nodes.
	 */
	public void changeOccupantOnNodes(List<Node> nodesToBeChanged, String playerId) {
		for (Node n : nodesToBeChanged) {
			changeOccupantOnNode(n, playerId);
		}
	}

	/**
	 * The node in the board with the given node id.
	 * 
	 * @param nodeId
	 *            the id of the node
	 * @return the node
	 */
	public Node getNode(String nodeId) {
		return boardMap.get(nodeId);
	}

	private void changeOccupantOnNode(Node node, String playerId) {
		boardMap.get(node.getId()).setOccupantPlayerId(playerId,
				new NodeOccupantNotification(node.getOccupantPlayerId(), playerId));
	}

	/**
	 * A string representing the board using internal nodes.
	 */
	@Override
	public String toString() {
		int xMax = getMaxX();
		int yMax = getMaxY();
		int max = Math.max(xMax, yMax);
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < max; y++) {
			for (int x = 0; x < max; x++) {
				if (x < xMax && y < yMax) {
					try {
						sb.append(getNode(x, y).getOccupantPlayerId() + " \t ");
					} catch (Exception e) {
						sb.append("\t");
					}
				}
				sb.append("\t");
			}
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	private void sortNodesInNaturalOrder(List<Node> nodes) {
		Collections.sort(nodes, new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if (n1.getYCoordinate() == n2.getYCoordinate())
					return n1.getXCoordinate() - n2.getXCoordinate();
				else
					return n1.getYCoordinate() - n2.getYCoordinate();
			}
		});
	}

}