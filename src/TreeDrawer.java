
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

class TreeDrawer<T extends Comparable<T>, V> extends JPanel {

    private RedBlackTree<T, V> tree;

    public TreeDrawer(RedBlackTree<T, V> tree) {
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawTree(g2d, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
    }

    private void drawTree(Graphics2D g, RedBlackNode<T, V> root, int x, int y, int xOffset) {
        if (!tree.isNil(root)) {
            if (!tree.isNil(root.getParent())) {
//                g.setColor(root.getParent().getColor() == RedBlackNode.BLACK ? Color.RED : Color.BLACK);
                g.setColor(root.getColor() == RedBlackNode.BLACK ? Color.BLACK : Color.RED);
                root.setColor(root.getColor() == RedBlackNode.BLACK ? RedBlackNode.BLACK : RedBlackNode.RED);
            } else {
                root.setColor(RedBlackNode.BLACK);
                g.setColor(Color.BLUE);
            }
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(root.getKey()), x - 5, y + 5);

            if (!tree.isNil(root.getLeft())) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(x, y, x - xOffset, y + 50);
                drawTree(g, root.getLeft(), x - xOffset, y + 50, xOffset / 2);
            }

            if (!tree.isNil(root.getRight())) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(x, y, x + xOffset, y + 50);
                drawTree(g, root.getRight(), x + xOffset, y + 50, xOffset / 2);

            }

        }
    }
}
