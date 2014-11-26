package kth.game.othello.move;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

/**
 * This class represents a move in a Othello game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class Move {

	private ArrayList<Node> nodes;

	/**
	 * Creates a new Move object that represents a move starting at the start node and ends at the end node.
	 * 
	 * @param nodes
	 *            A list of nodes that represents the move. The first index is the start and last index is the end of
	 *            the move.
	 */
	public Move(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * The first node of this move.
	 * 
	 * @return the start node of this move.
	 */
	public Node getStartNode() {
		return nodes.get(0);
	}

	/**
	 * The last node of this move.
	 * 
	 * @return the end node of this move.
	 */
	public Node getEndNode() {
		return nodes.get(nodes.size() - 1);
	}

	/**
	 * The nodes in between the start and end node.
	 * 
	 * @return the list of nodes between the start and end node.
	 */
	public List<Node> getIntermediateNodes() {
		ArrayList<Node> intermediateNodes = new ArrayList<Node>(nodes);
		intermediateNodes.remove(0);
		intermediateNodes.remove(intermediateNodes.size() - 1);
		return intermediateNodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{move: " + getStartNode());
		for (Node n : getIntermediateNodes()) {
			sb.append(" -> " + n);
		}
		sb.append(" -> " + getEndNode() + " }");
		return sb.toString();
	}

}
