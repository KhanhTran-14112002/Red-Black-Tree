package redblacktree;

/**
 * Định nghĩa một nút trong cây đỏ-đen.
 */
class RedBlackNode<T extends Comparable<T>, V> {

    /** Possible color for this node */
    public static final int BLACK = 0;
    /** Possible color for this node */
    public static final int RED = 1;

    // the key of each node
    public T key;
    // additional data associated with the key
    public V data;
    public int level;
    private int height;

    /** Parent of node */
    RedBlackNode<T, V> parent;
    /** Left child */
    RedBlackNode<T, V> left;
    /** Right child */
    RedBlackNode<T, V> right;
    // the number of elements to the left of each node
    public int numLeft = 0;
    // the number of elements to the right of each node
    public int numRight = 0;
    // the color of a node
    public int color;

    // Default constructor
    RedBlackNode() {
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }
    public RedBlackNode(T key, RedBlackNode leftChild, RedBlackNode rightChild) {
        this.left = leftChild;
        this.right = rightChild;
        this.key = key;
        if (leftChild == null && rightChild == null) {
            height = 1;
        } else if (leftChild == null) {
            height = rightChild.getHeight() + 1;
        } else if (rightChild == null) {
            height = leftChild.getHeight() + 1;
        } else {
            height = Math.max(leftChild.getHeight(), rightChild.getHeight()) + 1;
        }
    }

    public void setColor(int color_input) {
        this.color = color_input;
    }


    // Constructor with key
    RedBlackNode(T key, V data) {
        this();
        this.key = key;
        this.data = data;
    }

    public T getKey() {
        return key;
    }

    public V getData() {
        return data;
    }

    public RedBlackNode<T, V> getParent() {
        return parent;
    }

    public RedBlackNode<T, V> getLeft() {
        return left;
    }

    public RedBlackNode<T, V> getRight() {
        return right;
    }

    public int getNumLeft() {
        return numLeft;
    }

    public int getNumRight() {
        return numRight;
    }

    public int getColor() {
        return color;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String colorNode;
        if (color==1){
             colorNode = "RED";
        }else {
            colorNode = "Black";
        }

        return "RedBlackNode{" +
                "key=" + key +
                ", data=" + data +
                ", color=" + colorNode +
                '}';
    }
}
