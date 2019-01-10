import java.util.*;

public class Algorithms
{
    private static final int INFINITE = Integer.MAX_VALUE;

    public static HashMap<Node, Integer> DijkstrasAlgorithm(ArrayList<Node> nodes, Node from, Node to)
    {
        HashMap<Node, Boolean> visited = new HashMap<>();
        HashMap<Node, Integer> distance = new HashMap<>();
        ArrayList<Node> visitQueue = new ArrayList<>();

        for(Node node : nodes)
        {
            visited.put(node, false);
            distance.put(node, INFINITE);
        }
        distance.put(from, 0);
        visitQueue.add(from);

        while(!visitQueue.isEmpty())
        {
            Node closest = getClosestUnvisited(distance, visitQueue);
            if(closest == null)
                continue;
            visitQueue.remove(closest);
            visited.put(closest, true);
            for(Node edgeNode : closest.edges.keySet())
            {
                if(!visited.get(edgeNode))
                    visitQueue.add(edgeNode);
                int dist = distance.get(closest) + closest.edges.get(edgeNode);
                if(!visited.get(edgeNode) && dist < distance.get(edgeNode))
                    distance.put(edgeNode, dist);
            }
        }
        return distance;
    }
    
    private static Node getClosestUnvisited(HashMap<Node, Integer> distance, ArrayList<Node> nodes)
    {
        int smallest = INFINITE;
        Node closest = null;
        for(Node node : nodes)
        {
            if(distance.get(node) < smallest)
            {
                smallest = distance.get(node);
                closest = node;
            }
        }
        return closest;
    }
}