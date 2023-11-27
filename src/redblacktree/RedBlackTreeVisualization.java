package redblacktree;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.List;

public class RedBlackTreeVisualization<T extends Comparable<T>, V> extends JFrame {

    private RedBlackTree<T, V> tree;

    public RedBlackTreeVisualization(RedBlackTree<T, V> tree) {
        setTitle("Red-Black Tree Visualization");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.tree = tree;

        TreeDrawer<T, V> treeDrawer = new TreeDrawer<>(tree);
        add(treeDrawer);

        setVisible(true);
    }

    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();
        tree.insert(10, "Data10");
        tree.insert(5, "Data5");
        tree.insert(15, "Data15");
        tree.insert(3, "Data3");
        tree.insert(7, "Data7");
        tree.insert(8, "Data0");
        tree.insert(17, "Data47");
        tree.insert(14, "Data37");
        tree.insert(11, "Data37");
        tree.insert(948, "Data37");

        SwingUtilities.invokeLater(() -> new RedBlackTreeVisualization<>(tree));

        System.out.println("Size of the tree: " + tree.size());

        // In danh sách các phần tử nhỏ hơn 12
        System.out.println("Elements smaller than 12: " + tree.numSmaller(12));

        // In danh sách các phần tử lớn hơn 7
        System.out.println("Elements greater than 7: " + tree.numGreater(7));

        // In danh sách các phần tử lớn hơn 5 (sử dụng hàm getGreaterThan)
        List<Integer> greaterThan5 = tree.getGreaterThan(5, tree.size());
        System.out.println("Elements greater than 5: " + greaterThan5);

//        3(B) 5(R) 7(B) 8(R) 10(B) 11(R) 14(B) 15(R) 17(B) 948(R)

    }
}