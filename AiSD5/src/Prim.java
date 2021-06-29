import java.util.ArrayList;
import java.util.Collections;

public class Prim {
    private Graph graph;
    private Float[] keyValues;
    private boolean[] mstSet;
    private int compares = 0;
    ArrayList<Edge> list = new ArrayList<>();

    public int getResestComparisons() {
        int tmp = compares;
        compares = 0;
        return tmp;
    }

    public Prim(Graph graph) {
        this.graph = graph;
        keyValues = new Float[graph.getV()];
        mstSet = new boolean[graph.getV()];
    }

    private void initializeSingleSource(int start) {
        for (int i = 0; i < mstSet.length; i++) {
            mstSet[i] = false;
            keyValues[i] = (float) Integer.MAX_VALUE;
        }
        keyValues[start] = 0f;
    }

    public void runMode() {
        float totalWeight = 0f;

        int parent[] = new int[graph.getV()];
        // Initialization
        initializeSingleSource(0);

        mstSet[0] = true;
        PriorityQueueFloat queue = new PriorityQueueFloat(true);
        for (int i = 0; i < graph.getV(); i++) {
            queue.insert(i, keyValues[i]);
        }

        System.out.println("♚[Prim]♚: Following are edges of in constructed MST");
        while (!queue.isEmpty()) {
            compares++;
            Node u = queue.top();
            queue.pop();

            mstSet[u.getNumber()] = true;
            // we go through all adges coming out of u (top)
            for (Edge iterator : graph.getEdges().get(u.getNumber())) {

                compares += 2;
                // check if this vertex is already in mstSet and if its key is bigger than
                // weight of (u,v)
                // here v is our iterator
                if (!mstSet[iterator.getV()] && keyValues[iterator.getV()] > iterator.getWeight()) {
                    parent[iterator.getV()] = iterator.getU();
                    keyValues[iterator.getV()] = iterator.getWeight();
                    // queue.priority(iterator.getV(), keyValues[iterator.getV()]);
                    queue.insert(iterator.getV(), keyValues[iterator.getV()]);
                    //totalWeight += iterator.getWeight();
                    //System.out.println(
                    //        "(" + parent[iterator.getV()] + ", " + iterator.getV() + "): " + iterator.getWeight());
                    //        System.out.println("");
                }
            }

            compares += queue.getResestComparisons();
        }

        totalWeight = 0f;
        for (int i = 0; i < graph.getV(); i++) {
            System.out.println("(" + parent[i] + ", " + i + ") :" +  graph.getWeight(i, parent[i]));
            totalWeight += graph.getWeight(i, parent[i]);
        }
        System.out.println("Total weight: " + totalWeight);
    }

    public void priority(PriorityQueueFloat queue, int key, Float p) {
        for (int i = 0; i < queue.getSize(); i++) {
            compares++;
            if (queue.getNodes().get(i).getNumber() == key) {
                compares++;
                if (queue.getNodes().get(i).getPriority() < p) {
                    continue;
                }

                int j = i;
                queue.getNodes().get(i).setPriority(p);

                while (j > 0 && queue.getNodes().get(queue.parent(j)).getPriority() > queue.getNodes().get(j)
                        .getPriority()) {
                    compares++;
                    Collections.swap(queue.getNodes(), j, queue.parent(j));
                    j = queue.parent(j);
                }

            }
        }
    }
}
