public class BST extends Tree {

    public Node root;
    private Node nullGuard;
    private boolean test = true;
    public String inOrder = "";

    BST() {
        root = null;
        nullGuard = null;
    }

    @Override
    public void insert(String value) {
        insert(new Node(value));
        
    }


    protected Node insert(Node node) {
        Node y = nullGuard;
        Node x = root;

        increaseMod(1);

        while (x != nullGuard) {

            y = x;
            increaseComp(2);

            if (node.getKey().compareTo(x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        node.setParent(y);
        increaseComp(2);
        if (y == nullGuard) {
            increaseMod(1);
            this.root = node;
        } else if (node.getKey().compareTo(y.getKey()) < 0) {
            increaseMod(1);
            y.setLeft(node);
        } else {
            increaseMod(1);
            y.setRight(node);
        }
        return node;
        
    }

    @Override
    public void delete(String value) {
        Node toDeleteNode = searchNode(root, value);
        if (toDeleteNode != nullGuard) deleteNode(toDeleteNode);
        
    }

    protected Node deleteNode(Node del) {
        increaseComp(1);
        if (del.getLeft() == nullGuard) {
            return swap(del, del.getRight());
        } else if (del.getRight() == nullGuard) {
            increaseComp(1);
            return swap(del, del.getLeft());
        } else {
            increaseComp(1);
            Node y = minimum(del.getRight());
            increaseComp(1);
            if (y.getParent() != del) {
                swap(y, y.getRight());
                increaseMod(2);
                y.setRight(del.getRight());
                y.getRight().setParent(y);
            }
            swap(del, y);
            increaseMod(2);
            y.setLeft(del.getLeft());
            y.getLeft().setParent(y);
            return y;
        }
    }

    private Node swap(Node u, Node v) {
        // If was root
        increaseComp(1);
        if (u.getParent() == nullGuard) {
            increaseMod(1);
            this.root = v;
        // If the Deleted Key was left child then set Child to be the new Child of Key's Parent
        } else if (u == u.getParent().getLeft()) {
            increaseComp(1);
            increaseMod(1);
            u.getParent().setLeft(v);
        // Do the same if it was right child instead
        } else {
            increaseComp(1);
            increaseMod(1);
            u.getParent().setRight(v);
        }
        // And inform the child about it's new parent
        increaseComp(1);
        if (v != nullGuard) {
            increaseMod(1);
            v.setParent(u.getParent());
        }
        return v;
    }

    /**
     * minimalnie 2 porównania - szukamy roota
     * maksymalnie 3*n zliczając sprawdzanie czy nie doszliśmy do końca
     * Jest tak w przypadku dodawania elementów rosnąco lub malejąco
     * average case w arkuszu
     */
    public Node searchNode(Node node, String key) {
        increaseComp(2);

        while (node != nullGuard && key.compareTo(node.getKey()) != 0) {
            increaseComp(3);
            if (key.compareTo(node.getKey()) < 0) {
                node = node.getLeft();
            } else if (key.compareTo(node.getKey()) > 0) {
                node = node.getRight();
            } else {
                return node;
            }
        }
        return node;
    }

    /**
     * minimalnie 1 porównanie - drzewo puste lub w przypadku
     * maksymalnie n porównań - insert odwrotnie posortowanych elementów
     */
    
    @Override
    public Node minimum(Node x) {
        increaseComp(1);
        if (x != nullGuard) {
            while (x.getLeft() != nullGuard) {
                increaseComp(1);
                x = x.getLeft();
            }
           // System.out.println("Minimum: [" + x.getKey() + "]");
        }
        return x;
    }

    @Override
    public Node minimum() {
        return minimum(this.root);
    }

    /**
     * identycznie jak w minimum, worst case jest, gdy insertowaliśmy elementy posortowane
     */

    @Override
    public Node maximum() {
        increaseComp(1);
        Node x = root;
        if (x != nullGuard) {
            while (x.getRight() != nullGuard) {
                increaseComp(1);
                x = x.getRight();
            }
           // System.out.println("Maximum: [" + x.getKey() + "]");
        }
        return x;
        
    }

    @Override
    public boolean search(String value) {
        if (searchNode(this.root, value) != nullGuard) {
            if (!this.test) System.out.println("Key: [" + value + "] exists");
            return true;
        } else {
            if (!this.test) System.out.println("Key: [" + value + "] doesn't exist");
            return false;
        }
        
    }

    @Override
    public String inOrder() {
        inOrder = "";
        System.out.println();
        inOrderTraversal(this.root);
        System.out.println();
        return this.inOrder;
    }

    protected void inOrderTraversal(Node node) {
        if (node != nullGuard) {
            inOrderTraversal(node.getLeft());
            System.out.print(node.getKey() + " ");
            inOrder = inOrder + node.getKey() + " ";
            inOrderTraversal(node.getRight());
        }
    }

    @Override
    public Node successor(String value) {
        Node z = searchNode(this.root, value);
        if (searchNode(this.root, value) != nullGuard) {
            if (z.getRight() != nullGuard) {
                System.out.println("Node [" + z.getKey() + "] has successor [" + minimum(z.getRight()).getKey() + "].");
                return minimum(z.getRight());
            }

            Node succ = nullGuard;
            Node myRoot = root;
            while (myRoot  != nullGuard) {
                if (z.getKey().compareTo(myRoot.getKey()) < 0) {
                    succ = myRoot;
                    myRoot = myRoot.getLeft();
                } else if (z.getKey().compareTo(myRoot.getKey()) > 0 ) {
                    myRoot = myRoot.getRight();
                } else {
                    break;
                }
            }
            if (succ != null) {
                System.out.println("Node [" + z.getKey() + "] has successor [" + succ.getKey() + "].");
            } else {
                System.out.println("Node [" + z.getKey() + "] has no successor");
            }
            return succ;
        } else {
            System.out.println("Node doesn't exist!");
            return null;
        }        

    }

    @Override
    public String print() {
        return this.root.toString();
    }
    
}
