public class RBTree extends BST {

    private Node nullGuard = new Node(null);
    private Node root = nullGuard;
    private boolean test = true;
    public String inOrder = "";

    @Override
    public void insert(String value) {
        insert(new Node(value));
    }

    @Override
    public Node insert(Node node) {

        Node y = nullGuard;
        Node x = root;

        increaseMod(1);

        while (x != nullGuard) {

            y = x;
            increaseComp(1);

            if (node.getKey().compareTo(x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        node.setParent(y);
        if (y == nullGuard) {
            increaseMod(1);
            this.root = node;
        } else if (node.getKey().compareTo(y.getKey()) < 0) {
            increaseMod(1);
            increaseComp(1);
            y.setLeft(node);
        } else {
            increaseMod(1);
            increaseComp(1);
            y.setRight(node);
        }

        increaseMod(3);
        node.setLeft(nullGuard);
        node.setRight(nullGuard);

        node.setColor(Node.Color.RED);
        // System.out.println("dodano");
        insertFixUp(node);
        // System.out.println("naprawiono " + node.getColor().toString());
        return node;
    }

    private void insertFixUp(Node z) {
        increaseComp(1);

        // If [new] node parent is NOT black keep looping
        while (z.getParent().getColor() == Node.Color.RED) {
            increaseComp(1);
            // Parent is a left child
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                Node y = z.getParent().getParent().getRight();

                // If uncle is RED
                // <grand parent must have been black from property (4)>
                increaseComp(1);
                if (y.getColor() == Node.Color.RED) {

                    // Change colors of parent and uncle to BLACK
                    increaseMod(3);
                    z.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);

                    // Repeat in while loop with GRANDPARENT.
                    z.getParent().getParent().setColor(Node.Color.RED);
                    z = z.getParent().getParent();

                // If uncle is BLACK
                // <grand parent must have been black from property (4)>
                // There can be four cases which we need to solve
                } else {
                    increaseComp(1);
                    // Left Right Case if Z is Right Child
                    if (z == z.getParent().getRight()) {
                        z = z.getParent();
                        leftRotate(z);
                    }
                    increaseMod(2);

                    // Left Left Case & Color Swap
                    z.getParent().setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    rightRotate(z.getParent().getParent());
                }

            // Parent is a right child
            } else {
                Node y = z.getParent().getParent().getLeft();

                // If uncle is RED
                // <grand parent must have been black from property (4)>
                increaseComp(1);
                if (y.getColor() == Node.Color.RED) {

                    // Change colors of parent and uncle to BLACK
                    increaseMod(3);
                    z.getParent().setColor(Node.Color.BLACK);
                    y.setColor(Node.Color.BLACK);

                    // Repeat in while loop with GRANDPARENT.
                    z.getParent().getParent().setColor(Node.Color.RED);
                    z = z.getParent().getParent();

                // If uncle is BLACK
                // <grand parent must have been black from property (4)>
                // There can be four cases which we need to solve
                } else {
                    // Right Left Case
                    increaseComp(1);
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent();
                        rightRotate(z);
                    }

                    // Right Right Case & Color Swap
                    increaseMod(2);
                    z.getParent().setColor(Node.Color.BLACK);
                    z.getParent().getParent().setColor(Node.Color.RED);
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        increaseMod(1);
        this.root.setColor(Node.Color.BLACK);
    }

    private void rightRotate(Node z) {
        Node p = z.getLeft();
        increaseMod(1);
        z.setLeft(p.getRight());

        increaseComp(1);
        if (p.getRight() != nullGuard) {
            increaseMod(1);
            p.getRight().setParent(z);
        }

        increaseMod(1);
        p.setParent(z.getParent());

        if (z.getParent() == nullGuard) {
            increaseMod(1);
            this.root = p;
        } else if (z == z.getParent().getRight()) {
            increaseComp(1);
            increaseMod(1);
            z.getParent().setRight(p);
        } else {
            increaseComp(1);
            increaseMod(1);
            z.getParent().setLeft(p);
        }

        increaseMod(2);
        p.setRight(z);
        z.setParent(p);

    }

    private void leftRotate(Node g) {
        Node p = g.getRight();
        increaseMod(1);
        g.setRight(p.getLeft());

        increaseComp(1);
        if (p.getLeft() != nullGuard) {
            increaseMod(1);
            p.getLeft().setParent(g);
        }

        increaseMod(1);
        p.setParent(g.getParent());

        increaseComp(1);
        if (g.getParent() == nullGuard) {
            // It means [P] is new root
            increaseMod(1);
            this.root = p;

            // If [G] was right child
        } else if (g == g.getParent().getLeft()) {
            increaseComp(1);
            increaseMod(1);
            g.getParent().setLeft(p);

            // If [G] was left child
        } else {
            increaseComp(1);
            increaseMod(1);
            g.getParent().setRight(p);
        }

        // [P] is now parent of [G]
        p.setLeft(g);
        g.setParent(p);
    }

    private void swap(Node u, Node v) {
        // If was root
        increaseComp(1);
        if (u.getParent() == nullGuard) {
            increaseMod(1);
            this.root = v;
            // If the Deleted Key was left child then set Child to be the new Child of Key's
            // Parent
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
        increaseMod(1);
        v.setParent(u.getParent());
    }

    @Override
    public void delete(String value) {
        Node toDelete = searchNode(root, value);
        System.out.println(toDelete.getKey());
        if (toDelete != nullGuard)
            deleteNodeRBT(toDelete);
    }

    private void deleteNodeRBT(Node toDelete) {
        Node x;
        Node.Color initColor = toDelete.getColor();

        increaseComp(1);
        if (toDelete.getLeft() == nullGuard) {
            // System.out.println(1);
            x = toDelete.getRight();
            swap(toDelete, toDelete.getRight());
        } else if (toDelete.getRight() == nullGuard) {
            // System.out.println(2);
            increaseComp(1);
            x = toDelete.getLeft();
            swap(toDelete, toDelete.getLeft());
        } else {
            // System.out.println(3);
            increaseComp(1);
            Node y = minimum(toDelete.getRight());
            initColor = y.getColor();
            // System.out.println(y.getKey());

            x = y.getRight();
            increaseComp(1);
            if (y.getParent() != toDelete) {
                swap(y, y.getRight());
                increaseMod(2);
                y.setRight(toDelete.getRight());
                y.getRight().setParent(y);
            } else {
                increaseMod(1);
                x.setParent(y);
            }

            swap(toDelete, y);
            increaseMod(3);
            y.setLeft(toDelete.getLeft());
            y.getLeft().setParent(y);
            y.setColor(toDelete.getColor());
        }

        increaseComp(1);
        if (initColor == Node.Color.BLACK) {
            deleteFixUp(x);
        }
    }

    private void deleteFixUp(Node x) {
        increaseComp(2);
        while (x != this.root && x.getColor() == Node.Color.BLACK) {
            increaseComp(3);
            // if x is left son
            if (x == x.getParent().getLeft()) {
                Node s = x.getParent().getRight();

                increaseComp(1);
                if (s.getColor() == Node.Color.RED) {
                    increaseMod(2);
                    s.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    leftRotate(x.getParent());
                    s = x.getParent().getRight();
                }

                increaseComp(2);
                if (s.getLeft().getColor() == Node.Color.BLACK && s.getRight().getColor() == Node.Color.BLACK) {
                    increaseMod(1);
                    s.setColor(Node.Color.RED);
                    x = x.getParent();
                } else {
                    increaseComp(1);
                    if (s.getRight().getColor() == Node.Color.BLACK) {
                        increaseMod(2);
                        s.getLeft().setColor(Node.Color.RED);
                        rightRotate(s);
                        s = x.getParent().getRight();
                    }

                    increaseMod(3);
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(Node.Color.BLACK);
                    leftRotate(x.getParent());
                    x = this.root;
                }
            } else {
                Node s = x.getParent().getLeft();

                increaseComp(1);
                if (s.getColor() == Node.Color.RED) {
                    increaseMod(2);
                    s.setColor(Node.Color.BLACK);
                    x.getParent().setColor(Node.Color.RED);
                    rightRotate(x.getParent());
                    s = x.getParent().getLeft();
                }

                increaseComp(2);
                if (s.getRight().getColor() == Node.Color.BLACK && s.getLeft().getColor() == Node.Color.BLACK) {
                    increaseMod(1);
                    s.setColor(Node.Color.RED);
                    x = x.getParent();
                } else {
                    increaseComp(1);
                    if (s.getLeft().getColor() == Node.Color.BLACK) {
                        increaseMod(2);
                        s.getRight().setColor(Node.Color.RED);
                        leftRotate(s);
                        s = x.getParent().getLeft();
                    }

                    increaseMod(3);
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(Node.Color.BLACK);
                    rightRotate(x.getParent());
                    x = this.root;
                }
            }
        }
        increaseMod(1);
        x.setColor(Node.Color.BLACK);
        print();
    }

    @Override
    public String inOrder() {
        inOrder = "";
        System.out.println();
        inOrderTraversal(this.root);
        System.out.println();
        return inOrder;
    }

    protected void inOrderTraversal(Node node) {
        if (node != nullGuard) {
            inOrderTraversal(node.getLeft());
            System.out.print(node.getKey() + " ");
            inOrder = inOrder + node.getKey() + " ";
            inOrderTraversal(node.getRight());
        }
    }

    /**
     * minimalnie 2 porównania - drzewo puste lub szukamy roota średni case jest
     * taki sam jak worst czy opiewa wokol log_2(n) wynika to z własności drzewa.
     * Zawsze zaczynamy w roocie, a czarna ścieżka od roota do liścia ma tyle samo
     * czarnych węzłów
     */

    public Node searchNode(Node node, String key) {
        increaseComp(1);

        while (node != nullGuard && key.compareTo(node.getKey()) != 0) {
            if (key.compareTo(node.getKey()) < 0) {
                node = node.getLeft();
                increaseComp(1);
            } else if (key.compareTo(node.getKey()) > 0) {
                node = node.getRight();
                increaseComp(1);
            } else {
                increaseComp(2);
                return node;
            }
        }
        return node;
    }

    @Override
    public boolean search(String value) {
        if (searchNode(this.root, value) != nullGuard) {
            if (!this.test)
                System.out.println("Key: [" + value + "] exists");
            return true;
        } else {
            if (!this.test)
                System.out.println("Key: [" + value + "] doesn't exist");
            return false;
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
            while (myRoot != nullGuard) {
                if (z.getKey().compareTo(myRoot.getKey()) < 0) {
                    succ = myRoot;
                    myRoot = myRoot.getLeft();
                } else if (z.getKey().compareTo(myRoot.getKey()) > 0) {
                    myRoot = myRoot.getRight();
                } else {
                    break;
                }
            }
            if (succ != nullGuard) {
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

    /**
     * tutaj w kzadym wypadku wynik opiewa wokol log_2(n)
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
     * podobnie jak minimum
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
    public String print() {
        return this.root.toString();
    }

}
