import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Graph extends JFrame
{
	static int startX = 50, startY = 100;
	static int widthOfNode = 30, heightOfNode = 30;
	static ArrayList<Node> nodes;
	public static void main(String[] args)
	{
		Graph frame = new Graph("Graph Visualizer");
    
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
		frame.addNode("m", startX + 500, startY + 150);
        frame.addEdge(0, 1, 4);
		frame.addEdge(0, 2, 4);
		frame.addEdge(1, 3, 1);
		frame.addEdge(1, 4, 3);
		frame.addEdge(2, 5, 2);
		frame.addEdge(2, 6, 2);
		frame.addEdge(3, 4, 1);
		frame.addEdge(3, 7, 2);
		frame.addEdge(4, 8, 3);
		frame.addEdge(5, 8, 2);
		frame.addEdge(6, 9, 1);
		frame.addEdge(7, 10, 3);
		frame.addEdge(7, 11, 4);
		frame.addEdge(8, 11, 2);
		frame.addEdge(8, 12, 5);
		frame.addEdge(9, 11, 2);
		frame.addEdge(10, 12, 1);
		frame.addEdge(11, 12, 1);
	}

	Graph(String title)
	{
		JPanel pathFinder = new JPanel();
		JButton b1 = new JButton("Use Dijkstra's");
		JTextField fromNode = new JTextField("a", 2);
		JLabel fromLabel = new JLabel("From:");
		JTextField toNode = new JTextField("m", 2);
		JLabel toLabel = new JLabel("To:");
		pathFinder.add(fromLabel);
		pathFinder.add(fromNode);
		pathFinder.add(toLabel);
		pathFinder.add(toNode);
		pathFinder.add(b1);
		add(pathFinder, BorderLayout.SOUTH);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int origin = 0, dest = 12;
				try
				{
					origin = ((int)fromNode.getText().charAt(0)) - 97;
					dest = ((int)toNode.getText().charAt(0)) - 97;
				}
				catch(Exception exc) { System.out.println("Invalid input"); }
				ArrayList<Node> path = Algorithms.DijkstrasAlgorithm(nodes, nodes.get(origin), nodes.get(dest));
				DrawPath(path);
			}
		});
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,600);
		setVisible(true);
		nodes = new ArrayList<>();
	}

	void addNode(String label, int x, int y)
	{ 
		nodes.add(new Node(label, x, y));
	}
	
	void addEdge(int from, int to, int cost)
	{
		nodes.get(from).edges.put(nodes.get(to), cost);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		FontMetrics f = g.getFontMetrics();
		int nodeheightOfNode = Math.max(heightOfNode, f.getHeight());

		for(Node originNode : nodes)
		{
			for(Node edgesDest : originNode.edges.keySet())
			{
				g.drawLine(originNode.x, originNode.y, edgesDest.x, edgesDest.y);
				double midX = originNode.x + (edgesDest.x - originNode.x)/2;
				double midY = originNode.y + (edgesDest.y - originNode.y)/2;
				g.drawString(Integer.toString(originNode.edges.get(edgesDest)), (int) midX, (int) midY);
			}

			int nodeWidth = Math.max(widthOfNode, f.stringWidth(originNode.label)+widthOfNode/2);
			g.setColor(Color.white);
			g.fillOval(originNode.x - nodeWidth/2, originNode.y - nodeheightOfNode/2, 
				nodeWidth, nodeheightOfNode);
			g.setColor(Color.black);
			g.drawOval(originNode.x - nodeWidth/2, originNode.y - nodeheightOfNode/2, 
				nodeWidth, nodeheightOfNode);
			g.drawString(originNode.label, originNode.x - f.stringWidth(originNode.label)/2,
				originNode.y + f.getHeight()/2);
		}
	}

	void DrawPath(ArrayList<Node> path)
	{
		paint(getGraphics());
		Graphics2D g = (Graphics2D) getGraphics();
		if(path.size() <= 1)	return;
		g.setStroke(new BasicStroke(5));
		for(int i = 1; i < path.size(); i++)
			g.drawLine(path.get(i - 1).x, path.get(i - 1).y, path.get(i).x, path.get(i).y);
	}
}
