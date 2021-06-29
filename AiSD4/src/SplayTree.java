public class SplayTree extends BST {
    private boolean test = true;
    private Node nullGuard = null;
    public Node root = nullGuard;
    public String inOrder = "";

    @Override
    public Node successor(String value) {
        Node z = searchNode(this.root, value);
        if (searchNode(this.root, value) != nullGuard) {
            if (z.getRight() != nullGuard) {
                System.out.println("Node [" + z.getKey() + "] has successor [" + minimum(z.getRight()).getKey() + "].");
                return minimum(z.getRight());
            }

            Node succ = null;
            Node myRoot = root;
            while (myRoot  != null) {
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

    /**
     * Minimalnie 4 porównania w przypadku szukania roota 
     * lub 3 gdy drzewo jest puste
     * w worst case mamy n+1 porownan z metody znad klasy 
     */
    @Override
    public Node searchNode(Node x, String k) {
        Node node = super.searchNode(x, k);
        increaseComp(1);
        if (node != nullGuard) {
            increaseComp(1);
            while(node.getParent() != nullGuard) {
                increaseComp(1);
                this.splay(node);
            }
        }
        return node;
    }

    @Override
    public void insert(String value) {
        insert(new Node(value));
        
    }

    @Override
    public Node insert(Node z) {
        Node y = nullGuard;
        Node x = root;

        increaseMod(1);

        while (x != nullGuard) {

            y = x;
            increaseComp(2);

            if (z.getKey().compareTo(x.getKey()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);
        if (y == nullGuard) {
            increaseMod(1);
            this.root = z;
        } else if (z.getKey().compareTo(y.getKey()) < 0) {
            increaseMod(1);
            increaseComp(1);
            y.setLeft(z);
        } else {
            increaseMod(1);
            increaseComp(1);
            y.setRight(z);
        }
        Node nodeToReturn = z;
        increaseComp(1);
        if (nodeToReturn != nullGuard) {
            increaseComp(1);
            while (nodeToReturn.getParent() != nullGuard) {
                increaseComp(1);
                this.splay(nodeToReturn);
            }
        }
        return nodeToReturn;
    }

    @Override
    public void delete(String value) {
        Node toDeleteNode = searchNode(root, value);
        if (toDeleteNode != nullGuard) deleteNode(toDeleteNode);
        
    }

    @Override
    public Node deleteNode(Node z) {        
        increaseComp(1);
        Node removed = nullGuard;
        if (z.getLeft() == nullGuard) {
            removed = swap(z, z.getRight());
        } else if (z.getRight() == nullGuard) {
            increaseComp(1);
            removed = swap(z, z.getLeft());
        } else {
            increaseComp(1);
            Node y = minimum(z.getRight());
            increaseComp(1);
            if (y.getParent() != z) {
                swap(y, y.getRight());
                increaseMod(2);
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            swap(z, y);
            increaseMod(2);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            removed = y;
        }
        increaseComp(1);
        if (removed != nullGuard) {
            increaseComp(1);
            if (removed.getParent() != nullGuard) {
                Node p = removed.getParent();
                increaseComp(1);
                while (p.getParent() != nullGuard) {
                    increaseComp(1);
                    this.splay(p);
                }
            }
        }
        return removed;
    }

    public Node swap(Node u, Node v) {
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

    @Override
    public Node minimum(Node x) {
        increaseComp(1);
        if (x != nullGuard) {
            while (x.getLeft() != nullGuard) {
                increaseComp(1);
                x = x.getLeft();
            }
            //System.out.println("Minimum: [" + x.getKey() + "]");
        }
        return x;
    }

    @Override
    public Node minimum() {
        return minimum(this.root);
    }

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



    private void splay(Node node) {
        //we need node's parent and its parent
        Node p = node.getParent();
        increaseComp(1);
        Node g = (p != nullGuard) ? p.getParent() : nullGuard;
        increaseComp(1);

        // Case 1 - node is a root
        if (p != nullGuard) {
            increaseComp(1);
            //Case 2 - node is root child - Zig
            if (p == root) {
                root = node;
                node.setParent(nullGuard);
                increaseComp(1);
                increaseMod(1);
                //right rotate
                if (node == p.getLeft()) {
                    p.setLeft(node.getRight());
                    increaseComp(1);
                    increaseMod(1);
                    if (node.getRight() != nullGuard) {
                        node.getRight().setParent(p);
                        increaseMod(1);
                    }
                    increaseMod(2);
                    node.setRight(p);
                    p.setParent(node);
                //Left rotate
                } else {
                    p.setRight(node.getLeft());
                    increaseComp(1);
                    increaseMod(1);
                    if (node.getLeft() != nullGuard) {
                        increaseMod(1);
                        node.getLeft().setParent(p);
                    }
                    increaseMod(2);
                    node.setLeft(p);
                    p.setParent(node);
                }
            //case 3 - node has parent and GRANDPARENT
            } else if (g != nullGuard) {
                Node pg = g.getParent();
                increaseComp(2);

                if (pg != nullGuard && pg.getLeft() == g) {
                    pg.setLeft(node);
                    node.setParent(pg);
                    increaseMod(2);
                } else if (pg != nullGuard && pg.getRight() == g) {
                    pg.setRight(node);
                    node.setParent(pg);
                    increaseMod(2);
                } else {
                    root = node;
                    node.setParent(nullGuard);
                    increaseMod(1);
                }

                if ((node == p.getLeft() && p == g.getLeft()) || (node == p.getRight() && p == g.getRight())) {
                    increaseComp(5);
                    if (node == p.getLeft()) {
                        Node nr = node.getRight();
                        increaseMod(3);
                        node.setRight(p);
                        p.setParent(node);
                        p.setLeft(nr);
                        increaseComp(1);
                        if (nr != nullGuard) {
                            increaseMod(1);
                            nr.setParent(p);
                        }

                        Node pr = p.getRight();
                        increaseMod(3);
                        p.setRight(g);
                        g.setParent(p);
                        g.setLeft(pr);
                        increaseComp(1);
                        if (pr != nullGuard) {
                            pr.setParent(g);
                        }
                    } else {
                        Node nl = node.getLeft();
                        increaseMod(3);
                        node.setLeft(p);
                        p.setParent(node);
                        p.setRight(nl);
                        increaseComp(1);
                        if (nl != nullGuard) {
                            nl.setParent(p);
                        }

                        Node pl = p.getLeft();
                        increaseMod(3);
                        p.setLeft(g);
                        g.setParent(p);
                        g.setRight(pl);
                        increaseComp(1);
                        if (pl != nullGuard) {
                            pl.setParent(g);
                        }
                    }
                    return;
                }

                increaseComp(1);
                if (node == p.getLeft()) {
                    Node nl = node.getRight();
                    Node nr = node.getLeft();

                    increaseMod(6);
                    node.setRight(p);
                    p.setParent(node);
                    node.setLeft(g);
                    g.setParent(node);
                    p.setLeft(nl);
                    increaseComp(1);
                    if (nl != nullGuard) {
                        nl.setParent(p);
                        increaseMod(1);
                    }
                    g.setRight(nr);
                    increaseComp(1);
                    if (nr != nullGuard) {
                        nr.setParent(g);
                        increaseMod(1);
                    }
                    return;
                }

                if (node == p.getRight()) {
                    Node nl = node.getRight();
                    Node nr = node.getLeft();   
                    increaseMod(6);
                    node.setLeft(p);
                    p.setParent(node);
                    node.setRight(g);
                    g.setParent(node);
                    p.setRight(nl);
                    increaseComp(1);
                    if (nl != nullGuard) {
                        increaseMod(1);
                        nl.setParent(p);
                    }
                    g.setLeft(nr);
                    increaseComp(1);
                    if (nr != nullGuard) {
                        increaseMod(1);
                        nr.setParent(g);
                    }
                }
            }
        }
    }



    @Override
    public String print() {
        return this.root.toString();
    }
}
