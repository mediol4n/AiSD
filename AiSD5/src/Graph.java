import java.util.ArrayList;

public class Graph {
    

    private ArrayList<ArrayList<Edge>> edges = new ArrayList<>();
    private int e;
    private int v;



    Graph(int v) {
        this.v = v;
        this.e = 0;

        for (int i = 0; i < v; i++) {
            edges.add(i, new ArrayList<>());
        }
    }


    public void addWeightedEdge(int u, int v, Float weight) {
        Edge edge = new Edge(u, v, weight);
        edges.get(u).add(edge);
        e++;
    }


    public ArrayList<ArrayList<Edge>> getEdges() {
        return edges;
    }

    public int getV() {
        return v;
    }

    public int getE() {
        return e;
    }

    void addEdge(int u, int v) {
        Edge edge = new Edge(u, v);
        edges.get(u).add(edge);
        e++;
    }

    float getWeight(int u, int v) {
        for (Edge edge : edges.get(u)) {
            if (edge.getU() == u && edge.getV() == v) {
                return edge.getWeight();
            }
        }
        return 0f;
    }
}
