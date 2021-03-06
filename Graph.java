import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Graph extends JFrame
{
	private static int windowSizeX = 800, windowSizeY = 700;
	private static int startX = 150, startY = 250;
	private static int widthOfNode = 30, heightOfNode = 30;

	public static Graph frame;
	public ArrayList<Node> nodes;
	// the new graph to render on top of the main graph
	public ArrayList<Edge> renderingGraph;

	public static void main(String[] args)
	{
		frame = new Graph("Graph Visualizer");
	
		// adding nodes
        frame.addNode("a", startX, startY + 150);
		frame.addNode("b", startX + 100, startY + 50);
		frame.addNode("c", startX + 100, startY + 250);
		frame.addNode("d", startX + 200, startY);
		frame.addNode("e", startX + 200, startY + 100);
		frame.addNode("f", startX + 200, startY + 200);
		frame.addNode("g", startX + 200, startY + 300);
		frame.addNode("h", startX + 300, startY + 50);
		frame.addNode("i", startX + 300, startY + 150);
		frame.addNode("j", startX + 300, startY + 250);
		frame.addNode("k", startX + 400, startY + 100);
		frame.addNode("l", startX + 400, startY + 200);

		// adding undirected edges
		frame.addEdge(0, 1, 4); frame.addEdge(1, 0, 4);
		frame.addEdge(0, 2, 5); frame.addEdge(2, 0, 5);
		frame.addEdge(1, 3, 1); frame.addEdge(3, 1, 1);
		frame.addEdge(1, 4, 3); frame.addEdge(4, 1, 3);
		frame.addEdge(2, 5, 2); frame.addEdge(5, 2, 2);
		frame.addEdge(2, 6, 2); frame.addEdge(6, 2, 2);
		frame.addEdge(3, 7, 2); frame.addEdge(7, 3, 2);
		frame.addEdge(3, 4, 1); frame.addEdge(4, 3, 1);
		frame.addEdge(4, 8, 3); frame.addEdge(8, 4, 3);
		frame.addEdge(5, 8, 2); frame.addEdge(8, 5, 2);
		frame.addEdge(6, 9, 1); frame.addEdge(9, 6, 1);
		frame.addEdge(7, 10, 3); frame.addEdge(10, 7, 3);
		frame.addEdge(7, 11, 4); frame.addEdge(11, 7, 4);
		frame.addEdge(8, 11, 3); frame.addEdge(11, 8, 3);
		frame.addEdge(9, 11, 2); frame.addEdge(11, 9, 2);
	}

	public Graph(String title)
	{
		nodes = new ArrayList<>();
		renderingGraph = new ArrayList<>();
		JPanel stack = new JPanel();
		stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
		add(stack, BorderLayout.NORTH);
		
		// Dijstra row begin
		JPanel pathFinder = new JPanel();
		JTextField fromNode = new JTextField("a", 2);
		JTextField toNode = new JTextField("k", 2);
		JButton b1 = new JButton("Run Dijkstra's");
		pathFinder.add(new JLabel("From:"));
		pathFinder.add(fromNode);
		pathFinder.add(new JLabel("To:"));
		pathFinder.add(toNode);
		pathFinder.add(b1);
		stack.add(pathFinder);
		// Dijstra row end

		// Traverse row begin
		JPanel top = new JPanel();
		JTextField searchForNode = new JTextField("l", 2);
		
		JButton dfs = new JButton("Depth-First Search");
		JButton bfs = new JButton("Breadth-First Search");
		top.add(new JLabel("Search For:"));
		top.add(searchForNode);
		top.add(dfs);
		top.add(bfs);
		stack.add(top);
		// Traverse row end

		// Minimum spanning tree row begin
		JPanel row = new JPanel();
		stack.add(row);
		ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
		checkBoxes.add(new JCheckBox("a", true));
		checkBoxes.add(new JCheckBox("b", true));
		checkBoxes.add(new JCheckBox("c", true));
		checkBoxes.add(new JCheckBox("d", true));
		checkBoxes.add(new JCheckBox("e", true));
		checkBoxes.add(new JCheckBox("f", true));
		checkBoxes.add(new JCheckBox("g", true));
		checkBoxes.add(new JCheckBox("h", true));
		checkBoxes.add(new JCheckBox("i", true));
		checkBoxes.add(new JCheckBox("j", true));
		checkBoxes.add(new JCheckBox("k", true));
		checkBoxes.add(new JCheckBox("l", true));
		for(int i = 0; i < checkBoxes.size(); i++)
			row.add(checkBoxes.get(i));
		JButton kruskal = new JButton("Run Kruskal's");
		row.add(kruskal);
		JButton prim = new JButton("Run Prim's");
		row.add(prim);
		// Minimum spanning tree row end

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int origin = ((int)fromNode.getText().charAt(0)) - 97;
					int dest = ((int)toNode.getText().charAt(0)) - 97;
					renderingGraph.clear();
					Algorithms.DijkstrasAlgorithm(frame, nodes.get(origin), nodes.get(dest));
				}
				catch(Exception exc) { System.out.println("Invalid input"); }
			}
		});
		bfs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int targetNode = ((int)searchForNode.getText().charAt(0)) - 97;
					renderingGraph.clear();
					Algorithms.BreadthFirstSearch(frame, nodes.get(targetNode));
				}
				catch(Exception exc) { System.out.println("Invalid input"); }
			}
		});
		dfs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int targetNode = ((int)searchForNode.getText().charAt(0)) - 97;
					renderingGraph.clear();
					Algorithms.DepthFirstSearch(frame, nodes.get(targetNode));
				}
				catch(Exception exc) { System.out.println("Invalid input"); }
			}
		});
		kruskal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// find minimum spanning tree spanning these nodes
				ArrayList<Node> targetNodes = getSelectedNodes(checkBoxes);
				renderingGraph.clear();
				Algorithms.KruskalsAlgorithm(frame, targetNodes);
			}
		});
		prim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// find minimum spanning tree spanning these nodes
				ArrayList<Node> targetNodes = getSelectedNodes(checkBoxes);
				renderingGraph.clear();
				Algorithms.PrimsAlgorithm(frame, targetNodes);
			}
		});

		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowSizeX,windowSizeY);
		setVisible(true);
	}

	private ArrayList<Node> getSelectedNodes(ArrayList<JCheckBox> checkBoxes)
	{
		ArrayList<Node> targetNodes = new ArrayList<>();
		for(int i = 0; i < checkBoxes.size(); i++)
		{
			if(checkBoxes.get(i).isSelected())
			{
				int index = (int) checkBoxes.get(i).getText().charAt(0) - 97;
				targetNodes.add(nodes.get(index));
			}
		}
		return targetNodes.size() == 0 ? nodes : targetNodes;
	}

	private void addNode(String label, int x, int y)
	{ 
		nodes.add(new Node(label, x, y));
	}
	
	private void addEdge(int from, int to, int cost)
	{
		nodes.get(from).edges.put(nodes.get(to), cost);
	}
	
	public void paint(Graphics g)
	{
		// drawing the path on top of the edges,
		// and the nodes on top of the path
		super.paint(g);
		DrawEdges(g);
		DrawPath(g, renderingGraph);
		DrawNodes(g);
	}

	private void DrawPath(Graphics gr, ArrayList<Edge> path)
	{
		if(path.size() <= 1)
			return;
		Graphics2D g = (Graphics2D) gr;
		g.setStroke(new BasicStroke(5));
		for(int i = 0; i < path.size(); i++)
		{
			Node from = path.get(i).from;
			Node to = path.get(i).to;
			g.drawLine(from.x, from.y, to.x, to.y);
		}
		g.setStroke(new BasicStroke(1));
	}

	private void DrawNodes(Graphics g)
	{
		FontMetrics f = g.getFontMetrics();
		int nodeheightOfNode = Math.max(heightOfNode, f.getHeight());
		for(Node originNode : nodes)
		{
			int nodeWidth = Math.max(widthOfNode, f.stringWidth(originNode.label)+widthOfNode/2);
			g.setColor(Color.white);
			g.fillOval(originNode.x - nodeWidth/2, originNode.y - nodeheightOfNode/2, 
				nodeWidth, nodeheightOfNode);
			g.setColor(Color.orange);
			g.fillOval(originNode.x - nodeWidth/2, originNode.y - nodeheightOfNode/2, 
				nodeWidth, nodeheightOfNode);
				g.setColor(Color.black);
			g.drawString(originNode.label, originNode.x - f.stringWidth(originNode.label)/2,
				originNode.y + f.getHeight()/2 - 5);
		}
	}

	private void DrawEdges(Graphics g)
	{
		for(Node originNode : nodes)
			for(Node edgesDest : originNode.edges.keySet())
			{
				g.drawLine(originNode.x, originNode.y, edgesDest.x, edgesDest.y);
				double midX = originNode.x + (edgesDest.x - originNode.x)/2;
				double midY = originNode.y + (edgesDest.y - originNode.y)/2;
				g.drawString(Integer.toString(originNode.edges.get(edgesDest)), (int) midX, (int) midY);
			}
	}

	public void ReDraw()
	{
		// if we don't do the drawing on a separate thread,
		// the main thread will work on drawing,
		// which will show lag when algorithms are running 
		Thread t = new Thread() {
			public void run()
			{
				paint(getGraphics());
			}
		};
		t.start();
	}
}
