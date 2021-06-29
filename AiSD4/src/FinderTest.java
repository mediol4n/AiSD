import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FinderTest {

    private static int numOfTests = 150;
    private static final double NANO_TO_SEC = 1000000000d;

    public static void main(String[] args) {
        /*
         * try { testFinder(); } catch (IOException e) { e.printStackTrace(); }
         */
        searchLoadedRand(new BST());
        searchLoadedRand(new RBTree());
        searchLoadedRand(new SplayTree());
    }

    public static void testFinder() throws IOException {
        String[] files = { "aspellReversed" };
        for (String fileName : files) {
            File file = new File("find_tests/" + fileName + ".csv");
            FileWriter writer = new FileWriter(file);
            writer.write("BST_search_time;BST_search_cmp_count;BST_search_modified_nodes;"
                    + "RBT_search_time;RBT_search_cmp_count;RBT_search_modified_nodes;"
                    + "Splay_search_time;Splay_search_cmp_count;Splay_search_modified_nodes;" + "howMany;\n");
            long start, end;
            for (int i = 0; i < numOfTests; i++) {
                System.out.println("test : " + i);
                Tree tree1 = new BST();
                tree1.load("data/" + fileName + ".txt");
                tree1.getResetComparisons();
                tree1.getResetModifications();

                start = System.nanoTime();
                tree1.searchLoaded2("data/" + fileName + ".txt");
                end = System.nanoTime();

                writer.write(String.valueOf((end - start) / NANO_TO_SEC) + ";");
                writer.write(String.valueOf(tree1.getResetComparisons()) + ";");
                writer.write(String.valueOf(tree1.getResetModifications()) + ";");

                tree1 = new RBTree();
                tree1.load("data/" + fileName + ".txt");
                tree1.getResetComparisons();
                tree1.getResetModifications();

                start = System.nanoTime();
                tree1.searchLoaded2("data/" + fileName + ".txt");
                end = System.nanoTime();

                writer.write(String.valueOf((end - start) / NANO_TO_SEC) + ";");
                writer.write(String.valueOf(tree1.getResetComparisons()) + ";");
                writer.write(String.valueOf(tree1.getResetModifications()) + ";");

                tree1 = new SplayTree();
                tree1.load("data/" + fileName + ".txt");
                tree1.getResetComparisons();
                tree1.getResetModifications();

                start = System.nanoTime();
                long ile = tree1.searchLoaded2("data/" + fileName + ".txt");
                end = System.nanoTime();

                writer.write(String.valueOf((end - start) / NANO_TO_SEC) + ";");
                writer.write(String.valueOf(tree1.getResetComparisons()) + ";");
                writer.write(String.valueOf(tree1.getResetModifications()) + ";");
                writer.write(ile + ";\n");
            }
        }
    }

    /**
     * w WC wyszło mi liniowo
     */
    public static void testSplay() {
        SplayTree tree = new SplayTree();
        int i = 0;
        while (i < 1000) {
            for (int j = 0; j <= i; j++) {
                tree.insert(String.valueOf(j));
            }
            tree.getResetComparisons();

            tree.search("0");
            System.out.println(tree.getResetComparisons() + ";");
            tree = new SplayTree();
            i++;
        }

    }

    public static void searchLoaded(Tree tree) {
        String fileName = "data/aspellRandom.txt";
        String treeType = tree.getClass().getSimpleName();
        int wordCounter = 0;
        int bound = 1;
        try {
            File file = new File("ex2/" + treeType + "_find.csv");
            FileWriter writer = new FileWriter(file);
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            writer.write("n;find_time;find_cmp;\n");
            while (bound <= 100) {
                while ((line = bufferedReader.readLine()) != null && wordCounter < 10 * bound) {
                    String[] split = line.split(" ");
                    for (String s : split) {
                        String toInsert = tree.fix(s);
                        if (toInsert != null) {
                            wordCounter++;
                            tree.insert(toInsert);
                        }
                    }
                }
                tree.getResetComparisons();
                bufferedReader = new BufferedReader(fileReader);
                long start = System.nanoTime();
                // szukam konkretnego słowa, nie wiem jak to losować
                tree.search("rove");
                long end = System.nanoTime();
                System.out.println(wordCounter);
                writer.write(wordCounter + ";");
                writer.write(String.valueOf((end - start) / NANO_TO_SEC) + ";");
                writer.write(String.valueOf(tree.getResetComparisons()) + ";\n");
                wordCounter = 0;
                bound++;
                System.out.println(bound);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ebe) {
            ebe.printStackTrace();
        }
        return;

    }

    public static void searchLoadedLast(Tree tree) {
        String fileName = "data/aspellRandom.txt";
        String treeType = tree.getClass().getSimpleName();
        int wordCounter = 0;
        int bound = 1;
        try {
            File file = new File("ex2/" + treeType + "_find_last.csv");
            FileWriter writer = new FileWriter(file);
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line, toInsert;
            toInsert = "";
            writer.write("n;find_time;find_cmp;\n");
            while (bound <= 100) {
                while ((line = bufferedReader.readLine()) != null && wordCounter < 10 * bound) {
                    String[] split = line.split(" ");
                    for (String s : split) {
                        toInsert = tree.fix(s);
                        if (toInsert != null) {
                            wordCounter++;
                            tree.insert(toInsert);
                        }
                    }
                }
                tree.getResetComparisons();
                bufferedReader = new BufferedReader(fileReader);
                long start = System.nanoTime();
                // szukam konkretnego słowa, nie wiem jak to losować
                tree.search(toInsert);
                long end = System.nanoTime();
                System.out.println(wordCounter);
                writer.write(wordCounter + ";");
                writer.write(String.valueOf((end - start) / NANO_TO_SEC) + ";");
                writer.write(String.valueOf(tree.getResetComparisons()) + ";\n");
                wordCounter = 0;
                bound++;
                System.out.println(bound);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ebe) {
            ebe.printStackTrace();
        }
        return;

    }

    public static void searchLoadedRand(Tree tree) {
        String fileName = "data/aspellRandom.txt";
        String treeType = tree.getClass().getSimpleName();
        int wordCounter = 0;
        int bound = 1;
        try {
            File file = new File("ex2/" + treeType + "_find_rand.csv");
            FileWriter writer = new FileWriter(file);
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line, toInsert;
            String randomWord = "";
            Random random = new Random();
            Boolean isChosen = false;
            toInsert = "";
            writer.write("n;find_time;find_cmp;\n");
            while (bound <= 100) {
                while ((line = bufferedReader.readLine()) != null && wordCounter < 10 * bound) {
                    String[] split = line.split(" ");
                    for (String s : split) {
                        toInsert = tree.fix(s);
                        if (toInsert != null) {
                            wordCounter++;
                            tree.insert(toInsert);
                            if (random.nextDouble() < 0.5 && !isChosen) {
                                randomWord = toInsert;
                                isChosen = !isChosen;
                            }
                        }
                    }
                }
                tree.getResetComparisons();
                bufferedReader = new BufferedReader(fileReader);
                long start = System.nanoTime();
                if (randomWord != "") {
                    tree.search(randomWord);
                } else {
                    tree.search(toInsert);
                }
                long end = System.nanoTime();
                System.out.println(wordCounter);
                writer.write(wordCounter + ";");
                writer.write(String.valueOf((end - start) / NANO_TO_SEC) + ";");
                writer.write(String.valueOf(tree.getResetComparisons()) + ";\n");
                wordCounter = 0;
                bound++;
                System.out.println(bound);
            }
            writer.close();
        } catch (

        FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ebe) {
            ebe.printStackTrace();
        }
        return;

    }

}
