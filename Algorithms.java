import java.util.*;

public class Algorithms
{
    private static final int INFINITE = Integer.MAX_VALUE;

    public static HashMap<Node, Integer> DijkstrasAlgorithm(ArrayList<Node> nodes, Node from, Node to)
    {
        HashMap<Node, Boolean> visited = new HashMap<>();
        HashMap<Node, Integer> path = new HashMap<>();

        for(Node node : nodes)
        {
            visited.put(node, false);
            path.put(node, INFINITE);
        }
        path.put(from, 0);

        for(int i = 0; i < nodes.size(); i++)
        {
            Node closest = getClosestUnvisited(path, visited);
            if(closest == null)
            {
                System.out.println("NULL");
                continue;
            }
            else
                System.out.println(closest);
            visited.put(closest, true);
            for(Node edgeNode : closest.edges.keySet())
            {
                if(!visited.get(edgeNode) && path.get(closest) + closest.edges.get(edgeNode) < path.get(edgeNode))
                    path.put(edgeNode, path.get(closest) + closest.edges.get(edgeNode));
            }
        }
        return path;
    }
    
    private static Node getClosestUnvisited(HashMap<Node, Integer> path, HashMap<Node, Boolean> visited)
    {
        int smallest = INFINITE;
        Node closest = null;
        for(Node node : path.keySet())
        {
            if(path.get(node) < smallest && !visited.get(node))
            {
                smallest = path.get(node);
                closest = node;
            }
        }
        return closest;
    }
}