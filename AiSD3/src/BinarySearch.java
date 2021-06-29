import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*  Algorytm jest reukrencyjny
*  T(n) = T(n/2) + O(1)
*   a = 1, b = 2, d = 0
*   log[2, 1] = 0 = d
*   Zatem -> T(n) = O(logn), talice dzielimy zawsze na 2 wiec O(log_2(n))
*/


public class BinarySearch {

    public static String fileName = "zad3prim2.csv";
    public static int[] randTab, copy;
    public static int m = 1000;
    public static void main(String[] args) {
            Random random = new Random();
            String[] algs = {"n/2", "0", "n-1",  "n/4", "n/3", "n/2-1"};

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write("n");
                for (int i = 0; i < algs.length; i++) {
                    writer.write(";" + algs[i] + " - minComparisons;"+ algs[i] + " - maxComparisons;"+ algs[i] + " - averageCnomparisons;"+ algs[i] + " - standCnomparisons;" + algs[i] + " - time");
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
                        for (int j = 0; j < n; j++) {
                            randTab[j] = random.nextInt(); 
                        }
                        insertionSort(randTab);
                        //randTab = getPermutation(n);
                        //System.out.println(randTab.length);
                        Stats[] stats = new Stats[6];
                        for (int j = 0; j < 6; j++) {
                            stats[j] = new Stats();
                        }


                        copy = randTab.clone();
                        binarySearch(copy, copy[n/2], stats[0]);
                        allStats[6*i] = stats[0]; 


                        copy = randTab.clone();
                        binarySearch(copy, copy[0], stats[1]);
                        allStats[6*i + 1] = stats[1];

                        copy = randTab.clone();
                        binarySearch(copy, copy[n-1], stats[2]); 
                        allStats[6*i+2] = stats[2];

                        copy = randTab.clone();
                        binarySearch(copy, copy[n/4], stats[3]);
                        allStats[6*i+3] = stats[3];

                        copy = randTab.clone();
                        binarySearch(copy, copy[n/3], stats[4]);
                        allStats[6*i+4] = stats[4];

                        copy = randTab.clone();
                        binarySearch(copy, copy[random.nextInt(n)], stats[5]);
                        allStats[6*i+5] = stats[5];

                        for (int l = 0; l < 6; l++) {
                            avg_stats[l].comparisons += stats[l].comparisons / m;
                            avg_stats[l].swaps += stats[l].swaps / m;
                            avg_stats[l].time += stats[l].time /m;
                        }
                    }
                    writer.write("" + n);
                    for(int i = 0; i < 6; i++) {
                        int minC = findMinComp(allStats, i);
                        int maxC = findMaxComp(allStats, i);
                        int avgC = avg_stats[i].comparisons;
                        int stdC = findStdComp(allStats, i, avgC);
                        long time = avg_stats[i].time;
                        writer.write(";" + minC + ";" + maxC + ";" + avgC + ";" + stdC + ";" + time);
                    }
                    writer.write("\n");
                    System.out.println(n);   
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
   
    public static int binarySearch(int[] A, int key, Stats stats) {
        long start = System.nanoTime();
        int fasdf = binarySearch(A, 0, A.length-1, key, stats);
        long end = System.nanoTime();
        stats.time = (end - start);
        return fasdf;
    }

    public static int binarySearch(int[] A, int low, int high, int key, Stats stats) {
        if (high >= low) {
            int mid = low + (high - low) / 2;
            stats.comparisons++;
            if (A[mid] == key) {
                return 1;
            } 

            stats.comparisons++;
            if (A[mid] > key) {
                return binarySearch(A, low, mid - 1, key, stats);
            } else {
                return binarySearch(A, mid + 1, high, key, stats);
            }
        }
        return -1;
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


    public static void insertionSort(int[] A) {
        int key, i;
        for (int j = 1; j < A.length; j++) {
            key = A[j];
            i = j - 1;
            while (i > 0 && A[i] > key) {
                A[i+1] = A[i];
                //System.out.println("Set " + A[i] + " on " + i+1 + " position.");
                i -= 1;
            }
            A[i+1] = key;
        }
    }
}
