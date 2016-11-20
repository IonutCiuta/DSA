package bst;

/**
 * Ionut Ciuta on 11/20/2016.
 */
public class Test {
    public static void main(String[] args) {
        BST tree = new BST(7);
        tree.insert(4);
        tree.insert(8);
        tree.insert(1);
        tree.insert(5);
        tree.insert(9);
        tree.insert(6);

        tree.preorder();
        System.out.println();
        tree.inorder();
        System.out.println();
        tree.postorder();
    }
}

class Node {
    int value;
    Node left;
    Node right;

    Node() {}

    Node(int value) {
        this.value = value;
    }

    Node(int value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

enum Traversal {
    PREORDER, POSTORDER, INORDER
}

class BST {
    Node root;

    BST() {}

    BST(int rootValue) {
        this.root = new Node(rootValue);
    }

    void insert(int value) {
        insertNode(root, value);
    }

    private void insertNode(Node root, int value) {
        if(root == null) {
            root = new Node(value);
            return;
        }

        if(value < root.value) {
            if(root.left == null) {
                root.left = new Node(value);
                return;
            } else {
                insertNode(root.left, value);
            }
        }

        if(value > root.value) {
            if(root.right == null) {
                root.right = new Node(value);
                return;
            } else {
                insertNode(root.right, value);
            }
        }
    }

    void preorder() {
        explore(root, Traversal.PREORDER);
    }

    void postorder() {
        explore(root, Traversal.POSTORDER);
    }

    void inorder() {
        explore(root, Traversal.INORDER);
    }

    private void explore(Node n, Traversal type) {
        if (n != null) {
            switch (type) {
            case PREORDER:
                System.out.print(n.value + " ");
                explore(n.left, type);
                explore(n.right, type);
                break;

            case POSTORDER:
                explore(n.left, type);
                explore(n.right, type);
                System.out.print(n.value + " ");
                break;

            case INORDER:
                explore(n.left, type);
                System.out.print(n.value + " ");
                explore(n.right, type);
                break;
            }
        }
    }
}
