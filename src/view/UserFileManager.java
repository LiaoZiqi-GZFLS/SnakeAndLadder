package view;

import java.io.*;

public class UserFileManager {

    // 写入用户名到文件
    public static void writeUsernameToFile(String username, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(username);
            System.out.println("用户名已成功写入文件: " + username);
        } catch (IOException e) {
            System.err.println("写入用户名时发生错误: " + e.getMessage());
        }
    }

    // 从文件中读取用户名
    public static String readUsernameFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String username = reader.readLine();
            if (username != null) {
                System.out.println("从文件中读取的用户名: " + username);
                return username;
            } else {
                System.out.println("文件为空或无法读取用户名");
                return null;
            }
        } catch (IOException e) {
            System.err.println("读取用户名时发生错误: " + e.getMessage());
            return null;
        }
    }

    // 从文件中读取用户名
    public static String readUsernameFromFile(String filePath, boolean quiet) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String username = reader.readLine();
            if (username != null) {
                if(!quiet){
                    System.out.println("从文件中读取的用户名: " + username);
                }
                return username;
            } else {
                if(!quiet){
                    System.out.println("文件为空或无法读取用户名");
                }
                return null;
            }
        } catch (IOException e) {
            if(!quiet){
                System.err.println("读取用户名时发生错误: " + e.getMessage());
            }
            return null;
        }
    }

    // 更新用户名（如果文件不存在会自动创建）
    public static void updateUsername(String newUsername, String filePath) {
        writeUsernameToFile(newUsername, filePath);
    }

    public static void main(String[] args) {
        String filePath = "username.txt";

        // 示例：写入用户名
        writeUsernameToFile("john_doe", filePath);

        // 示例：读取用户名
        readUsernameFromFile(filePath);

        // 示例：更新用户名
        updateUsername("jane_smith", filePath);
        readUsernameFromFile(filePath);
    }
}
