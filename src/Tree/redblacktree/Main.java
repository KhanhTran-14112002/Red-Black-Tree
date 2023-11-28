package Tree.redblacktree;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Tạo một cây đỏ-đen với kiểu Integer
        RedBlackTree<Integer, String> redBlackTree = new RedBlackTree<>();

        // Thêm các phần tử vào cây
        redBlackTree.insert(10, "Khanh");
        redBlackTree.insert(5, "Long");
        redBlackTree.insert(15, "Hieu");
        redBlackTree.insert(3, "Ninh");
        redBlackTree.insert(7, "GR12");
        redBlackTree.insert(12, "DSA");
        redBlackTree.insert(18, "REDBLACK");


        // In số lượng phần tử trong cây
        System.out.println("Size of the tree: " + redBlackTree.size());

        // In danh sách các phần tử nhỏ hơn 12
        System.out.println("Elements smaller than 12: " + redBlackTree.numSmaller(12));

        // In danh sách các phần tử lớn hơn 7
        System.out.println("Elements greater than 7: " + redBlackTree.numGreater(7));

        // In danh sách các phần tử lớn hơn 5 (sử dụng hàm getGreaterThan)
        List<Integer> greaterThan5 = redBlackTree.getGreaterThan(5, redBlackTree.size());
        System.out.println("Elements greater than 5: " + greaterThan5);


        System.out.println("Enter  element to search");
        Integer check = scanner.nextInt();
        RedBlackNode<Integer, String> find = redBlackTree.searchByKey(check);
        System.out.println(find);

        redBlackTree.printTree();

        System.out.println("root is " + redBlackTree.getRoot());

    }
}
