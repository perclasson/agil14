package kth.game.othello.move;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

/**
 * Class of contains nodes that represents a move in Othello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class Move {

	private ArrayList<Node> visitedNodes;
	private Node fromNode;
	private Node toNode;

	/**
	 * Initializes a Othello move given a "start" node, "end" node, and a list
	 * of visited nodes between the start and end node.
	 */
	public Move(Node start, Node end, ArrayList<Node> visitedNodes) {
		this.visitedNodes = visitedNodes;
		this.fromNode = start;
		this.toNode = end;
	}

	/**
	 * The first node of the move.
	 * 
	 * @return the "from" node
	 */
	public Node getStartNode() {
		return fromNode;
	}

	/**
	 * The last node of the move.
	 * 
	 * @return the "to" node
	 */
	public Node getEndNode() {
		return toNode;
	}

	/**
	 * The nodes in between the start and end node.
	 * 
	 * @return the list of nodes between the start and end node.
	 */
	public List<Node> getIntermediateNodes() {
		return visitedNodes;
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
