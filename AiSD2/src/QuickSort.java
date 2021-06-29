import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class QuickSort {

        public static ArrayList<Integer> randTab;
        public static ArrayList<Integer> copy;
        
        public static <T extends Comparable> void dualPivotQuickSort(List<T> list, Stats stats) {
        long start = System.nanoTime();    
        int begin = 0;
        int end = list.size() - 1;
        dualPivotQuickSort(list, stats, begin, end);
        long finish = System.nanoTime();
        stats.time = (finish - start)/100;
    }

        private static <T extends Comparable> void dualPivotQuickSort(List<T> list, Stats stats, int left, int right) {
        T p, q;
        int j, k, i;
        int d;
        T temp;
        if (left >= right) {
            return;
        }

        stats.comparisons++;
        if (list.get(right).compareTo(list.get(left)) <= 0) {
            p = list.get(right);
            q = list.get(left);
        } else {
            q = list.get(right);
            p = list.get(left);  
        }
        

        
        i = left + 1; // i - liczba elementow wiekszych od p
        j = i; // j = liczba elementow pomiedzy p i q
        k = right - 1; // k = lizba elementow wiekszych od q
        // Do tej pory sklasyfikowaliśmy co najmniej tyle samo małych, co dużych elementów (tj. D ≥ 0).

        d = 0;

        while (j <= k) {
            if (d >= 0) {
                stats.comparisons++;
                if (list.get(j).compareTo(p) <= 0) {
                    stats.sets++;
                    T tmp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, tmp);
                    i++;
                    j++;
                    d++;
                } else {
                    stats.comparisons++;
                    if (list.get(j).compareTo(q) <= 0) {
                        j++;
                    } else {
                        stats.sets++;
                        T tmp = list.get(j);
                        list.set(j, list.get(k));
                        list.set(k, tmp);
                        k--;
                        d--;
                    }
                }
            } else {
                stats.comparisons++;
                if (list.get(k).compareTo(q) > 0) {
                    k--;
                    d--;
                } else {
                    stats.comparisons++;
                    if (list.get(k).compareTo(p) <= 0) {
                        temp = list.get(k);
                        list.set(k, list.get(j));
                        list.set(j, list.get(i));
                        list.set(i,temp);
                        i++;
                        d++;
                        stats.sets += 2;
                    } else {
                        stats.sets+=1;
                        stats.sets++;
                        temp = list.get(j);
                        list.set(j, list.get(k));
                        list.set(k, temp);
                    }
                    j++;
                }
            }
        }
        list.set(left, list.get(i-1));
        list.set(i-1, p);
        list.set(right, list.get(k+1));
        list.set(k+1, q);
        dualPivotQuickSort(list, stats, left, i-2);
        dualPivotQuickSort(list, stats, i, k);
        dualPivotQuickSort(list, stats, k+2, right);
    }



        public static void main(String[] args) {
            MersenneTwister random = new MersenneTwister();
            String algs = "dual";
            /*randTab = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                randTab.add(random.nextInt(213700));
            }
            System.out.println(randTab);
            Stats stats = new Stats();
            dualPivotQuickSort(randTab, stats);
            System.out.println(randTab); */
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("quick.csv"));
                writer.write("n");
                writer.write(";" + algs + " - comparations;" + algs + " - sets;" + algs + " - time;" + algs + " - comparations/n;" + algs + " - sets/n"); 
                writer.write("\n");

                for (int n = 100; n <= 10000; n += 100) {
                    randTab = new ArrayList<>();
                    copy = new ArrayList<>();

                    Stats avg_stats = new Stats();
                    for (int i = 0; i < 100; i++) {
                        randTab = new ArrayList<>();
                        for (int j = 0; j < n; j++) {
                            randTab.add(random.nextInt(213700));
                        }
                        Stats stats = new Stats();
                        copy = new ArrayList<>(randTab);
                        dualPivotQuickSort(copy, stats);


                        avg_stats.comparisons += stats.comparisons / 100;
                        avg_stats.sets += stats.sets / 100;
                        avg_stats.time += stats.time / 100;
                    }
                    writer.write("" + n);
                    int c = avg_stats.comparisons;
                    int s = avg_stats.sets;
                    long t = avg_stats.time;
                    int cn = c / n;
                    int sn = s / n;
                    writer.write(";" + c + ";" + s + ";" + t + ";" + cn + ";" + sn);                        
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
