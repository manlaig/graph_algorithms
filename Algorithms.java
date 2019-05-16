import java.util.*;

public class Algorithms
{
    private static final int INFINITE = Integer.MAX_VALUE;

    // to show the progress of an algorithm
    private static void sleep(int milliseconds)
    {
        long initial = System.currentTimeMillis();
        while(System.currentTimeMillis() - initial < milliseconds);
    }

    public static void DepthFirstSearch(Graph graph)
    {
        ArrayList<Node> visited = new ArrayList<>();
        LinkedList<Node> q = new LinkedList<>();

        Node start = graph.nodes.get(0);
        q.add(start);

        while(!q.isEmpty())
        {
            Node temp = (Node) q.removeLast();
            if(!visited.contains(temp))
            {
                graph.currShortestPath.add(temp);
                graph.ReDraw();
                sleep(200);
            }
            visited.add(temp);
            for(Node edgeNode : temp.edges.keySet())
                if(!visited.contains(edgeNode))
                    q.add(edgeNode);
        }
    }

    public static void BreadthFirstSearch(Graph graph)
    {
        ArrayList<Node> visited = new ArrayList<>();
        LinkedList<Node> q = new LinkedList<>();

        Node start = graph.nodes.get(0);
        q.add(start);

        while(!q.isEmpty())
        {
            Node temp = (Node) q.removeFirst();
            if(!visited.contains(temp))
            {
                graph.currShortestPath.add(temp);
                graph.ReDraw();
                sleep(200);
            }
            visited.add(temp);
            for(Node edgeNode : temp.edges.keySet())
                if(!visited.contains(edgeNode))
                    q.add(edgeNode);
        }
    }

    public static ArrayList<Node> DijkstrasAlgorithm(ArrayList<Node> nodes, Node from, Node to)
    {
        HashMap<Node, Boolean> visited = new HashMap<>();
        HashMap<Node, Integer> distance = new HashMap<>();
        ArrayList<Node> visitQueue = new ArrayList<>();
        /* mapping the shortest path to every node */
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
                    path.put(edgeNode, (ArrayList<Node>) path.get(closest).clone());
                    path.get(edgeNode).add(closest);
                    distance.put(edgeNode, dist);
                }
            }
        }
        path.get(to).add(to);
        return path.get(to);
    }
    
    // what is the closest node we haven't visited
    private static Node getClosestUnvisited(HashMap<Node, Integer> distance, ArrayList<Node> nodes)
    {
        int smallest = INFINITE;
        Node closest = null;
        for(Node node : nodes)
            if(distance.get(node) < smallest)
            {
                smallest = distance.get(node);
                closest = node;
            }
        return closest;
    }
}