package Tree.BST;

public class BinarySearchTree<T extends Comparable<T>, V> implements BinaryTreeInterface<T, V> {

    private Node root; // Nút gốc của cây

    // Lớp nút cho cây
    private class Node {
        T key;
        V value;
        Node left, right;

        public Node(T key, V value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
    }

    @Override
    public void insert(T key, V value) {
        root = insertRec(root, key, value);
    }

    private Node insertRec(Node root, T key, V value) {
        if (root == null) {
            return new Node(key, value);
        }

        // Duyệt xuống cây
        if (key.compareTo(root.key) < 0) {
            root.left = insertRec(root.left, key, value);
        } else if (key.compareTo(root.key) > 0) {
            root.right = insertRec(root.right, key, value);
        }

        return root;
    }

    @Override
    public V search(T key) {
        return searchRec(root, key);
    }

    private V searchRec(Node root, T key) {
        if (root == null || key.equals(root.key)) {
            return (root != null) ? root.value : null;
        }

        if (key.compareTo(root.key) < 0) {
            return searchRec(root.left, key);
        }

        return searchRec(root.right, key);
    }

    @Override
    public void delete(T key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, T key) {
        if (root == null) {
            return null;
        }

        // Duyệt xuống cây
        if (key.compareTo(root.key) < 0) {
            root.left = deleteRec(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = deleteRec(root.right, key);
        } else {
            // Nút với một hoặc không có con
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Nút với hai con: tìm nút kế tiếp trong thứ tự in-order (nút nhỏ nhất trong cây con phải)
            root.key = minValue(root.right);

            // Xoá nút kế tiếp
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    private T minValue(Node root) {
        T minValue = root.key;
        while (root.left != null) {
            minValue = root.left.key;
            root = root.left;
        }
        return minValue;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void preOrderTraversal() {
        preOrderTraversal(root);
    }

    private void preOrderTraversal(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }
    }

    @Override
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.key + " ");
            inOrderTraversal(root.right);
        }
    }

    @Override
    public void postOrderTraversal() {
        postOrderTraversal(root);
    }

    private void postOrderTraversal(Node root) {
        if (root != null) {
            postOrderTraversal(root.left);
            postOrderTraversal(root.right);
            System.out.print(root.key + " ");
        }
    }
}
