import java.util.*;

public class Algorithms
{
    private static final int INFINITE = Integer.MAX_VALUE;

    public static ArrayList<Node> DijkstrasAlgorithm(ArrayList<Node> nodes, Node from, Node to)
    {
        HashMap<Node, Boolean> visited = new HashMap<>();
        HashMap<Node, Integer> distance = new HashMap<>();
        ArrayList<Node> visitQueue = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> path = new HashMap<>();

        for(Node node : nodes)
        {
            visited.put(node, false);
            distance.put(node, INFINITE);
            path.put(node, new ArrayList<Node>());
        }
        distance.put(from, 0);
        visitQueue.add(from);
        path.put(from, new ArrayList<>());
        
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
                {
                    path.put(edgeNode, (ArrayList) path.get(closest).clone());
                    path.get(edgeNode).add(closest);
                    distance.put(edgeNode, dist);
                    //System.out.println("From: " + closest + " Visiting: " + edgeNode + " dist: " + dist);
                }
            }
        }
        path.get(to).add(to);
        return path.get(to);
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