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

    public static void DepthFirstSearch(Graph graph, Node target)
    {
        ArrayList<Node> visited = new ArrayList<>();
        LinkedList<Node> q = new LinkedList<>();

        Node start = graph.nodes.get(0);
        q.add(start);

        // only for illustration purposes
        HashMap<Node, Node> parent = new HashMap<>();
        parent.put(start, start);

        while(!q.isEmpty())
        {
            Node temp = (Node) q.removeLast();
            if(!visited.contains(temp))
            {
                graph.renderingGraph.add(new Edge(parent.get(temp), temp));
                graph.ReDraw();
                sleep(250);
            }
            if(temp.equals(target))
                break;
            visited.add(temp);
            for(Node edgeNode : temp.edges.keySet())
                if(!visited.contains(edgeNode))
                {
                    parent.put(edgeNode, temp);
                    q.add(edgeNode);
                }
        }
    }

    public static void BreadthFirstSearch(Graph graph, Node target)
    {
        ArrayList<Node> visited = new ArrayList<>();
        LinkedList<Node> q = new LinkedList<>();

        Node start = graph.nodes.get(0);
        q.add(start);

        // only for illustration purposes
        HashMap<Node, Node> parent = new HashMap<>();
        parent.put(start, start);

        while(!q.isEmpty())
        {
            Node temp = (Node) q.removeFirst();
            if(!visited.contains(temp))
            {
                graph.renderingGraph.add(new Edge(parent.get(temp), temp));
                graph.ReDraw();
                sleep(250);
            }
            if(temp.equals(target))
                break;
            visited.add(temp);
            for(Node edgeNode : temp.edges.keySet())
                if(!visited.contains(edgeNode))
                {
                    parent.put(edgeNode, temp);
                    q.add(edgeNode);
                }
        }
    }

    public static void DijkstrasAlgorithm(Graph graph, Node from, Node to)
    {
        HashMap<Node, Boolean> visited = new HashMap<>();
        HashMap<Node, Integer> distance = new HashMap<>();
        ArrayList<Node> visitQueue = new ArrayList<>();

        // mapping the shortest path to every node
        HashMap<Node, ArrayList<Edge>> path = new HashMap<>();

        // Initializing each node to have distance of INFINITY
        for(Node node : graph.nodes)
        {
            visited.put(node, false);
            distance.put(node, INFINITE);
            path.put(node, new ArrayList<Edge>());
        }
        distance.put(from, 0);
        visitQueue.add(from);
        path.put(from, new ArrayList<Edge>());
        
        while(!visitQueue.isEmpty())
        {
            Node closest = getClosestUnvisited(distance, visitQueue);
            if(closest == null)
                continue;
            visitQueue.remove(closest);
            visited.put(closest, true);

            // looping over all the nodes, which 'closest' has an edge to
            for(Node edgeNode : closest.edges.keySet())
            {
                // visit it later, if not already visited
                if(!visited.get(edgeNode))
                    visitQueue.add(edgeNode);
                
                /* accumulate the distance to find the total distance to 'edgeNode' */
                int dist = distance.get(closest) + closest.edges.get(edgeNode);

                if(!visited.get(edgeNode) && dist < distance.get(edgeNode))
                {
                    ArrayList<Edge> newPath = (ArrayList<Edge>) path.get(closest).clone();
                    newPath.add(new Edge(closest, edgeNode));
                    path.put(edgeNode, newPath);
                    distance.put(edgeNode, dist);

                    // for algorithm illustration
                    // 'newPath' will be drawn with a 250ms delay
                    graph.renderingGraph = newPath;
                    graph.ReDraw();
                    sleep(250);
                }
            }
        }
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

    public static void KruskalsAlgorithm(Graph graph)
    {
        // the edges with the lowest cost will be on the top
        PriorityQueue<Edge> nodes = new PriorityQueue<>(new EdgeComparator());

        for(Node node : graph.nodes)
        {
            for(Map.Entry<Node, Integer> edge : node.edges.entrySet())
                nodes.add(new Edge(node, edge.getKey(), edge.getValue()));
        }

        while(!nodes.isEmpty())
        {
            Edge e = nodes.peek();
            nodes.remove();
            System.out.println(e);
        }
    }
}
