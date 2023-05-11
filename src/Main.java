import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        BTree bTree = new BTree();
        ArrayList<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        int searchSize = 100;
        int deleteSize = 1000;
        long totalInsertTime = 0;
        long totalInsertOperations = 0;
        long totalSearchTime = 0;
        long totalSearchOperations = 0;
        long totalDeleteTime = 0;
        long totalDeleteOperations = 0;

        try (Scanner scanner = new Scanner(new File("C:\\Users\\timat\\IdeaProjects\\B-Tree\\data\\random_numbers.txt"))) {
            while (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("insert_data.txt"))) {
            for (int number : numbers) {
                long operationCountBefore = bTree.getOperationCount();
                long insertStart = System.nanoTime();
                bTree.add(number);
                long insertEnd = System.nanoTime();
                long operationCountAfter = bTree.getOperationCount();

                long insertTime = insertEnd - insertStart;
                long operations = operationCountAfter - operationCountBefore;

                totalInsertTime += insertTime;
                totalInsertOperations += operations;

                out.println(insertTime + " " + operations);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("search_data.txt"))) {
            for (int i = 0; i < searchSize; i++) {
                int randomIndex = rand.nextInt(numbers.size());
                long operationCountBefore = bTree.getOperationCount();
                long searchStart = System.nanoTime();
                bTree.contains(numbers.get(randomIndex));
                long searchEnd = System.nanoTime();
                long operationCountAfter = bTree.getOperationCount();

                long searchTime = searchEnd - searchStart;
                long operations = operationCountAfter - operationCountBefore;

                totalSearchTime += searchTime;
                totalSearchOperations += operations;

                out.println(searchTime + " " + operations);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("delete_data.txt"))) {
            for (int i = 0; i < deleteSize; i++) {
                int randomIndex = rand.nextInt(numbers.size());
                long operationCountBefore = bTree.getOperationCount();
                long deleteStart = System.nanoTime();
                bTree.remove(numbers.get(randomIndex));
                long deleteEnd = System.nanoTime();
                long operationCountAfter = bTree.getOperationCount();

                long deleteTime = deleteEnd - deleteStart;
                long operations = operationCountAfter - operationCountBefore;

                totalDeleteTime += deleteTime;
                totalDeleteOperations += operations;

                out.println(deleteTime + " " + operations);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file.");
            e.printStackTrace();
        }

        double averageInsertTime = totalInsertTime / (double) numbers.size();
        double averageInsertOperations = totalInsertOperations / (double) numbers.size();

        double averageSearchTime = totalSearchTime / (double) searchSize;
        double averageSearchOperations = totalSearchOperations / (double) searchSize;

        double averageDeleteTime = totalDeleteTime / (double) deleteSize;
        double averageDeleteOperations = totalDeleteOperations / (double) deleteSize;

        System.out.println("Average insert time: " + averageInsertTime + " ns");
        System.out.println("Average insert operations: " + averageInsertOperations);
        System.out.println("Average search time: " + averageSearchTime + " ns");
        System.out.println("Average search operations: " + averageSearchOperations);
        System.out.println("Average delete time: " + averageDeleteTime + " ns");
        System.out.println("Average delete operations: " + averageDeleteOperations);
        BTreePerformanceAnalyzer analyzer = new BTreePerformanceAnalyzer();
        analyzer.analyzePerformance();
    }
}

