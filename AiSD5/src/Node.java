public class Node {

    private int number;
    private Float priority;

    Node(int number, Float priority) {
        this.number = number;
        this.priority =  priority > 0 ? priority : 0;
    }

    public void setPriority(Float priority) {
        if (priority > 0) {
            this.priority = priority;
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Float getPriority() {
        return priority;
    }

}
