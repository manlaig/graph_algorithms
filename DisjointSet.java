/*
    Kruskal's algorithm uses this class
*/
public class DisjointSet
{
    int[] parent;
    int[] sizes;
    int size;

    public DisjointSet(int num_nodes)
    {
        size = num_nodes;
        parent = new int[size];
        sizes = new int[size];
        for(int i = 0; i < size; i++)
        {
            parent[i] = -1;
            sizes[i] = 0;
        }
    }

    public boolean isConnected(Node node1, Node node2)
    {
        int n1 = ((int) node1.label.charAt(0)) - 97;
        int n2 = ((int) node2.label.charAt(0)) - 97;
        return isConnected(n1, n2);
    }

    private boolean isConnected(int node1, int node2)
    {
        int max = Math.max(node1, node2);
        if(max < 0 || max >= size || node1 == node2)
            return false;
        return getRoot(node1) == getRoot(node2);
    }

    public void connect(Node node1, Node node2)
    {
        if(isConnected(node1, node2))
            return;
        int n1 = ((int) node1.label.charAt(0)) - 97;
        int n2 = ((int) node2.label.charAt(0)) - 97;
        connect(n1, n2);
    }

    private void connect(int n1, int n2)
    {
        int root1 = getRoot(n1);
        int root2 = getRoot(n2);
        // child a node to the other node with larger size
        if(sizes[root1] >= sizes[root2])
        {
            parent[root2] = root1;
            updateSize(n2);
        }
        else
        {
            parent[root1] = root2;
            updateSize(n1);
        }
    }

    private int getRoot(int node)
    {
        while(parent[node] != -1)
            node = parent[node];
        return node;
    }

    private void updateSize(int n)
    {
        int count = 0;
        while(n != -1)
        {
            sizes[n] += count++;
            n = parent[n];
        }
    }
}