public class BTree {
    private static final int T = 3;
    private Node root = new Node(T);

    private long operationCount;

    private static class Node {
        int n;
        Integer[] key = new Integer[2 * T - 1];
        Node[] child = new Node[2 * T];
        boolean leaf = true;

        Node(int t) { n = t; }

        int find(int k) {
            for (int i = 0; i < this.n; i++) {
                if (this.key[i] == k) {
                    return i;
                }
            }
            return -1;
        };
    }

    public Node search(Node x, int k) {
        int i = 0;
        while (i < x.n && k > (x.key[i] != null ? x.key[i].intValue() : Integer.MIN_VALUE)) {
            i++;
        }
        if (i < x.n && k == (x.key[i] != null ? x.key[i].intValue() : Integer.MIN_VALUE)) {
            return x;
        } else if (x.leaf) {
            return null;
        } else {
            return search(x.child[i], k);
        }
    }





    private void insert(int k) {
        operationCount++;
        Node r = root;
        if (r.n == 2 * T - 1) {
            Node s = new Node(T);
            root = s;
            s.child[0] = r;
            split(s, 1, r);
            insertNonFull(s, k);
        } else {
            insertNonFull(r, k);
        }
    }

    private void insertNonFull(Node x, int k) {
        int i = x.n;
        if (x.leaf) {
            x.key[i] = k;
            x.n++;
            sort(x.key, x.n);
        } else {
            while (i >= 1 && k < x.key[i - 1]) {
                i--;
            }
            i++;
            Node xi = x.child[i];
            if (xi.n == 2 * T - 1) {
                split(x, i, xi);
                if (k > x.key[i - 1]) {
                    i++;
                }
            }
            insertNonFull(x.child[i], k);
        }
    }

    private void split(Node x, int i, Node xi) {
        Node t = new Node(T);
        x.child[i] = t;
        x.key[i - 1] = xi.key[T - 1];
        x.n++;
        t.key[T - 1] = xi.key[2 * T - 2];
        t.n = T;
        xi.n = T - 1;
        if (!xi.leaf) {
            for (int j = 0; j < T; j++) {
                t.child[j] = xi.child[j + T];
            }
        }
    }

    public void sort(Integer[] array, int n) {
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                if (array[i] != null && array[j] != null && array[i].intValue() > array[j].intValue()) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }


    public boolean delete(Node x, int k) {
        int i = 0;
        while (i < x.n && k > (x.key[i] != null ? x.key[i].intValue() : Integer.MIN_VALUE)) {
            i++;
        }
        if (i < x.n && k == (x.key[i] != null ? x.key[i].intValue() : Integer.MIN_VALUE)) {
            delete(x, i);
            return true;
        } else {
            if (!x.leaf) {
                return delete(x.child[i], k);
            }
            return false;
        }
    }



    private int getPredecessor(Node x) {
        if (x.leaf) {
            return x.key[x.n - 1];
        } else {
            return getPredecessor(x.child[x.n]);
        }
    }

    private int getSuccessor(Node x) {
        if (x.leaf) {
            return x.key[0];
        } else {
            return getSuccessor(x.child[0]);
        }
    }

    private void merge(Node x, int i, Node y, Node z) {
        x.key[i] = y.key[y.n - 1];
        y.key[T - 1] = x.key[i];
        for (int j = 0; j < z.n; j++) {
            y.key[j + T] = z.key[j];
        }
        if (!y.leaf) {
            for (int j = 0; j <= z.n; j++) {
                y.child[j + T] = z.child[j];
            }
        }
        for (int j = i + 1; j < x.n; j++) {
            x.key[j - 1] = x.key[j];
            x.child[j] = x.child[j + 1];
        }
        x.n--;
        y.n = 2 * T - 2;
    }

    public boolean contains(int k) {
        operationCount++;
        Node x = search(root, k);
        return (x != null);
    }

    public void add(int k) {
        operationCount++;
        insert(k);
    }

    public boolean remove(int k) {
        operationCount++;
        return delete(root, k);
    }
    public long getOperationCount() {
        return operationCount;
    }
}


