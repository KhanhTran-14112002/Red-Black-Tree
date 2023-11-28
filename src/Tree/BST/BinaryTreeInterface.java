package Tree.BST;

public interface BinaryTreeInterface<T extends Comparable<T>, V> {

    // Chèn một cặp khóa-giá trị vào cây
    void insert(T key, V value);

    // Tìm giá trị tương ứng với một khóa trong cây
    V search(T key);

    // Xoá một nút có khóa cho trước khỏi cây
    void delete(T key);

    // Kiểm tra xem cây có trống không
    boolean isEmpty();

    // Trả về số lượng nút trong cây
    int size();

    // Thực hiện duyệt cây theo thứ tự trước (pre-order)
    void preOrderTraversal();

    // Thực hiện duyệt cây theo thứ tự giữa (in-order)
    void inOrderTraversal();

    // Thực hiện duyệt cây theo thứ tự sau (post-order)
    void postOrderTraversal();
}

