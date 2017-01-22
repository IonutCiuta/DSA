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
        System.out.println();
        System.out.println(tree.findNode(6));
        System.out.println(tree.findNode(100));

        System.out.println(tree.findParent(1));
        System.out.println(tree.findParent(7));

        tree.deleteNode(9);
        tree.preorder();
        System.out.println();
        tree.deleteNode(4);
        tree.preorder();
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

    @Override
    public String toString() {
        return "Node " + value + " (" +
                (left != null ? left.value : "-") + ", " +
                (right != null ? right.value : "-") + ")";
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

    public Node findNode(int target) {
        return getReference(root, target);
    }

    private Node getReference(Node root, int target) {
        if(root == null || root.value == target) return root;
        else if(root.value > target) return getReference(root.left, target);
        else return getReference(root.right, target);
    }

    public Node findParent(int target) {
        if(root.value == target) return null;
        return getParentReference(root, target);
    }

    private Node getParentReference(Node parent, int target) {
        if(target < parent.value && parent.left != null) {
            if(parent.left.value == target)
                return parent;
            else
                return getParentReference(parent.left, target);
        }

        if(target > parent.value && parent.right != null) {
            if(parent.right.value == target)
                return parent;
            else
                return getParentReference(parent.right, target);
        }

        return null;
    }

    public boolean deleteNode(int target) {
        Node targetNode = findNode(target);
        if(targetNode == null) return false;

        Node parentNode = findParent(target);
        //Node is leaf
        if(targetNode.left == null && targetNode.right == null ) {
            if(parentNode.right.value == targetNode.value) {
                parentNode.right = null;
                return true;
            }

            if(parentNode.left.value == targetNode.value) {
                parentNode.left = null;
                return true;
            }
        }

        //Node has left subtree
        if(targetNode.left != null && targetNode.right == null) {
            if(targetNode.value < parentNode.value) {
                parentNode.left = targetNode.left;
            } else {
                parentNode.right = targetNode.left;
            }
            return true;
        }

        //Node has right subtree
        if(targetNode.right != null && targetNode.left == null) {
            if(targetNode.value < parentNode.value) {
                parentNode.left = targetNode.right;
            } else {
                parentNode.right = targetNode.right;
            }
            return true;
        }

        if(targetNode.left != null && targetNode.right != null) {
            Node largestLeftValue = targetNode.left;
            while(largestLeftValue.right != null) {
                largestLeftValue = largestLeftValue.right;
            }

            Node parentOfLargerstLeftValue = findParent(largestLeftValue.value);
            targetNode.value = largestLeftValue.value;
            parentOfLargerstLeftValue.left = null;
            return true;
        }

        return false;
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
                System.out.print(n + " ");
                explore(n.left, type);
                explore(n.right, type);
                break;

            case POSTORDER:
                explore(n.left, type);
                explore(n.right, type);
                System.out.print(n + " ");
                break;

            case INORDER:
                explore(n.left, type);
                System.out.print(n + " ");
                explore(n.right, type);
                break;
            }
        }
    }
}
