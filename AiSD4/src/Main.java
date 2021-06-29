import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final double NANO_TO_SEC = 1000000000d;
    private static int mode = 0;
    private static String inputFile;

    public static void main(String[] args) throws IOException {
        runProgram(mode);
    }

    private static void runProgram(int test) throws IOException {
        Tree tree;
        switch (test) {
            case 1: {
                System.out.println("[Binary Search Tree]");
                tree = new BST();
                interactionPanel(tree);
                break;
            }
            case 2: {
                System.out.println("[Red Black Tree]");
                tree = new RBTree();
                interactionPanel(tree);
                break;
            }
            case 3: {
                System.out.println("[SPlay Tree]");
                tree = new SplayTree();
                interactionPanel(tree);
                break;
            }
            default: {
                System.out.println("[Trees] Please choose program mode: ");
                System.out.println("Available: (BST - 1), (RBT - 2),  (SPL - 3)");
                Scanner s = new Scanner(System.in);
                mode = s.nextInt();
                inputFile = "data/input.txt";
                runProgram(mode);
                break;
            }
        }
        // System.out.println("Process finished successfully.\nProgram restarts.\n");
        // runProgram(mode);
    }

    private static void interactionPanel(Tree tree) throws IOException {
        // Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader(inputFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        FileWriter fileWriter = new FileWriter(new File("data/output.txt"));
        int k = Integer.parseInt(bufferedReader.readLine());
        System.out.println(
                "Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr>>\n- test <i>, reset, restart");
        // System.out.println(k);
        // while (true) {
        while (k >= 1) {
            String line = bufferedReader.readLine();
            String[] userInput = line.split(" ");
            switch (userInput[0]) {
                case "insert": {
                    tree.insert(userInput[1]);
                    System.err.println("Comparisons " + tree.getResetComparisons() + "\n");
                    break;
                }
                case "delete": {
                    tree.delete(userInput[1]);
                    break;
                }
                case "search": {
                    tree.getResetModifications();
                    tree.getResetComparisons();
                    if (tree.search(userInput[1]) == true) {
                        fileWriter.write("1\n");
                        System.out.println("Comparisons, search " + tree.getResetComparisons());
                        System.out.println("modifications, search " + tree.getResetModifications());
                    } else {
                        fileWriter.write("0\n");
                    }
                    break;
                }
                case "load": {
                    if (userInput.length != 2) {
                        System.out.println(
                                "Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr>");
                        break;
                    }
                    // Check Loading Time
                    long start = System.nanoTime();
                    tree.load("data/" + userInput[1] + ".txt");
                    long end = System.nanoTime();

                    double duration = (end - start) / NANO_TO_SEC;
                    System.out.println(duration);
                    fileWriter.write(duration + "\n");
                    break;
                }
                case "max": {
                    if (tree.maximum() != null) {
                        fileWriter.write(tree.maximum().getKey() + "\n");
                    }
                    break;
                }
                case "inorder": {
                    tree.inOrder();
                    fileWriter.write(tree.inOrder() + "\n");
                    break;
                }
                case "test": {
                    if (userInput.length != 2) {
                        System.out.println(
                                "Available Commands:\n- insert <s>, delete <s>, search <s>, inorder\n- load <aspell, aspellReversed, aspellRandom, kjb, lotr>");
                        break;
                    }
                    runTests(tree, Integer.valueOf(userInput[1]));
                    break;
                }
                case "reset": {
                    runProgram(mode);
                    break;
                }
                case "restart": {
                    mode = 0;
                    runProgram(mode);
                    break;
                }
                case "successor": {
                    if (tree.successor(userInput[1]) != null) {
                        fileWriter.write(tree.successor(userInput[1]).getKey() + "\n");
                    }
                    break;
                }
                case "min": {
                    if (tree.minimum() != null) {
                        fileWriter.write(tree.minimum().getKey() + "\n");
                    }
                    break;
                }
                case "print": {
                    System.out.println(tree.print());
                    break;
                }
                default: {
                    // System.out.println("Available Commands:\n- insert <s>, delete <s>, search
                    // <s>, successor <s>, min, max inorder\n- load <sample, aspell_wordlist,
                    // aspellRandom, KJB, lotr, tadzio>");
                    break;

                }
            }
            k = k - 1;
        }
        fileWriter.close();
    }

    private static void runTests(Tree tree, int numOfTests) throws IOException {
        String treeType = tree.getClass().getSimpleName();
        String[] fileNames = { "aspell_wordlist", "lotr", "tadzio", "sample" };
        for (String fileName : fileNames) {

            File file = new File("tests/" + treeType + "_" + fileName + ".csv");

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append("insert_time;insert_cmp_count;insert_modified_nodes;"
                    + "search_time;search_cmp_count;search_modified_nodes;"
                    + "delete_time;delete_cmp_count;delete_modified_nodes\n");
            long start, end;
            for (int i = 0; i < numOfTests; i++) {
                start = System.nanoTime();
                tree.load("data/" + fileName + ".txt");
                end = System.nanoTime();
                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getResetComparisons())).append(";");
                fileWriter.append(String.valueOf(tree.getResetModifications())).append(";");
                start = System.nanoTime();
                tree.searchLoaded("data/" + fileName + ".txt");
                end = System.nanoTime();

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getResetComparisons())).append(";");
                fileWriter.append(String.valueOf(tree.getResetModifications())).append(";");
                start = System.nanoTime();
                tree.unload("data/" + fileName + ".txt");
                end = System.nanoTime();

                fileWriter.append(String.valueOf((end - start) / NANO_TO_SEC)).append(";");
                fileWriter.append(String.valueOf(tree.getResetComparisons())).append(";");
                fileWriter.append(String.valueOf(tree.getResetModifications())).append(";");

                fileWriter.append("\n");
                System.out.println("Test #" + i + ".");
            }
            fileWriter.flush();
            fileWriter.close();
        }
        System.out.println("All tests done.");
    }

}
