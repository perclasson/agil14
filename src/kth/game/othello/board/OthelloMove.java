package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

public class OthelloMove {
	
	private ArrayList<Node> visitedNodes;
	private Node fromNode;
	private Node toNode;
	
	public OthelloMove(Node from, Node to, ArrayList<Node> visitedNodes) {
		this.visitedNodes = visitedNodes;
		this.fromNode = from;
		this.toNode = to;
	}
	
	public Node getMovedFromNode() {
		return fromNode;
	}
	
	public Node getMovedToNode() {
		return toNode;
	}
	
	public List<Node> getIntermediateNodes() {
		return visitedNodes;
	}
	
}
