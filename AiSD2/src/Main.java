import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int descFlag = 0;
    public static int sortFlag = 0;
    public static int k = 0;
    public static boolean statFlag = false;
    public static int genFlag = 0;
    static String fileName = "";
    public static ArrayList<Integer> randTab;
    public static ArrayList<Integer> copy;


    public static void main(String[] args) {
        try {
            int l = 0;
            while (l < args.length) {
                if (args[l].equals("--stat")) {
                    statFlag = true;
                    fileName = args[l + 1];
                    k = Integer.parseInt(args[l + 2]);
                    break;
                }
                if (args[l].equals("--type")) {
                    if (args[l+1].equals("insert")) {
                        sortFlag = 1;
                    }
                    if (args[l+1].equals("quick")) {
                        sortFlag = 2;
                    }
                    if (args[l+1].equals("merge")) {
                        sortFlag = 3;
                    }
                    if (args[l+1].equals("hybrid")) {
                        sortFlag = 4;
                    }
                    if (args[l+1].equals("dual")) {
                        sortFlag = 5;
                    }
                }
                if (args[l].equals("--gen")) {
                    if (args[l+1].equals("int")) {
                        genFlag = 1;
                    }
                    if (args[l+1].equals("string")) {
                        genFlag = 2;
                    }
                }
                if (args[l].equals("--comp")) {
                    if (args[l+1].equals("desc")) {
                        descFlag = 1;
                    }
                }
                l++;
            }    
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (statFlag == true && k > 0 && fileName.length() > 0) {
            MersenneTwister random = new MersenneTwister();
            String[] algs = {"insert", "quick", "merge", "hybrid", "dual"};

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write("n");
                for (int i = 0; i < algs.length; i++) {
                   writer.write(";" + algs[i] + " - comparations;" + algs[i] + " - sets;" + algs[i] + " - time;" + algs[i] + " - comparations/n;" + algs[i] + " - sets/n"); 
                }
                writer.write("\n");

                for (int n = 100; n <= 10000; n += 100) {
                    randTab = new ArrayList<>();
                    copy = new ArrayList<>();

                    Stats[] avg_stats = new Stats[5];
                    for (int i = 0; i < 5; i++) {
                        avg_stats[i] = new Stats();
                    }
                    for (int i = 0; i < k; i++) {
                        randTab = new ArrayList<>();
                        for (int j = 0; j < n; j++) {
                            randTab.add(random.nextInt(213700));
                        }
                        Stats[] stats = new Stats[5];
                        for (int j = 0; j < 5; j++) {
                            stats[j] = new Stats();
                        }
                        copy = new ArrayList<>(randTab);
                        SortingAlgorithms.insertionSort(copy, stats[0], 0);

                        copy = new ArrayList<>(randTab);
                        SortingAlgorithms.quickSort(copy, stats[1], 0);

                        copy = new ArrayList<>(randTab);
                        SortingAlgorithms.mergeSort1(copy, stats[2], 0);

                        copy = new ArrayList<>(randTab);
                        SortingAlgorithms.hybridSort1(copy, stats[3], 0);

                        copy = new ArrayList<>(randTab);
                        SortingAlgorithms.dualPivotQuickSort(copy, stats[4], 0);
                         for (int j = 0; j < 5; j++) {
                            avg_stats[j].comparisons += stats[j].comparisons / k;
                            avg_stats[j].sets += stats[j].sets / k;
                            avg_stats[j].time += stats[j].time / k;
                        }
                    }
                    writer.write("" + n);
                    for (int i = 0; i < 5; i++) {
                        int c = avg_stats[i].comparisons;
                        int s = avg_stats[i].sets;
                        long t = avg_stats[i].time;
                        int cn = c / n;
                        int sn = s / n;
                        writer.write(";" + c + ";" + s + ";" + t + ";" + cn + ";" + sn);                        
                    }
                    writer.write("\n"); 
                    System.out.println(n);
                }
                writer.close();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

        } else if (sortFlag > 0 && genFlag > 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("How many elements do you wish to sort?");
            int size = scanner.nextInt();
            if (genFlag == 1) {
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(scanner.nextInt());
                }
                Stats stats = new Stats();
                if (descFlag == 0) {
                    switch (sortFlag) {
                        case 1: {
                        SortingAlgorithms.insertionSort(list, stats, 1);
                        break;
                        }
                        case 2: {
                        SortingAlgorithms.quickSort(list, stats, 1);
                        break;
                        }
                        case 3: {
                        SortingAlgorithms.mergeSort1(list, stats, 1);
                        break;
                        }
                        case 4: {
                        SortingAlgorithms.hybridSort1(list, stats, 1);
                        System.out.println(list);
                        }
                        case 5: {
                        SortingAlgorithms.dualPivotQuickSort(list, stats, 1);
                        }
                    }
                } else {
                    switch (sortFlag) {
                        case 1: {
                        SortingAlgorithms.insertionSortReverse(list, stats, 1);
                        break;
                        }
                        case 2: {
                        SortingAlgorithms.quickSortReverse(list, stats, 1);
                        break;
                        }
                        case 3: {
                        SortingAlgorithms.mergeSortReverse(list, stats, 1);
                        break;
                        }
                        case 4: {
                        SortingAlgorithms.hybridSortReverse(list, stats, 1);
                        System.out.println(list);
                        }
                        case 5: {
                        SortingAlgorithms.dualPivotQuickSortReverse(list, stats, 1);
                        }
                    }                        
                }  
                System.err.println("Comparisons: " + stats.comparisons + ", sets: " + stats.sets  + ", time: " + stats.time);
                if (SortingAlgorithms.isSorted(list, descFlag)) {
                    System.out.println(list);
                }
            }
            else if (genFlag == 2) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    list.add(scanner.next());
                }
                Stats stats = new Stats();
                if (descFlag == 0) {
                    switch (sortFlag) {
                        case 1: {
                        SortingAlgorithms.insertionSort(list, stats, 1);
                        break;
                        }
                        case 2: {
                        SortingAlgorithms.quickSort(list, stats, 1);
                        break;
                        }
                        case 3: {
                        SortingAlgorithms.mergeSort1(list, stats, 1);
                        break;
                        }
                        case 4: {
                        SortingAlgorithms.hybridSort1(list, stats, 1);
                        System.out.println(list);
                        }
                        case 5: {
                        SortingAlgorithms.dualPivotQuickSort(list, stats, 1);
                        }
                    }
                } else {
                    switch (sortFlag) {
                        case 1: {
                        SortingAlgorithms.insertionSortReverse(list, stats, 1);
                        break;
                        }
                        case 2: {
                        SortingAlgorithms.quickSortReverse(list, stats, 1);
                        break;
                        }
                        case 3: {
                        SortingAlgorithms.mergeSortReverse(list, stats, 1);
                        break;
                        }
                        case 4: {
                        SortingAlgorithms.hybridSortReverse(list, stats, 1);
                        System.out.println(list);
                        }
                        case 5: {
                        SortingAlgorithms.dualPivotQuickSortReverse(list, stats, 1);
                        }
                    }                        
                }
                System.err.println("Comparisons: " + stats.comparisons + ", sets: " + stats.sets  + ", time: " + stats.time);
                if (SortingAlgorithms.isSorted(list, descFlag)) {
                    System.out.println(list);
                }
            }
            scanner.close();
        }
    }



}
