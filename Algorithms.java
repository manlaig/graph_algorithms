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
        // mapping the shortest path to every node
        HashMap<Node, ArrayList<Edge>> path = new HashMap<>();
        HashMap<Node, Integer> distance = new HashMap<>();
        PriorityQueue<Node> visitQueue = new PriorityQueue<>(new NodeComparator(distance));

        // Initializing each node to have distance of INFINITY
        for(Node node : graph.nodes)
        {
            distance.put(node, INFINITE);
            path.put(node, new ArrayList<Edge>());
        }
        
        distance.put(from, 0);
        visitQueue.add(from);
        
        while(!visitQueue.isEmpty())
        {
            System.out.println(visitQueue);
            Node closest = visitQueue.remove();

            /* looping over all the nodes, which 'closest' has an edge to */
            for(Node edgeNode : closest.edges.keySet())
            {
                /* accumulate the distance to find the total distance to 'edgeNode' */
                int dist = distance.get(closest) + closest.edges.get(edgeNode);

                if(dist < distance.get(edgeNode))
                {
                    visitQueue.add(edgeNode);
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