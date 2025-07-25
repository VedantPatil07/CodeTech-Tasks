import java.io.*;
import java.util.*;

public class FileOperations {

    public static void main(String[] args) {

        String fileName = "sample.txt"; // File name

        // Step 1: Write content to the file
        writeToFile(fileName, "This is the first line.\nThis is the second line.\n");

        // Step 2: Read and display the content of the file
        System.out.println("Original File Content:");
        readFromFile(fileName);

        // Step 3: Modify file content (replace a word and save changes)
        modifyFile(fileName, "second", "modified");

        // Step 4: Read modified content
        System.out.println("\nModified File Content:");
        readFromFile(fileName);
    }

    // Method to write to a file
    public static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("File written successfully.\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to read from a file
    public static void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Method to modify the file (simple word replacement)
    public static void modifyFile(String fileName, String oldWord, String newWord) {
        StringBuilder newContent = new StringBuilder();

        // Read original file content and replace target word
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                newContent.append(line.replace(oldWord, newWord)).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error modifying file: " + e.getMessage());
            return;
        }

        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(newContent.toString());
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.out.println("Error writing modified content: " + e.getMessage());
        }
    }
}
