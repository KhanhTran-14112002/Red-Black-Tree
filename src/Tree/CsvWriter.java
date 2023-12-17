package Tree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CsvWriter {
    public static void main(String[] args) {
//        String fileName = "H:\\Workspace\\Java\\DataStructureAndAlgorithms\\src\\BST\\output.csv";
        String fileName = "src\\Tree\\output.csv";
        int numberOfLines = 12000; // specify the number of lines in the CSV file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 1; i <= numberOfLines; i++) {
                String randomString = generateRandomName(); // specify the length of the random string
                String line = i + "," + randomString;
                writer.write(line);
                writer.newLine();
            }

            System.out.println("CSV file created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private static String generateRandomName(){
        String[] ho = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Phan",
                "Huỳnh", "Vũ", "Võ", "Đặng", "Bùi",
                "Đỗ", "Hồ", "Ngô", "Dương", "Lý", "Lương" };


        String[] tendem = {"Ái Việt", "Ân Việt", "Anh Việt", "Bảo Việt", "Đạo Việt", "Đạt Việt",
                "Đông Việt", "Đức Việt", "Dũng Việt", "Ngọc", "Bùi", "Tràng",
                "Hiền Việt", "Mạnh Việt", "Mỹ Việt", "Mai Việt", "Lý",
                "Phan Việt", "Đức Việt", "Hòa", "Văn", "Thị", "Cao", "Tuệ" };

        String[] ten = {"Quyền", "Anh", "Tuấn", "Toản", "Lợi", "Khánh",
                "Hiếu", "Ninh", "Minh", "Ngọc", "Nga",
                "Dương", "Huyền", "Tâm", "Mai", "Việt",
                "Cao", "Sỉu", "Hòa", "Văn", "An" };
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
// Chọn ngẫu nhiên từ mỗi mảng và thêm vào StringBuilder
        sb.append(ho[random.nextInt(ho.length)]).append(" ");
        sb.append(tendem[random.nextInt(tendem.length)]).append(" ");
        sb.append(ten[random.nextInt(ten.length)]);

        // Chuyển StringBuilder thành String và trả về
        return sb.toString();

    }
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }


}
