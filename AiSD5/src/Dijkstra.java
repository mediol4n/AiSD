import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dijkstra {

    private ArrayList<Integer> spt = new ArrayList<>();
    private PriorityQueueFloat queue = new PriorityQueueFloat(false);

    private Integer[] prevs;
    private Float[] distance;
    private Graph graph;
    private int compares = 0;

    public int getResestComparisons() {
        int tmp = compares;
        compares = 0;
        return tmp;
    }

    Dijkstra(Graph graph) {
        this.graph = graph;
        prevs = new Integer[graph.getV()];
        distance = new Float[graph.getV()];
    }

    public void printPath(int u) {
        System.err.print("(" + u + ", " + distance[u] + ")" + " ");
        if (prevs[u] != null) {
            printPath(prevs[u]);
        } else {
            System.err.println("");
        }
    }

    public void initializeSingleSource(int start) {
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Float.MAX_VALUE;
            prevs[i] = null;
        }
        distance[start] = 0f;
    }

    public void relax(Edge edge) {
        int u = edge.getU();
        int v = edge.getV();

        compares++;
        if (distance[v] > distance[u] + edge.getWeight()) {
            distance[v] = distance[u] + edge.getWeight();
            priority(v, distance[v]);
            prevs[v] = u;
        }
    }

    void runMode() {
        Scanner s = new Scanner(System.in);
        System.out.print("\nStarting vertex: ");
        int start = s.nextInt();

        long startTime = System.nanoTime();

        initializeSingleSource(start);

        for (int i = 0; i < graph.getV(); i++) {
            queue.insert(i, distance[i]);
        }
        System.out.println("\n[Dijkstra] Prep Done. Performing search.");

        while (!queue.isEmpty()) {
            int vertTop = queue.top().getNumber();
            spt.add(vertTop);
            for (Edge edge : graph.getEdges().get(vertTop)) {
                relax(edge);
            }
            queue.pop();

            System.out.println("✪ DISTANCE ✪ (" + vertTop + ", " + distance[vertTop] + ")");
            printPath(vertTop);
        }
        System.err.println("⌛Time elapsed: " + (System.nanoTime() - startTime) / 1000000 + " milliseconds.\n");
        s.close();
    }


    public void runAlgorithm(int start) {
        
        initializeSingleSource(start);

        for (int i = 0; i < graph.getV(); i++) {
            queue.insert(i, distance[i]);
        }

        while(!queue.isEmpty()) {
            int vertTop = queue.top().getNumber();
            spt.add(vertTop);
            for (Edge edge : graph.getEdges().get(vertTop)) {
                relax(edge);
            }
            queue.pop();
        }

    }


    public void priority(int key, Float p) {
        for (int i = 0; i < queue.getSize(); i++) {
            compares++;
            if (queue.getNodes().get(i).getNumber() == key) {
                compares++;
                if (queue.getNodes().get(i).getPriority() < p) {
                    continue;
                }

                int j = i;
                queue.getNodes().get(i).setPriority(p);

                while (j > 0 && queue.getNodes().get(queue.parent(j)).getPriority() > queue.getNodes().get(j).getPriority()) {
                    compares++;
                    Collections.swap(queue.getNodes(), j, queue.parent(j));
                    j = queue.parent(j);
                }
            }
        }
    }

}
