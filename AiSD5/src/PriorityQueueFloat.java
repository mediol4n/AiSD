import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class PriorityQueueFloat {
    
    private ArrayList<Node> nodes = new ArrayList<>();
    private int size = 0;

    int compares = 0;

    
    public int getResestComparisons() {
        int tmp = compares;
        compares = 0;
        return tmp;
    }

    //for testing stuff
    private boolean silent;

    PriorityQueueFloat(boolean silent) {
        this.silent = silent;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    private HashMap<Integer, Integer> map = new HashMap<>();

    public ArrayList<Node> getQueue() {
        return nodes;
    }

    public int getSize() {
        return size;
    }

    public int parent(int i) {
        return i >> 1;
    }

    public int left(int i) {
        return i << 1;
    }

    public int right(int i) {
        return (i << 1) + 1;
    }

    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;

        compares++;
        if (l < size && nodes.get(l).getPriority() < nodes.get(smallest).getPriority()) {
            smallest = l;
        }

        compares++;
        if (r < size && nodes.get(r).getPriority() < nodes.get(smallest).getPriority()) {
            smallest = r;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    private void swap(int i, int j) {
        map.replace(nodes.get(i).getNumber(), j);
        map.replace(nodes.get(j).getNumber(), i);
        Collections.swap(nodes, i, j);
    }

    public void insert(int number, Float priority) {
        map.put(number, size);
        Node node = new Node(number, priority);
        size++;
        nodes.add(size - 1, node);
        decreaseNumber(size - 1, priority);
        if (!silent) System.out.println("[Insert🟢] (" + number + ", " + priority + ")");
        
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void pop() {
        compares++;
        if (isEmpty()) {
            System.out.println("[Pop🟡] list is empty");
            return;
        }

        Node max = nodes.get(0);
        nodes.set(0, nodes.get(size - 1));
        nodes.remove(size - 1);
        size--;
        heapify(0);
        if (!silent) System.out.println("[Pop🟡] (" + max.getNumber() + ", " + max.getPriority() + ")");
        map.remove(max.getNumber());
    }

    public Node top() {
        if (isEmpty()) {
            System.out.println("[Top🟣] list is empty");
            return null;
        } else {
            if (!silent) System.out.println("[Top🟣] (" + nodes.get(0).getNumber() + ", " + nodes.get(0).getPriority() + ")");
            return nodes.get(0);
        }
    }

    public void print() {
        System.out.print("Num/Prio: \t{ ");
        for (Node node : nodes) {
            System.out.print("(" + node.getNumber() + ", " + node.getPriority() + ")");
        }
        System.out.print("}\nNum/Order: \t");
        System.out.println(map.toString());
    }

    private void decreaseNumber(int i, Float priority) {

        if (priority > nodes.get(i).getPriority()) {
            return;
        }

        nodes.get(i).setPriority(priority);
        while (i >= 0 && nodes.get(parent(i)).getPriority() > nodes.get(i).getPriority()) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public void priority(int number, Float priority) {
        if (map.get(number) != null) {
            int index = map.get(number);
            decreaseNumber(index, priority);
        }
    }

    public boolean contains(int number) {
        return map.get(number) != null;
    }

    void runMode() {
        Scanner s = new Scanner(System.in);
        System.out.println("[Priority Queue]\nAvailable Commands: 'insert <x> <p>', 'empty', 'top', 'pop', 'priority <x> <p>', 'print'");
        System.out.print("Please input desired number of actions: ");
        int actionsLimit = s.nextInt();

        // Turn off silent mode to print Top / Pop
        silent = false;

        System.out.println("Scanning for actions:");
        for (int n = 0; n < actionsLimit; n++) {
            String str = s.next();
            switch (str) {
                case "insert": {
                    int x = s.nextInt();
                    float p = s.nextFloat();
                    insert(x, p);
                    break;
                }
                case "empty": {
                    System.out.println(isEmpty());
                    break;
                }
                case "top": {
                    top();
                    break;
                }
                case "pop": {
                    pop();
                    break;
                }
                case "priority": {
                    int x = s.nextInt();
                    Float p = s.nextFloat();
                    priority(x, p);
                    break;
                }
                case "print": {
                    print();
                    break;
                }
                default: {
                    System.err.println("Couldn't find an option for this input.");
                    n--;
                    break;
                }
            }
        }
        s.close();
    }
}
