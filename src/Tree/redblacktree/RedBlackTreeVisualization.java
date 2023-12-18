package Tree.redblacktree;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
        tree.insert(50, "Data10");
        tree.insert(75, "Data5");
        tree.insert(25, "Data15");
        tree.insert(80, "Data3");
        tree.insert(100, "Data7");
        tree.insert(110, "Data0");
        tree.insert(115, "Data47");
        tree.insert(120, "Data37");

        SwingUtilities.invokeLater(() -> new RedBlackTreeVisualization<>(tree));

//        RedBlackTree<Integer, String> tree = new RedBlackTree<>();
////
////        Scanner scanner = new Scanner(System.in);
////
////        System.out.print("Enter the size of the tree (n): ");
////        int n = scanner.nextInt();
////
////        Random random = new Random();
////
////        System.out.println("Inserting random keys into the tree...");
////
////        for (int i = 0; i < n; i++) {
////            int key = random.nextInt(100); // Số ngẫu nhiên từ 0 đến 99 (có thể thay đổi)
////            String data = "Data" + key;
////            tree.insert(key, data);
////        }

    }
}