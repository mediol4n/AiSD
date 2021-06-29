import java.util.ArrayList;
import java.util.Comparator;

/**
 * 1) Sort all the edges from low weight to high 2) Take the edge with the
 * lowest weight and add it to the spanning tree. If adding the edge created a
 * cycle, then reject this edge. 3) Keep adding edges until we reach all
 * vertices.
 */

public class Kruskal {

    private ArrayList<Edge> edges = new ArrayList<>();
    private Graph graph;
    private int compares = 0;

    public int getResestComparisons() {
        int tmp = compares;
        compares = 0;
        return tmp;
    }

    Kruskal(Graph graph) {
        this.graph = graph;
    }

    // A utility function to find
    // set of an element i (uses
    // path compression technique)
    private int find(Subset[] subsets, int i) {
        compares++;
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // A function that does union
    // of two sets of x and y
    // (uses union by rank)
    private void union(Subset[] subsets, int x, int y) {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        compares++;
        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            compares++;
            subsets[yRoot].parent = xRoot;
        } else {
            compares++;
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }

    public void runMode() {

        for (int n = 0; n < graph.getV(); n++) {
            edges.addAll(graph.getEdges().get(n));
        }

        // Step 1: Sorting by weight
        edges.sort(Comparator.comparing(Edge::getWeight));

        // Initialization
        Subset[] subsets = new Subset[graph.getV()];
        for (int i = 0; i < subsets.length; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        Edge[] result = new Edge[graph.getV()];
        int edgeIndex = 0;
        int edgeSorted = 0;

        // G is a tree <-> G is acyclic and |V| = |E| + 1
        while (edgeIndex < graph.getV() - 1) {
            compares++;
            Edge next;
            // Step 2: Get edge with lowest weight
            next = edges.get(edgeSorted++);
            //in every edge we get 'root' of the tree which it is connected to. and check if roots are the same
            int x = find(subsets, next.getU());
            int y = find(subsets, next.getV());

            compares++;

            // Step 3: If edge does not make circle, add it
            if (x != y) {
                result[edgeIndex++] = next;
                union(subsets, x, y);
            }
        }

        System.out.println("♕[Kruskal]♕: Following are edges of in constructed MST");
        float totalWeight = 0;
        for (edgeSorted = 0; edgeSorted < edgeIndex; edgeSorted++) {
            System.out.println("(" + result[edgeSorted].getU() + ", " + result[edgeSorted].getV() + "):"
                    + result[edgeSorted].getWeight());
            totalWeight += result[edgeSorted].getWeight();
        }
        System.out.println("Total weight: " + totalWeight);
    }

}
