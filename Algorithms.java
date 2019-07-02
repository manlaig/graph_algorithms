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

                if(dist < distance.get(edgeNode))
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
        // when done, show the shortest path to node 'to'
        graph.renderingGraph = path.get(to);
        graph.ReDraw();
        sleep(250);
    }
    
    // what is the closest node we haven't visited
    // used by Dijkstra's Algorithm
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

    // find a minimum spanning tree spanning the 'targetNodes'
    public static void KruskalsAlgorithm(Graph graph, ArrayList<Node> targetNodes)
    {
        // the edges with the lowest cost will be on the top
        PriorityQueue<Edge> edges = new PriorityQueue<>(new EdgeComparator());
        DisjointSet set = new DisjointSet(graph.nodes.size());

        // kind of like a preprocessing step
        // fill 'edges' queue with the appropriate edges
        // appropriate edges meaning edges connecting 'targetNodes'
        for(Node node : targetNodes)
        {
            for(Map.Entry<Node, Integer> edge : node.edges.entrySet())
            {
                // only include edges that connect our target nodes
                for(Node n : targetNodes)
                {
                    if(edge.getKey().equals(n))
                        edges.add(new Edge(node, edge.getKey(), edge.getValue()));
                }
            }
        }

        // loop through all the edges, starting with the lowest weight
        while(!edges.isEmpty())
        {
            Edge e = edges.peek();
            edges.remove();

            if(!set.isConnected(e.from, e.to))
            {
                set.connect(e.from, e.to);
                graph.renderingGraph.add(e);

                // for algorithm visualization
                graph.ReDraw();
                sleep(500);
            }
        }
    }
}