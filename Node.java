import java.util.HashMap;

class Node
{
	int x, y;
	String label;
	HashMap<Node, Integer> edges;
	
	Node(String originNode, int xVal, int yVal)
	{
		x = xVal;
		y = yVal;
		label = originNode;
		edges = new HashMap<>();
	}
}