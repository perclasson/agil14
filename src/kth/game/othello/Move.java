package kth.game.othello;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;

public class Move {
	
	private ArrayList<Node> visitedNodes;
	private Node fromNode;
	private Node toNode;
	
	public Move(Node from, Node to, ArrayList<Node> visitedNodes) {
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
