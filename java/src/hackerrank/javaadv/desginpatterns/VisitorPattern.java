package hackerrank.javaadv.desginpatterns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {
    private int sum=0;
    public int getResult() {
      	//implement this
        return sum;
    }

    public void visitNode(TreeNode node) {
      	// Nothing needed
    }

    public void visitLeaf(TreeLeaf leaf) {
      	//implement this
        sum+=leaf.getValue();  
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    private int product = 1; 
    public int getResult() {
      	//implement this
        return product%(1000000000+7);
    }

    private void product(Tree node) {
        if(node.getColor() == Color.RED) product *= node.getValue();
    }

    public void visitNode(TreeNode node) {
      	//implement this
        product(node);  
    }

    public void visitLeaf(TreeLeaf leaf) {
      	//implement this
        product(leaf);  
    }
}

class FancyVisitor extends TreeVis {
    private int sumEvenNonLeaf = 0;
    private int sumGreenLeaf = 0;
    public int getResult() {
      	//implement this
        return Math.abs((sumGreenLeaf-sumEvenNonLeaf));
    }

    public void visitNode(TreeNode node) {
    	//implement this
        if(node.getDepth()%2 == 0) 
            sumEvenNonLeaf += node.getValue();
    }

    public void visitLeaf(TreeLeaf leaf) {
    	//implement this
        if(leaf.getColor() == Color.GREEN) 
            sumGreenLeaf += leaf.getValue();
    }
}

public class VisitorPattern {
    static private Set<Integer> leafNodes = new HashSet<>();
    static int countT, countL, countN;
    static private Tree createNode(Map<Integer, Set<Integer>> edges, List<Integer> values, List<Color> colors, int node, int depth) {
    	countT++;
        if(edges.containsKey(node)) {
        	countN++;
            return new TreeNode(values.get(node-1), colors.get(node-1), depth);
        } else {
        	countL++;
        	leafNodes.add(node);
            return new TreeLeaf(values.get(node-1), colors.get(node-1), depth);
        }
    }

    static private Tree populateTree(Map<Integer, Set<Integer>> edges, List<Integer> values, List<Color> colors, int nodeInt, int depth) {
        Tree node = createNode(edges, values, colors, nodeInt, depth);
        if(edges.containsKey(nodeInt)) {
	        for(Integer childInt : edges.get(nodeInt)) {
	            ((TreeNode)node).addChild(populateTree(edges, values, colors, childInt, depth+1));
	        }
        }
        return node;
    }

    public static Tree solve() {
        //read the tree from STDIN and return its root as a return 
        // value of this function
        List<Integer> values = new ArrayList<Integer>();
        List<Color> colors = new ArrayList<Color>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            int n = Integer.parseInt(br.readLine().trim());

            for(String s : br.readLine().trim().split(" ")) {
                if(s.trim().length()>0) {
                    values.add(Integer.parseInt(s.trim()));
                }
            }

            for(String s : br.readLine().trim().split(" ")) {
                if(s.trim().length()>0) {
                    if("0".equals(s.trim()))
                        colors.add(Color.RED);
                    else     
                        colors.add(Color.GREEN);
                }
            }

            Map<Integer, Set<Integer>> edges = new HashMap<Integer, Set<Integer>>();
            for(int i=1; i<n; i++) {
                String[] s = br.readLine().trim().split(" ");
                int node = Integer.parseInt(s[0].trim());
                int child = Integer.parseInt(s[1].trim());
                if(!edges.containsKey(node)) {
                    edges.put(node, new HashSet<Integer>());        
                }
                edges.get(node).add(child);
            }
            
            Tree root = populateTree(edges, values, colors, 1, 0);
            
            List<Integer> leaves = new ArrayList<>();
            IntStream.range(1, n+1).filter(i->!edges.containsKey(i)).forEach(i->leaves.add(i));;
            System.out.println(leaves);
            System.out.println(leafNodes);
            System.out.println(leaves.stream().filter(i -> !leafNodes.contains(i)).collect(Collectors.toList()));
            System.out.println(countL + " " + countN + " " + countT);
            for(int node : edges.keySet()) {
            	System.out.println(node + " : " + edges.get(node).size() + " : " + edges.get(node));
            }
            return root;
        } catch (Exception e) {
            throw new RuntimeException("failed", e);     
        }
    }



    public static void main(String[] args) {
      	Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
      	ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
      	FancyVisitor vis3 = new FancyVisitor();

      	root.accept(vis1);
      	root.accept(vis2);
      	root.accept(vis3);

      	int res1 = vis1.getResult();
      	int res2 = vis2.getResult();
      	int res3 = vis3.getResult();

      	System.out.println(res1);
     	System.out.println(res2);
    	System.out.println(res3);
	}
}

