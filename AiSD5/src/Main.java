import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int mode = 4;

    public static void main(String[] args) {
        // PriorityQueue pq = new PriorityQueue(false);
        // pq.runMode();

        // Dijkstra dijkstra = new Dijkstra(getGraph(false, true));
        // dijkstra.runMode();

       // Graph graph = getGraph(false, true);
        //Kruskal kruskal = new Kruskal(graph);
        //kruskal.runMode();

        try {
            if (mode == 1) {
                kruskal();
            } else if (mode == 2) {
                prim();
            } else if (mode == 3) {
                Graph graph2 = createSimpleGraph();
                Dijkstra dijkstra = new Dijkstra(graph2);
                dijkstra.runMode();
            } else if (mode == 4) {
                Graph graph2 = createSimpleGraph();
                Kruskal kruskal = new Kruskal(graph2);
                kruskal.runMode();
                graph2 = createSimpleGraph();
                Prim prim = new Prim(graph2);
                prim.runMode();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Graph createSimpleGraph() {
        Graph graph = new Graph(9);
        graph.addWeightedEdge(0, 1, 0.4f);
        graph.addWeightedEdge(1, 0, 0.4f);
        graph.addWeightedEdge(0, 7, 0.8f);
        graph.addWeightedEdge(7, 0, 0.8f);
        graph.addWeightedEdge(1, 7, 1.1f);
        graph.addWeightedEdge(7, 1, 1.1f);
        graph.addWeightedEdge(1, 2, 0.8f);
        graph.addWeightedEdge(2, 1, 0.8f);
        graph.addWeightedEdge(8, 7, 0.7f);
        graph.addWeightedEdge(7, 8, 0.7f);
        graph.addWeightedEdge(7, 6, 0.1f);
        graph.addWeightedEdge(6, 7, 0.1f);
        graph.addWeightedEdge(6, 8, 0.6f);
        graph.addWeightedEdge(8, 6, 0.6f);
        graph.addWeightedEdge(2, 8, 0.2f);
        graph.addWeightedEdge(8, 2, 0.2f);
        graph.addWeightedEdge(2, 5, 0.4f);
        graph.addWeightedEdge(5, 2, 0.4f);
        graph.addWeightedEdge(2, 3, 0.7f);
        graph.addWeightedEdge(3, 2, 0.7f);
        graph.addWeightedEdge(6, 5, 0.2f);
        graph.addWeightedEdge(5, 6, 0.2f);
        graph.addWeightedEdge(3, 5, 1.4f);
        graph.addWeightedEdge(5, 3, 1.4f);
        graph.addWeightedEdge(4, 5, 1.0f);
        graph.addWeightedEdge(5, 4, 1.0f);
        graph.addWeightedEdge(3, 4, 0.9f);
        graph.addWeightedEdge(4, 3, 0.9f);
        return graph;
    }

    public static Graph getGraph(boolean directed, boolean weighted) {
        // Scan for the desired amount of Vertices and Edges in whole Graph(V,E) and
        // Create
        Scanner s = new Scanner(System.in);
        System.out.print("Vertices and Edges amount:");
        int totalV = s.nextInt();
        int totalE = s.nextInt();
        Graph graph = new Graph(totalV);

        // Setup edge connections between Vertices (u,v,w -> starting v, ending v,
        // weight of e)
        if (weighted) {
            for (int n = 0; n < totalE; n++) {
                int u, v;
                float w;
                u = s.nextInt();
                v = s.nextInt();
                w = s.nextFloat();

                // Add Edge and it's reverse to get undirected graph
                graph.addWeightedEdge(u, v, Float.valueOf(w));
                if (!directed)
                    graph.addWeightedEdge(v, u, Float.valueOf(w));
            }
        } else {
            for (int n = 0; n < totalE; n++) {
                int u, v;
                u = s.nextInt();
                v = s.nextInt();

                // Add Edge and it's reverse to get undirected graph
                graph.addEdge(u, v);
                if (!directed)
                    graph.addEdge(v, u);
            }
        }
        s.close();
        return graph;
    }

    // Dijkstra testing
    static void runTestDijkstra() throws IOException {
        String fileNames[] = { "ug8", "ug250", "ug1000", "ug10000" };
        FileWriter fileWriter = new FileWriter("tests/dijkstra.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        BufferedReader reader;
        writer.write("v;e;time;compares\n");
        for (int iterator = 0; iterator < fileNames.length; iterator++) {
            System.out.println(iterator);
            FileReader fileReader = new FileReader("data/" + fileNames[iterator] + ".txt");
            reader = new BufferedReader(fileReader);
            String line;
            int u = 0, v;
            float w;
            int totalV = Integer.parseInt(reader.readLine().split(" ")[0]);
            // not necessary, do while until end of file
            int totalE = Integer.parseInt(reader.readLine().split(" ")[0]);
            Graph graph = new Graph(totalV);
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\s+", " ").trim();
                String split[] = line.split(" ");
                u = Integer.parseInt(split[0]);
                v = Integer.parseInt(split[1]);
                w = Float.parseFloat(split[2]);
                // we dont have to add reverse edges
                graph.addWeightedEdge(u, v, w);
            }

            Dijkstra dijkstra = new Dijkstra(graph);
            long startTime = System.nanoTime();
            dijkstra.runAlgorithm(u);
            writer.write(fileNames[iterator].substring(1) + ";" + totalE + ";"
                    + ((System.nanoTime() - startTime) / 1000000) + ";" + dijkstra.getResestComparisons() + "\n");

        }
        writer.close();

    }

    private static int generateRandomNumber(int bound) {
        Random random = new Random(System.nanoTime() * System.currentTimeMillis() * bound);
        return random.nextInt(bound);
    }

    public static void testPriorityQueue() throws IOException {
        FileWriter fileWriter = new FileWriter("tests/pq2.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write("n;ins_cmp;top_cmp;prio_cmp;pop_cmp;");
        PriorityQueue priorityQueue = new PriorityQueue();
        for (int i = 10; i <= 10000; i += 10) {
            for (int j = 0; j < i - 1; j++) {
                priorityQueue.insert(generateRandomNumber(i), generateRandomNumber(i / 2));
                priorityQueue.getResestComparisons();
            }
            int random_inserted = generateRandomNumber(i);
            priorityQueue.insert(random_inserted, 0);
            int insert_cmp = priorityQueue.getResestComparisons();
            priorityQueue.top();
            int top_cmp = priorityQueue.getResestComparisons();
            priorityQueue.priority(generateRandomNumber(i), i / 4);
            int prio_cmp = priorityQueue.getResestComparisons();
            priorityQueue.pop();
            int pop_cmp = priorityQueue.getResestComparisons();
            writer.write(i + ";" + insert_cmp + ";" + top_cmp + ";" + prio_cmp + ";" + pop_cmp + ";\n");
            priorityQueue = new PriorityQueue();
        }

        writer.flush();
        writer.close();
    }

    // Test prim and kruskal

    private static void prim() throws IOException {
        String fileNames[] = { "g8", "g250", "g1000", "g10000" };
        FileWriter fileWriter = new FileWriter("tests/prim.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        BufferedReader reader;
        writer.write("v;e;time;compares\n");
        for (int iterator = 0; iterator < fileNames.length; iterator++) {
            System.out.println(iterator);
            FileReader fileReader = new FileReader("data/" + fileNames[iterator] + ".txt");
            reader = new BufferedReader(fileReader);
            String line;
            int u = 0, v;
            float w;
            int totalV = Integer.parseInt(reader.readLine().split(" ")[0]);
            // not necessary, do while until end of file
            int totalE = Integer.parseInt(reader.readLine().split(" ")[0]);
            Graph graph = new Graph(totalV);
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\s+", " ").trim();
                String split[] = line.split(" ");
                u = Integer.parseInt(split[0]);
                v = Integer.parseInt(split[1]);
                w = Float.parseFloat(split[2]);
                // we dont have to add reverse edges
                graph.addWeightedEdge(u, v, w);
            }
            Prim pr = new Prim(graph);
            long startTime = System.nanoTime();
            pr.runMode();
            writer.write(fileNames[iterator].substring(1) + ";" + totalE + ";"
                    + ((System.nanoTime() - startTime) / 1000000) + ";" + pr.getResestComparisons() + "\n");
        }

        writer.flush();
        writer.close();
    }

    private static void kruskal() throws IOException {
        String fileNames[] = { "g8", "g250", "g1000", "g10000" };
        FileWriter fileWriter = new FileWriter("tests/kruskal.csv");
        BufferedWriter writer = new BufferedWriter(fileWriter);
        BufferedReader reader;
        writer.write("v;e;time;compares\n");
        for (int iterator = 0; iterator < fileNames.length; iterator++) {
            System.out.println(iterator);
            FileReader fileReader = new FileReader("data/" + fileNames[iterator] + ".txt");
            reader = new BufferedReader(fileReader);
            String line;
            int u = 0, v;
            float w;
            int totalV = Integer.parseInt(reader.readLine().split(" ")[0]);
            // not necessary, do while until end of file
            int totalE = Integer.parseInt(reader.readLine().split(" ")[0]);
            Graph graph = new Graph(totalV);
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\s+", " ").trim();
                String split[] = line.split(" ");
                u = Integer.parseInt(split[0]);
                v = Integer.parseInt(split[1]);
                w = Float.parseFloat(split[2]);
                // we dont have to add reverse edges
                graph.addWeightedEdge(u, v, w);
            }
            Kruskal kruskal = new Kruskal(graph);
            long startTime = System.nanoTime();
            kruskal.runMode();
            writer.write(fileNames[iterator].substring(1) + ";" + totalE + ";"
                    + ((System.nanoTime() - startTime) / 1000000) + ";" + kruskal.getResestComparisons() + "\n");
        }

        writer.flush();
        writer.close();
    }

}
