public class Edge {

    // u ----> v
    private int u;
    private int v;
    private float weight;

    Edge(int u, int v, Float weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }
    
    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    public float getWeight() {
        return weight;
    }
}
