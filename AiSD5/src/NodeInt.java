public class NodeInt {

    private int number;
    private int priority;

    NodeInt(int number, int priority2) {
        this.number = number;
        this.priority =  priority2 > 0 ? priority2 : 0;
    }

    public void setPriority(int priority) {
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

    public int getPriority() {
        return priority;
    }

}
