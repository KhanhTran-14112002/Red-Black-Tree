package Tree.redblacktree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class EnglishDictionary2 {
    RedBlackTree<String,String> dictionary = new RedBlackTree<>();

    public void buildDictionary(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String definition = parts[1].trim();
                    dictionary.insert(word, definition);
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }
            System.out.println("Dictionary built successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RedBlackNode<String,String> lookupWord(String word) {
        return dictionary.search(word);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Đọc đường dẫn file từ người dùng
        String filePath = "src\\Tree\\redblacktree\\Data";

        EnglishDictionary2 englishDictionary = new EnglishDictionary2();
        englishDictionary.buildDictionary(filePath);

        // Tra từ
        while (true) {
            System.out.print("Nhập từ cần tra (hoặc 'exit' để thoát): ");
            String inputWord = scanner.nextLine();

            if (inputWord.equalsIgnoreCase("exit")) {
                break;
            }

            RedBlackNode<String,String> definition = englishDictionary.lookupWord(inputWord);
            if (definition != null) {
                System.out.println("Nghĩa của \"" + inputWord + "\": " + definition);
            } else {
                System.out.println("Không tìm thấy từ \"" + inputWord + "\" trong từ điển.");
            }
        }

        scanner.close();
    }
}

