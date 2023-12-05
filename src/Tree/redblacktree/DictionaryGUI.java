package Tree.redblacktree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DictionaryGUI extends JFrame {
    private RedBlackTree<String, WordInfo> dictionary;

    public DictionaryGUI() {
        // Tạo JFrame và cài đặt thuộc tính cơ bản
        super("English Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        // Tạo panel cho các thành phần của giao diện
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Tạo và thêm các thành phần vào panel
        JLabel label = new JLabel("Nhập từ cần tra:");
        JTextField textField = new JTextField(20);
        JButton searchButton = new JButton("Tra từ");
        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);

        panel.add(label);
        panel.add(textField);
        panel.add(searchButton);

        // Đọc dữ liệu từ file và xây dựng từ điển
        dictionary = new RedBlackTree<>();
        buildDictionary("src\\Tree\\redblacktree\\Data");
        // Xử lý sự kiện khi nhấn nút "Tra từ"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputWord = textField.getText().trim().toLowerCase();
                RedBlackNode<String, WordInfo> searchResult = dictionary.search(inputWord);
                if (searchResult!=null && searchResult.getData()!=null) {
                WordInfo wordInfo = dictionary.search(inputWord).getData();
//                if (wordInfo != null) {
                    resultArea.setText("Từ: \"" + inputWord + "\"\nLoại từ: " + wordInfo.getType() + "\nNghĩa: " + wordInfo.getDefinition());
                } else {
                    resultArea.setText("Không tìm thấy từ \"" + inputWord + "\" trong từ điển.");
                }
            }
        });

        // Thêm panel và kết quả vào JFrame
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }
    private void buildDictionary(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            dictionary = new RedBlackTree<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String word = parts[0].trim().toLowerCase();
                    String type = parts[1].trim();
                    String definition = parts[2].trim();
                    dictionary.insert(word, new WordInfo(type, definition));
                } else {
                    System.out.println("Invalid line: " + line);
                }
            }
            System.out.println("Dictionary built successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DictionaryGUI dictionaryGUI = new DictionaryGUI();
                dictionaryGUI.setVisible(true);
            }
        });
    }

    // Lớp để lưu trữ thông tin về từ
    private static class WordInfo {
        private String type;
        private String definition;

        public WordInfo(String type, String definition) {
            this.type = type;
            this.definition = definition;
        }

        public String getType() {
            return type;
        }

        public String getDefinition() {
            return definition;
        }
    }
}