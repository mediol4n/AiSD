import java.util.ArrayList;
import java.util.Collections;

public class PriorityQueue {
    
    private int size = 0;
    ArrayList<NodeInt> queue;
    private int compares = 0;
    //private int shifts = 0;

    public int getResestComparisons() {
        int tmp = compares;
        compares = 0;
        return tmp;
    }

    PriorityQueue() {
        queue = new ArrayList<>();
    }
 
    ///
    public int parent(int i) {
        return i >> 1;
    }

    public int left(int i) {
        return i << 1;
    }

    public int right(int i) {
        return (i << 1) + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void top() {
        compares++;
        if(!isEmpty()) {
            System.out.println("[Top🟣] (" + queue.get(0).getNumber() + ", " + queue.get(0).getPriority() + ")");
        } else {
            System.out.println("[Top🟣] list is empty");
        }
    }

    public void pop() {
        compares++;
        if (isEmpty()) {
            System.out.println("[Pop🟡] list is empty");
            return;
        }

        NodeInt max = queue.get(0);
        queue.add(0, queue.get(size - 1));
        size--;
        heapify(0);
        System.out.println("[Pop🟡] (" + max.getNumber() + ", " + max.getPriority() + ")");
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest;

        compares++;
        if (l < size && queue.get(l).getPriority() < queue.get(i).getPriority()) {
            largest = l;
        } else {
            largest = i;
        }

        compares++;
        if (r < size && queue.get(r).getPriority() < queue.get(largest).getPriority()) {
            largest = r;
        }

        compares++;
        if (largest != i) {
            Collections.swap(queue, i, largest);
            heapify(largest);
        }
    }


    public void insert(int key, int priority) {
        NodeInt node = new NodeInt(key, priority);
        size++;
        queue.add(node);
        int i = size - 1;
        compares+=2;
        while(i > 0 && queue.get(parent(i)).getPriority() > priority) {
            compares+=2;
            Collections.swap(queue, i, parent(i));
            i = parent(i);
        }
    }


    public void priority(int key,int p) {
        for (int i = 0; i < size; i++) {
            compares++;
            if (queue.get(i).getNumber() == key) {
                compares++;
                if (queue.get(i).getPriority() < p) {
                    continue;
                }

                int j = i;
                queue.get(i).setPriority(p);

                while (j > 0 && queue.get(parent(j)).getPriority() > queue.get(j).getPriority()) {
                    compares++;
                    Collections.swap(queue, j, parent(j));
                    j = parent(j);
                }
            }
        }
    }


    public void print() {
        System.out.println("❆PRINT❆");
        System.out.print("Num/Prio: \t{ ");
        for (NodeInt node : queue) {
            System.out.print("(" + node.getNumber() + ", " + node.getPriority() + ")");
        }
    }



}


