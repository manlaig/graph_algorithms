import java.util.Comparator;

public class Edge
{
    Node from;
    Node to;
    int cost;
    Edge(Node _from, Node _to)
    {
        from = _from;
        to = _to;
        cost = -1;
    }
    Edge(Node _from, Node _to, int _cost)
    {
        from = _from;
        to = _to;
        cost = _cost;
    }
}

// will be used for a PriorityQueue of Edges
class EdgeComparator implements Comparator<Edge>
{
    public int compare(Edge e1, Edge e2)
    {
        if(e1.cost > e2.cost)
            return 1;
        else if (e1.cost < e2.cost)
            return -1;
        return 0;
    }
}