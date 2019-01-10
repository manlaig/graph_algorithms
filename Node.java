import java.util.HashMap;

public class Node
{
	int x, y;
	String label;
	HashMap<Node, Integer> edges;
	
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

    @Override
    public int hashCode()
    {
        /* used to not allow duplicate nodes in HashSet */
        /* if not overriden, HashSet will add nodes with same coordinates */
        return 0;
    }
}