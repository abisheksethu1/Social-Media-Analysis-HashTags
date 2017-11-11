

public class HeapNode {
	int frequency;
	String word;
	HeapNode parent;
	HeapNode leftSibling;
	HeapNode rightSibling;
	HeapNode child;
	int degree;
	boolean isTopLevel;
	boolean isMaxNode;
	boolean childCut;
	
	public HeapNode(String name, int frequency){
		this.frequency = frequency;
		this.word = name;
		isTopLevel = false;
		isMaxNode = false;
		degree = 0;
		childCut = false;
		child = null;
		leftSibling = null;
		rightSibling = null;
		parent = null;
		//System.out.println("node created");
		//System.out.println(this.word);
	}

}

