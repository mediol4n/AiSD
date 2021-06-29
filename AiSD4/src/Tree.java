import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class Tree {
    int comparisons = 0;
    int modifications = 0;
    public String inOrder = "";

    public abstract void insert(String value);

    public abstract void delete(String value);

    public abstract boolean search(String value);

    public abstract String inOrder();

    public abstract Node successor(String value);

    public abstract Node minimum(Node node);

    public abstract Node minimum();

    public abstract Node maximum();

    public abstract String print();

    public void increaseMod(int modifications) {
        this.modifications += modifications;
    }

    public void increaseComp(int comparisons) {
        this.comparisons += comparisons;
    }

    public int getResetComparisons() {
        int result = comparisons;
        this.comparisons = 0;
        return result;
    }

    public int getResetModifications() {
        int result = modifications;
        this.modifications = 0;
        return result;
    }

    public void load(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    String toInsert = fix(s);
                    if (toInsert != null)
                        insert(toInsert);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unload(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    String toDelete = fix(s);
                    if (toDelete != null)
                        delete(toDelete);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchLoaded(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for (String s : split) {
                    String toSearch = fix(s);
                    if (toSearch != null)
                        search(toSearch);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long searchLoaded2(String fileName) {
        int howMany = 0;
        int counter = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                for  (String s : split) {
                    counter++;
                    if (counter >= 25) {
                        String toSearch = fix(s);
                        if (toSearch != null) {
                            search(toSearch);
                            counter = 0;
                            howMany += 1;
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ebe) {
            ebe.printStackTrace();
        }
        return howMany;
    }

    private boolean isInvalidChar(char c) {
        return (c < 65 || c > 90) && (c < 97 || c > 122);
    }

    public String fix(String element) {
        if (element.equals(""))
            return element;
        if (isInvalidChar(element.charAt(0))) {
            return fix(element.substring(1));
        }
        if (isInvalidChar(element.charAt(element.length() - 1))) {
            return fix(element.substring(0, (element.length() - 1)));
        }
        return element;
    }

}
