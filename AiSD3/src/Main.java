import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

    private static boolean statFlag = false;
    private static int[] randTab;
    private static int[] copy;
    private static String fileName;
    private static boolean p = false, r = false;
    private static int n1, k, m;


    public static void main(String[] args) {
        try {
            int l = 0;
            while (l < args.length) {
                if (args[l].equals("-p") && !r) {
                    p = true;
                    n1  = Integer.parseInt(args[l+1]);
                    k = Integer.parseInt(args[l+2]);
                }
                if (args[l].equals("-r") && !p) {
                    r = true;
                    n1  = Integer.parseInt(args[l+1]);
                    k = Integer.parseInt(args[l+2]);
                }
                if (args[l].equals("--stat")) {
                    statFlag = true;
                    fileName = args[l + 1];
                    m = Integer.parseInt(args[l+2]);
                    break;
                } 
                l++;
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (statFlag && fileName.length() > 0) {
            Random random = new Random();
            String[] algs = {"random-17", "random-n/2", "random-bigK",  "select-17", "select-n/2", "select-bigK"};

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write("n");
                for (int i = 0; i < algs.length; i++) {
                    writer.write(";" + algs[i] + " - minComparisons;"+ algs[i] + " - maxComparisons;"+ algs[i] + " - averageCnomparisons;"+ algs[i] + " - standCnomparisons;" + algs[i] + " - minSets;"
                     + algs[i] + " - maxSets;" + algs[i] + " - avgSets;" + algs[i] + " - stdSets;");
                }
                writer.write("\n");

                for (int n = 100; n <= 10000; n += 100) {
                    randTab = new int[n];
                    copy = new int[n];
                    Stats[] min_Stats = new Stats[6];
                    Stats[] max_Stats = new Stats[6];

                    Stats[] avg_stats = new Stats[6];
                    for (int i = 0; i < 6; i++) {
                        avg_stats[i] = new Stats();
                        min_Stats[i] = new Stats();
                        max_Stats[i] = new Stats();
                    }
                    Stats[] allStats = new Stats[6*m];
                    for (int i = 0; i < 6*m; i++) {
                        allStats[i] = new Stats();
                    }

                    for (int i = 0; i < m; i++) {
                        //for (int j = 0; j < n; j++) {
                        //    randTab[j] = random.nextInt(); 
                        //}
                        randTab = getPermutation(n);
                        System.out.println(randTab.length);
                        Stats[] stats = new Stats[6];
                        for (int j = 0; j < 6; j++) {
                            stats[j] = new Stats();
                        }

                        int lowKey = 17;
                        int midKey = n/2;
                        int highKey = random.nextInt(n/4) + 3 * n/4;
                        System.out.println("LOWK - " + lowKey + "  MIDK - " + midKey + " HiGHKEY - " + highKey);

                        copy = randTab.clone();
                        System.out.println(copy.length);
                        SelectingAlgorithms.randomizedSelect(copy, 0, n-1, lowKey, stats[0]); 
                        allStats[6*i] = stats[0]; 


                        copy = randTab.clone();
                        SelectingAlgorithms.randomizedSelect(copy, 0, n-1, midKey, stats[1]); 
                        allStats[6*i + 1] = stats[1];

                        copy = randTab.clone();
                        SelectingAlgorithms.randomizedSelect(copy, 0, n-1, highKey, stats[2]); 
                        allStats[6*i+2] = stats[2];

                        copy = randTab.clone();
                        SelectingAlgorithms.select(copy, 0, n-1, lowKey, stats[3]);
                        allStats[6*i+3] = stats[3];

                        copy = randTab.clone();
                        SelectingAlgorithms.select(copy, 0, n-1, midKey, stats[4]);
                        allStats[6*i+4] = stats[4];

                        copy = randTab.clone();
                        SelectingAlgorithms.select(copy, 0, n-1, highKey, stats[5]);
                        allStats[6*i+5] = stats[5];

                        for (int l = 0; l < 6; l++) {
                            avg_stats[l].comparisons += stats[l].comparisons / m;
                            avg_stats[l].swaps += stats[l].swaps / m;
                        }
                    }
                    writer.write("" + n);
                    for(int i = 0; i < 6; i++) {
                        int minC = findMinComp(allStats, i);
                        int maxC = findMaxComp(allStats, i);
                        int avgC = avg_stats[i].comparisons;
                        int stdC = findStdComp(allStats, i, avgC);
                        int minS = findMinSwap(allStats, i);
                        int maxS = findMaxSwap(allStats, i);
                        int avgS = avg_stats[i].swaps;
                        int stdS = findStdSwap(allStats, i, avgS);
                        writer.write(";" + minC + ";" + maxC + ";" + avgC + ";" + stdC + ";" + minS + ";" + maxS + ";" + avgS + ";" + stdS);
                    }
                    writer.write("\n");
                    System.out.println(n);   
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (p == true && k <= n1 && k >= 1) {

                int permutation[] = getPermutation(n1);
                int copy[] = new int[n1];

                System.out.print("TABLICA: [");
                for (int i = 0; i < n1; i++) {
                    System.out.print(permutation[i] + ", ");
                }
                System.out.println("] ");
                System.out.println(" ");
                System.out.println(" ");
                Stats stats1 = new Stats();
                copy = permutation.clone();
                System.out.println("-----RANDOM SELECT-----");
                int number = SelectingAlgorithms.randomizedSelect(copy, 0, n1-1, k, stats1);
                System.out.print("[");
                for (int i = 0; i < n1-1; i++) {
                    if (copy[i] == number) {
                        System.out.print("[" + copy[i] + "], ");
                    } else {
                        System.out.print(copy[i] + ", ");
                    }
                }
                System.out.println(copy[n1-1] + "] ");
                System.out.println(" ");
                System.out.println("-----RESULT-----");
                System.out.println("Comparisons ➔ " + stats1.comparisons);
                System.out.println("Swaps ➔ " + stats1.swaps);
                System.out.println(" ");
                System.out.println(" ");
                Stats stats2 = new Stats();
                copy = permutation.clone();
                System.out.println("-----SELECT-----");
                number = SelectingAlgorithms.select(copy, 0, n1-1, k, stats2);
                System.out.print("[");
                for (int i = 0; i < n1-1; i++) {
                    if (copy[i] == number) {
                        System.out.print("[" + copy[i] + "], ");
                    } else {
                        System.out.print(copy[i] + ", ");
                    }
                }
                System.out.println(copy[n1-1] + "] ");
                System.out.println(" ");
                System.out.println("-----RESULT-----");
                System.out.println("Comparisons ➔ " + stats2.comparisons);
                System.out.println("Swaps ➔ " + stats2.swaps);
            } else if (r == true && k <= n1 && k >= 1) {
                int random[] = getRandomArray(n1);
                int copy[] = new int[n1];

                System.out.print("TABLICA: [");
                for (int i = 0; i < n1; i++) {
                    System.out.print(random[i] + ", ");
                }
                System.out.println("] ");
                System.out.println(" ");
                System.out.println(" ");
                Stats stats1 = new Stats();
                copy = random.clone();
                System.out.println("-----RANDOM SELECT-----");
                int number = SelectingAlgorithms.randomizedSelect(copy, 0, n1-1, k, stats1);
                System.out.print("[");
                for (int i = 0; i < n1-1; i++) {
                    if (copy[i] == number) {
                        System.out.print("[" + copy[i] + "], ");
                    } else {
                        System.out.print(copy[i] + ", ");
                    }
                }
                System.out.println(copy[n1-1] + "] ");
                System.out.println(" ");
                System.out.println("-----RESULT-----");
                System.out.println("Comparisons ➔ " + stats1.comparisons);
                System.out.println("Swaps ➔ " + stats1.swaps);
                System.out.println(" ");
                System.out.println(" ");
                Stats stats2 = new Stats();
                copy = random.clone();
                System.out.println("-----SELECT-----");
                number = SelectingAlgorithms.select(copy, 0, n1-1, k, stats2);
                System.out.print("[");
                for (int i = 0; i < n1-1; i++) {
                    if (copy[i] == number) {
                        System.out.print("[" + copy[i] + "], ");
                    } else {
                        System.out.print(copy[i] + ", ");
                    }
                }
                System.out.println(copy[n1-1] + "] ");
                System.out.println(" ");
                System.out.println("-----RESULT-----");
                System.out.println("Comparisons ➔ " + stats2.comparisons);
                System.out.println("Swaps ➔ " + stats2.swaps);
            }
        }
    }


    public static int findStdSwap(Stats[] allStats, int remainder, int avg) {
        int sum = 0;
        for (int i = 0; i < m; i++) {
            sum += (avg-allStats[6*i+remainder].swaps)*(avg-allStats[6*i+remainder].swaps);
        }
        return (int) Math.sqrt(sum/m);
    }


    public static int findMaxSwap(Stats[] allStats, int remainder) {
        int max = allStats[remainder].swaps;
        for (int i = 0; i < m; i++) {
            if (max <= allStats[6*i+remainder].swaps) {
                max = allStats[6*i+remainder].swaps;
            }
        }
        return max;  
    }


    public static int findMinSwap(Stats[] allStats, int remainder) {
        int min = allStats[remainder].swaps;
        for (int i = 0; i < m; i++) {
            if (min >= allStats[6*i+remainder].swaps) {
                min = allStats[6*i+remainder].swaps;
            }
        }
        return min;
    }


    public static int findStdComp(Stats[] allStats, int remainder, int avg) {
        int sum = 0;
        for (int i = 0; i < m; i++) {
            sum += (avg-allStats[6*i+remainder].comparisons)*(avg-allStats[6*i+remainder].comparisons);
        }
        return (int) Math.sqrt(sum/m);
    }


    public static int findMaxComp(Stats[] allStats, int remainder) {
        int max = allStats[remainder].comparisons;
        for (int i = 0; i < m; i++) {
            if (max <= allStats[6*i+remainder].comparisons) {
                max = allStats[6*i+remainder].comparisons;
            }
        }
        return max;
    }


    public static int findMinComp(Stats[] allStats, int remainder) {
        int min = allStats[remainder].comparisons;
        for (int i = 0; i < m; i++) {
            if (min >= allStats[6*i+remainder].comparisons) {
                min = allStats[6*i+remainder].comparisons;
            }
        }
        return min;
    }


    private static int[] getRandomArray(int n2) {
        int a[] = new int[n2];
        Random rand = new Random(System.nanoTime());

        for (int i = 0; i < n2; i++) {
            a[i] = rand.nextInt(2137);
        }

        return a;
    }


    private static int[] getPermutation(int n2) {
        int a[] = new int[n2];
        for (int i = 1; i <= n2; i++)
            a[i-1] = i;

        // shuffle
        for (int i = 0; i < n2; i++) {
            int r = (int) (Math.random() * (i+1));     // int between 0 and i
            int swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
        return a;
    }    

    
}
