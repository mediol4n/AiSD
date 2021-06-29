import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class HybridSort {

        public static ArrayList<Integer> randTab;
        public static ArrayList<Integer> copy;
    
    public static <T extends Comparable> void hybridSort1(List<T> list, Stats stats, int limit) {
        long start = System.nanoTime();
        hybridSort(list, stats, limit);
        long end = System.nanoTime();
        stats.time = (end - start)/100;

    }
        public static <T extends Comparable> void hybridSort(List<T> list, Stats stats, int limit) {
        int size = list.size();
        if (size <= limit) {
            if (list.isEmpty()) {
                return;
            }

            for (int i = 1; i < list.size(); i++) {
                int j = i;
                T aux = list.get(i);
                stats.comparisons++;
                int checker = 1;
                while (j > 0 && aux.compareTo(list.get(j - 1)) <= 0) {
                    if (checker == 0) {
                        stats.comparisons++;
                    }
                    list.set(j, list.get(j - 1));
                    stats.sets++;
                    j--;
                    checker = 0;
                }
                stats.sets++;
                list.set(j, aux);
            }
        } else {
            int middle = size / 2;

            List<T> leftList = new ArrayList<>();
            List<T> rigthList = new ArrayList<>();

            for (int i = 0; i < middle; i++) {
                T get = list.get(i);
                leftList.add(get);
                stats.sets++;
            }

            for (int i = middle; i < size; i++) {
                T get = list.get(i);
                rigthList.add(get);
                stats.sets++;
            }

            hybridSort(leftList, stats, limit);
            hybridSort(rigthList, stats, limit);

            merge(list, leftList, rigthList, stats);
        } 

   }  

       private static <T extends Comparable> void merge(List<T> list, List<T> leftList, List<T> rigthList, Stats stats) {
        list.clear();
        while (!rigthList.isEmpty() && !leftList.isEmpty()) {
            stats.comparisons++;
            if (rigthList.get(0).compareTo(leftList.get(0)) > 0) {
                stats.sets++;
                list.add(leftList.get(0));
                leftList.remove(0);
            } else {
                stats.sets++;
                list.add(rigthList.get(0));
                rigthList.remove(0);
            }
        }

        while (!leftList.isEmpty()) {
            stats.sets++;
            list.add(leftList.get(0));
            leftList.remove(0);
        }

        while (!rigthList.isEmpty()) {
            stats.sets++;
            list.add(rigthList.get(0));
            rigthList.remove(0);
        }

    }


    public static void main(String[] args) {
            MersenneTwister random = new MersenneTwister();
            String[] algs = {"hybrid-9", "hybrid-12", "hybrid-15", "hybrid-18", "hybrid-21"};

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("hybrid.csv"));
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
                    for (int i = 0; i < 100; i++) {
                        randTab = new ArrayList<>();
                        for (int j = 0; j < n; j++) {
                            randTab.add(random.nextInt(213700));
                        }
                        Stats[] stats = new Stats[5];
                        for (int j = 0; j < 5; j++) {
                            stats[j] = new Stats();
                        }
                        copy = new ArrayList<>(randTab);
                        hybridSort1(copy, stats[0], 9);

                        copy = new ArrayList<>(randTab);
                        hybridSort1(copy, stats[1], 12);

                        copy = new ArrayList<>(randTab);
                        hybridSort1(copy, stats[2], 15);

                        copy = new ArrayList<>(randTab);
                        hybridSort1(copy, stats[3], 18);

                        copy = new ArrayList<>(randTab);
                        hybridSort1(copy, stats[4], 21);
                         for (int j = 0; j < 5; j++) {
                            avg_stats[j].comparisons += stats[j].comparisons / 100;
                            avg_stats[j].sets += stats[j].sets / 100;
                            avg_stats[j].time += stats[j].time / 100;
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

    } 


}
