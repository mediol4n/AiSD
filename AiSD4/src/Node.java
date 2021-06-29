public class Node {

    /**
     *      [parent]
     *          |
     *        [key]
     *         /  \
     *     [left] [right]
     * 
     */

    enum Color {
        RED,
        BLACK
    }
    
    private String key;
    private Node left = null;
    private Node right = null;
    private Node parent = null;
    private Color color;

    Node(String key) {
        this.key = key;
        this.color = Color.BLACK;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Node getLeft() {
        return this.left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return this.parent;
    }    

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        String sign;
        if (this.color == Color.RED) {
            sign = "🔴";
        } else {
            sign = "\u2B24";
        }
        buffer.append(key + sign);
        buffer.append('\n');
        if (left != null) {
            if (left.getKey() != null) {
                left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }
        } 
        if (right != null) {
            if (right.getKey() != null) {
                right.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            }
        }
    }

}
