package Tree;

import Tree.BST.BinarySearchTree;
import Tree.BST.BinaryTreeInterface;
import Tree.redblacktree.RedBlackTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompareTime {
    private static final String COMMA_DELIMITER = ",";
    private static final BinaryTreeInterface<Integer, String> binarySearchTree = new BinarySearchTree<>();
    private static final RedBlackTree<Integer, String> redBlackTree = new RedBlackTree<>();

    public static void main(String[] args) {
        init();
        // Thời gian tìm kiếm trong BST
        System.out.println("============================================================");
        int keyToSearchBST = 12000;
        long startTimeBST = System.nanoTime();
        binarySearchTree.search(keyToSearchBST);
        long endTimeBST = System.nanoTime();
        long elapsedTimeBST = endTimeBST - startTimeBST;
        System.out.println("data in "+ keyToSearchBST+" is: "+binarySearchTree.search(keyToSearchBST));
        System.out.println("Time search in binarySearchTree: " + elapsedTimeBST + " nanoseconds");
        System.out.println("============================================================");
        // Thời gian tìm kiếm trong RBT
        int keyToSearchRBT = 12000;
        long startTimeRBT = System.nanoTime();
        redBlackTree.search(keyToSearchRBT);
        long endTimeRBT = System.nanoTime();
        long elapsedTimeRBT = endTimeRBT - startTimeRBT;
        System.out.println("data in "+ keyToSearchBST+" is: "+redBlackTree.search(keyToSearchRBT));
        System.out.println("Time search in redBlackTree: " + elapsedTimeRBT + " nanoseconds");
    }


    public static void init() {
        String filePath = "H:\\Workspace\\Java\\DataStructureAndAlgorithms\\src\\BST\\output.csv";
        readListData(filePath);
    }

    public static void readListData(String filePath) {
        BufferedReader dataReader = null;
        try {
            String line;
            dataReader = new BufferedReader(new FileReader(filePath));
            // Read file line by line?
//            long startTime = System.nanoTime();
//            while ((line = dataReader.readLine()) != null) {
//                List<String> dataList = parseDataLineToList(line);
//                if (dataList.size() != 2) {
//                    continue;
//                }
//                binarySearchTree.insert(Integer.parseInt(dataList.get(0)), dataList.get(1));
//
//            }

            // Đọc dữ liệu vào danh sách
            List<String> dataList = new ArrayList<>();
            while ((line = dataReader.readLine()) != null) {
                List<String> dtList = parseDataLineToList(line);
                if (dtList.size() == 2) {
                    dataList.add(line);
                }
            }

        // Chèn vào cây BST
            long startTimeBST = System.nanoTime();
            for (String data : dataList) {
                List<String> dtList = parseDataLineToList(data);
                binarySearchTree.insert(Integer.parseInt(dtList.get(0)), dtList.get(1));
            }
            long endTimeBST = System.nanoTime();
            long elapsedTimeBST = endTimeBST - startTimeBST;
            System.out.println("Time insert binarySearchTree: " + elapsedTimeBST + " nanoseconds");
        // Chèn vào cây RBT
            long startTimeRBT = System.nanoTime();
            for (String data : dataList) {
                List<String> dtList = parseDataLineToList(data);
                redBlackTree.insert(Integer.parseInt(dtList.get(0)), dtList.get(1));
            }
            long endTimeRBT = System.nanoTime();
            long elapsedTimeRBT = endTimeRBT - startTimeRBT;
            System.out.println("Time insert redBlackTree: " + elapsedTimeRBT + " nanoseconds");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataReader != null)
                    dataReader.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }

    public static List<String> parseDataLineToList(String dataLine) {
        List<String> result = new ArrayList<>();
        if (dataLine != null) {
            String[] splitData = dataLine.split(COMMA_DELIMITER);
            for (int i = 0; i < splitData.length; i++) {
                result.add(splitData[i]);
            }
        }
        return result;
    }
}
