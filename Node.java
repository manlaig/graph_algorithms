import java.util.HashMap;
import java.util.Comparator;

public class Node
{
	int x, y;   // coordinates
	String label;
	HashMap<Node, Integer> edges;   // weighted edges
	
	public Node(String originNode, int xVal, int yVal)
	{
		x = xVal;
		y = yVal;
		label = originNode;
		edges = new HashMap<>();
    }

    @Override
    public String toString()
    {
        return label;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        Node node = (Node) obj;
        return node.x == x && node.y == y;
    }
}

// used by Greedy Algorithms, like Dijkstra's and Prim's
// pass it to the PriorityQueue
class NodeComparator implements Comparator<Node>
{
    HashMap<Node, Integer> distance;

    NodeComparator(HashMap<Node, Integer> _distance)
    {
        distance = _distance;
    }

    public int compare(Node n1, Node n2)
    {
        if(distance.get(n1) > distance.get(n2))
            return 1;
        else if(distance.get(n1) < distance.get(n2))
            return -1;
        return 0;
    }
}