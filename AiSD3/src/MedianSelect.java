import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class MedianSelect {

    public static String fileName = "zad2prim.csv";
    public static int[] randTab, copy;
    public static int m = 1000;
    public static void main(String[] args) {
        Random random = new Random();
            String[] algs = {"k-3", "k-5", "k-7",  "k-17", "k-9", "k-25"};

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write("n");
                for (int i = 0; i < algs.length; i++) {
                    writer.write(";" + algs[i] + " - minComparisons;"+ algs[i] + " - maxComparisons;"+ algs[i] + " - averageCnomparisons;"+ algs[i] + " - standCnomparisons;" + algs[i] + " - minSets;"
                     + algs[i] + " - maxSets;" + algs[i] + " - avgSets;" + algs[i] + " - stdSets;" + algs[i] + " - time");
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
                        //randTab = getPermutation(n);
                        //System.out.println(randTab.length);
                        Stats[] stats = new Stats[6];
                        for (int j = 0; j < 6; j++) {
                            stats[j] = new Stats();
                        }

                        int key = random.nextInt(n);

                        copy = randTab.clone();
                        select(copy, key, stats[0], 3);
                        allStats[6*i] = stats[0]; 


                        copy = randTab.clone();
                        select(copy, key, stats[1], 5);
                        allStats[6*i + 1] = stats[1];

                        copy = randTab.clone();
                        select(copy, key, stats[2], 7); 
                        allStats[6*i+2] = stats[2];

                        copy = randTab.clone();
                        select(copy, key, stats[3], 17);
                        allStats[6*i+3] = stats[3];

                        copy = randTab.clone();
                        select(copy, key, stats[4], 9);
                        allStats[6*i+4] = stats[4];

                        copy = randTab.clone();
                        select(copy, key, stats[5], 25);
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
                        int minS = findMinSwap(allStats, i);
                        int maxS = findMaxSwap(allStats, i);
                        int avgS = avg_stats[i].swaps;
                        int stdS = findStdSwap(allStats, i, avgS);
                        long time = avg_stats[i].time;
                        writer.write(";" + minC + ";" + maxC + ";" + avgC + ";" + stdC + ";" + minS + ";" + maxS + ";" + avgS + ";" + stdS + ";" + time);
                    }
                    writer.write("\n");
                    System.out.println(n);   
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static int select(int A[], int k, Stats stats, int bound) {
        long start = System.nanoTime();
        int select = select(A, 0, A.length-1, k, stats, bound);
        long end = System.nanoTime();
        stats.time =  ((end-start)/100);
        return select;
    }

    public static int select(int A[], int low, int high, int k, Stats stats, int bound) {

        if (k > 0 && k <= high - low + 1) {
            // number of elements in array
            int n = high - low + 1;

            int i, median[] = new int[(n + bound - 1) / bound];

            for (i = 0; i < median.length - 1; i++) {
                median[i] = getMedian(Arrays.copyOfRange(A, bound * i + low, bound * i + low + bound - 1), bound, stats);
            }

            if (n % bound == 0) {
                median[i] = getMedian(Arrays.copyOfRange(A, bound * i + low, bound * i + low + bound - 1), bound, stats);
                i++;
            } else {
                median[i] = getMedian(Arrays.copyOfRange(A, bound * i + low, bound * i + low + (n % bound)), n % bound, stats);
                i++;
            }

            int medOfMed = i == 1 ? median[i - 1]
                    : select(median, 0, i - 1, i / 2, stats, bound);
            //System.out.println("Median of medians: " + medOfMed + " (and our pivot)");

            int partition = partitionPractise(A, low, high, medOfMed, stats);

            if (partition - low == k - 1) {
                return A[partition];
            }

            if (partition - low > k - 1) {
                return select(A, low, partition - 1, k, stats, bound);
            }

            return select(A, partition + 1, high, k - (partition + 1) + low, stats, bound);
        }

        return -1;
    }

    private static int getMedian(int A[], int n, Stats stats) {
        //System.out.print("Sorting [");
        //for (int i = 0; i < A.length; i++) {
        //    System.out.print(A[i] + ", ");
        //}
        //System.out.println("] using ➔ Inserion Sort");
        insertionSort(A, stats);
        return A[n / 2];
    }



    private static int partitionPractise(int[] A, int low, int high, int pivot, Stats stats) {

        for (int i = 0; i < A.length; i++) {
            if (A[i] == pivot) {
                stats.swaps++;
                swap(A, i, high);
                break;
            }
        }
        int index = low - 1;
        int i = low;
        while (i < high) {
            stats.comparisons++;
            if (A[i] < pivot) {
                index++;
                stats.swaps++;
                swap(A, i, index);
            }
            i++;
        }
        index++;
        stats.swaps++;
        swap(A, index, high);
        return index;
    }


    public static void insertionSort(int[] A, Stats stats) {
        int key, i;
        for (int j = 1; j < A.length; j++) {
            key = A[j];
            i = j - 1;
            int checker = 1;
            stats.comparisons++;
            while (i > 0 && A[i] > key) {
                if (checker == 0) {
                    stats.comparisons++;
                }
                A[i+1] = A[i];
                //System.out.println("Set " + A[i] + " on " + i+1 + " position.");
                stats.swaps++;
                i -= 1;
            }
            A[i+1] = key;
        }
    }

    	
	private static void swap(int[] A, int i, int j) {
        //System.out.println("Swap: " + A[i] + " ⮂ " + A[j]);
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
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
}
