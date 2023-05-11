import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BTreePerformanceAnalyzer {
    private BTree bTree;

    public BTreePerformanceAnalyzer() {
        bTree = new BTree();
    }

    public void analyzePerformance() {
        long insertTime = measureInsertTime();
        long deleteTime = measureDeleteTime();
        long searchTime = measureSearchTime();

        long insertOperations = bTree.getOperationCount();
        long deleteOperations = bTree.getOperationCount();
        long searchOperations = bTree.getOperationCount();

        System.out.println("Average Insert Time: " + insertTime + " milliseconds");
        System.out.println("Average Delete Time: " + deleteTime + " milliseconds");
        System.out.println("Average Search Time: " + searchTime + " milliseconds");
        System.out.println("Average Insert Operations: " + insertOperations);
        System.out.println("Average Delete Operations: " + deleteOperations);
        System.out.println("Average Search Operations: " + searchOperations);
    }

    private long measureInsertTime() {
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\timat\\IdeaProjects\\B-Tree\\insert_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                int key = Integer.parseInt(data[0]);
                bTree.add(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private long measureDeleteTime() {
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\timat\\IdeaProjects\\B-Tree\\delete_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                int key = Integer.parseInt(data[0]);
                bTree.remove(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private long measureSearchTime() {
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\timat\\IdeaProjects\\B-Tree\\search_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                int key = Integer.parseInt(data[0]);
                bTree.contains(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}