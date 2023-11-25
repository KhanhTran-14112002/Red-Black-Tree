package redblacktree;

import java.util.List;

interface RedBlackTreeInterface<T extends Comparable<T>, V> {
    /**
     * Chèn một phần tử mới vào cây.
     *
     * @param key  Giá trị khóa của phần tử cần chèn.
     * @param data Giá trị dữ liệu của phần tử cần chèn.
     */
    void insert(T key, V data);

    /**
     * Xóa một phần tử khỏi cây.
     *
     * @param key Giá trị khóa của phần tử cần xóa.
     */
    void remove(T key);

    /**
     * Trả về số lượng phần tử lớn hơn giá trị khóa cho trước.
     *
     * @param key Giá trị khóa để so sánh.
     * @return Số lượng phần tử lớn hơn key trong cây.
     */
    int numGreater(T key);

    /**
     * Trả về số lượng phần tử nhỏ hơn giá trị khóa cho trước.
     *
     * @param key Giá trị khóa để so sánh.
     * @return Số lượng phần tử nhỏ hơn key trong cây.
     */
    int numSmaller(T key);

    /**
     * Tìm kiếm một phần tử trong cây dựa trên giá trị khóa.
     *
     * @param key Giá trị khóa cần tìm kiếm.
     * @return Nút chứa phần tử có giá trị khóa là key, hoặc null nếu không tìm thấy.
     */
    RedBlackNode<T, V> search(T key);

    /**
     * Trả về danh sách các phần tử có giá trị khóa lớn hơn giá trị cho trước.
     *
     * @param key         Giá trị khóa để so sánh.
     * @param maxReturned Số lượng tối đa các kết quả cần trả về.
     * @return Danh sách các phần tử có giá trị khóa lớn hơn key, không vượt quá maxReturned.
     */
    List<T> getGreaterThan(T key, Integer maxReturned);

    /**
     * Trả về kích thước (số lượng phần tử) của cây.
     *
     * @return Kích thước của cây.
     */
    int size();
}
