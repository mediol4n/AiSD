import java.util.ArrayList;
import java.util.List;

public class SortingAlgorithms {

    public static <T extends Comparable> void insertionSort(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime();
        if (list.isEmpty()) {
            return;
        }

        for (int i = 1; i < list.size(); i++) {
            int j = i;
            T aux = list.get(i);
            if (statFlag > 0) {
                System.err.println("Compare-> " + aux + " and " + list.get(j-1));
            }
            stats.comparisons++;
            int checker = 1;
            while (j > 0 && aux.compareTo(list.get(j - 1)) <= 0) {
                if (checker == 0) {
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + aux + " and " + list.get(j-1));
                    }
                    stats.comparisons++;
                }
                list.set(j, list.get(j - 1));
                if (statFlag > 0) {
                    System.err.println("Set " + list.get(j-1) + " on " + j + " position");
                }
                stats.sets++;
                j--;
                checker = 0;
            }
            //System.out.println(list);
            list.set(j, aux);
            stats.sets++;
            if (statFlag > 0) {
                System.err.println("Set " + aux + " on " + j + " position");
            }
        }
        long finish = System.nanoTime();
        stats.time = (finish - start)/100;

    }

    public static <T extends Comparable> void quickSort(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime();
        int begin = 0;
        int end = list.size()-1;
        quickSort(list, begin, end, stats, statFlag);
        long finish = System.nanoTime();
        stats.time = (finish - start)/100;
    }
    
    private static <T extends Comparable> void quickSort(List<T> list, int begin, int end, Stats stats, int statFlag) {
        T pivot = list.get((begin + end) / 2);
        int i = begin;
        int j = end;
        int checker = 1;
        while (i < j) {
            checker = 1;
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + list.get(i) + " and " + pivot);
            }
            while (list.get(i).compareTo(pivot) < 0) {
                if (checker ==  0) {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(i) + " and " + pivot);
                    }
                }
                checker = 0;
                i++;
            }
            checker = 1;
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + list.get(j) + " and " + pivot);
            }
            while (list.get(j).compareTo(pivot) > 0) {
                if (checker ==  0) {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(j) + " and " + pivot);
                    }
                }
                checker = 0;
                j--;
            }

            if (i <= j) {
                T aux = list.get(i);
                list.set(i, list.get(j));
                if (statFlag > 0) {
                    System.err.println("Set " + list.get(j) + " on " + i + " position");
                }
                list.set(j, aux);
                if (statFlag > 0) {
                    System.err.println("Set " + aux + " on " + j + " position");
                }
                stats.sets++;
                i++;
                j--;
            }
        }

        if (j > begin) {
            quickSort(list, begin, j, stats, statFlag);
        }

        if (i < end) {
            quickSort(list, i, end, stats, statFlag);
        }


    }

    public static <T extends Comparable> void mergeSort1(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime(); 
        mergeSort(list, stats, statFlag);
        long end = System.nanoTime();
        stats.time = (end - start)/100;
    }
    
    public static <T extends Comparable> void mergeSort(List<T> list, Stats stats, int statFlag) {
        int size = list.size();
        
        if (size >= 2) {

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

            mergeSort(leftList, stats, statFlag);
            mergeSort(rigthList, stats, statFlag);

            merge(list, leftList, rigthList, stats, statFlag);
        }


    }

    private static <T extends Comparable> void merge(List<T> list, List<T> leftList, List<T> rigthList, Stats stats, int statFlag) {
        list.clear();
        int index = 0;
        while (!rigthList.isEmpty() && !leftList.isEmpty()) {
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + rigthList.get(0) + " and " + leftList.get(0));
            }
            if (rigthList.get(0).compareTo(leftList.get(0)) > 0) {
                if (statFlag > 0) {
                    System.err.println("Set " + leftList.get(0) + " on " + index + " position");
                }
                stats.sets++;
                list.add(leftList.get(0));
                leftList.remove(0);
            } else {
                if (statFlag > 0) {
                    System.err.println("Set " + rigthList.get(0) + " on " + index + " position");
                }
                stats.sets++;
                list.add(rigthList.get(0));
                rigthList.remove(0);
            }
            index++;
        }

        while (!leftList.isEmpty()) {
            if (statFlag > 0) {
                System.err.println("Set " + leftList.get(0) + " on " + index + " position");
            }
            stats.sets++;
            list.add(leftList.get(0));
            leftList.remove(0);
        }

        while (!rigthList.isEmpty()) {
            if (statFlag > 0) {
                System.err.println("Set " + rigthList.get(0) + " on " + index + " position");
            }
            stats.sets++;
            list.add(rigthList.get(0));
            rigthList.remove(0);
        }

    }

    public static <T extends Comparable> void hybridSort1(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime();
        hybridSort(list, stats, statFlag);
        long end = System.nanoTime();
        stats.time = (end - start)/100;

    }

    public static <T extends Comparable> void hybridSort(List<T> list, Stats stats, int statFlag) {
        int size = list.size();
        if (size <= 21) {
            if (list.isEmpty()) {
                return;
            }

            for (int i = 1; i < list.size(); i++) {
                int j = i;
                T aux = list.get(i);
                if (statFlag > 0) {
                    System.err.println("Compare-> " + aux + " and " + list.get(j-1));
                }
                stats.comparisons++;
                int checker = 1;
                while (j > 0 && aux.compareTo(list.get(j - 1)) <= 0) {
                    if (checker == 0) {
                        if (statFlag > 0) {
                            System.err.println("Compare-> " + aux + " and " + list.get(j-1));
                        }
                        stats.comparisons++;
                    }
                    list.set(j, list.get(j - 1));
                    if (statFlag > 0) {
                        System.err.println("Set " + list.get(j-1) + " on " + j + " position");
                    }
                    stats.sets++;
                    j--;
                    checker = 0;
                }
                stats.sets++;
                list.set(j, aux);
                if (statFlag > 0) {
                    System.err.println("Set " + aux + " on " + j + " position");
                }
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

            hybridSort(leftList, stats, statFlag);
            hybridSort(rigthList, stats, statFlag);

            merge(list, leftList, rigthList, stats, statFlag);
        } 

   } 

    public static <T extends Comparable> void dualPivotQuickSort(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime();    
        int begin = 0;
        int end = list.size() - 1;
        dualPivotQuickSort(list, stats, statFlag, begin, end);
        long finish = System.nanoTime();
        stats.time = (finish - start)/100;
    }

    private static <T extends Comparable> void dualPivotQuickSort(List<T> list, Stats stats, int statFlag, int left, int right) {
        int index1 = 0, index2 = 0;
        T p, q;
        int j, k, l;
        int x;
        T temp;
        if (left >= right) {
            return;
        }

        MersenneTwister mt = new MersenneTwister();

        for (int i = 0; i < 5; i++) {
            index1 = left + mt.nextInt(right - left);
            index2 = left + mt.nextInt(right - left);

            stats.comparisons++;
            if (list.get(index1).compareTo(list.get(index2)) != 0) {
                break;
            }
        }
        stats.comparisons+=2;
        if (list.get(index1).compareTo(list.get(index2)) != 0) {
            if(list.get(index1).compareTo(list.get(index2)) <= 0) {
                T tmp = list.get(index1);
                if (statFlag > 0) {
                   System.err.println("Set " + list.get(right) + " on " + index1 + " position");
                   System.err.println("Set " + tmp + " on " + right + " position");
                   System.err.println("Set " + list.get(left) + " on " + index2 + " position");
                   System.err.println("Set " + list.get(index2) + " on " + left + " position");
                }
                list.set(index1, list.get(right));
                list.set(right, tmp);
                p = list.get(right);
                tmp = list.get(index2);
                list.set(index2, list.get(left));
                list.set(left, tmp);
                q = list.get(left);
                stats.sets+=2;
            }
            else {
                T tmp = list.get(index2);
                if (statFlag > 0) {
                   System.err.println("Set " + list.get(left) + " on " + index1 + " position");
                   System.err.println("Set " + tmp + " on " + right + " position");
                   System.err.println("Set " + list.get(right) + " on " + index2 + " position");
                   System.err.println("Set " + list.get(index1) + " on " + left + " position");
                }
                list.set(index2, list.get(right));
                list.set(right, tmp);
                p = list.get(right);
                tmp = list.get(index1);
                list.set(index1, list.get(left));
                list.set(left, tmp);
                q = list.get(left);
                stats.sets+=2;
            }
        } else {
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + list.get(left) + " and " + list.get(right));
            }
            if (list.get(right).compareTo(list.get(left)) <= 0) {
                p = list.get(right);
                q = list.get(left);
            } else {
                q = list.get(right);
                p = list.get(left);  
            }
        }

        l = left + 1;
        j = l;
        k = right - 1;
        //elementy miedzy j i k sa niesklasyfikowane
        x = 0;

        while (j <= k) {
            if (x >= 0) {
                //wiecej mniejszych elementow lub tyle samo
                stats.comparisons++;
                if (statFlag > 0) {
                    System.err.println("Compare -> " + list.get(j) + " and " + p);
                }
                if (list.get(j).compareTo(p) <= 0) {
                    stats.sets++;
                    if (statFlag > 0) {
                        System.err.println("Set " + list.get(j) + " on  " + l + " position");
                        System.err.println("Set " + list.get(l) + " on  " + j + " position");
                    }
                    T tmp = list.get(j);
                    list.set(j, list.get(l));
                    list.set(l, tmp);
                    l++;
                    j++;
                    x++;
                } else {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(j) + " and " + q);
                    }
                    if (list.get(j).compareTo(q) <= 0) {
                        j++;
                    } else {
                        stats.sets++;
                        if (statFlag > 0) {
                            System.err.println("Set " + list.get(j) + " on  " + l + " position");
                            System.err.println("Set " + list.get(l) + " on  " + j + " position");
                        }
                        T tmp = list.get(j);
                        list.set(j, list.get(k));
                        list.set(k, tmp);
                        k--;
                        x--;
                    }
                }
            } else {
                //wiecej wiekszych elementow
                stats.comparisons++;
                if (statFlag > 0) {
                    System.err.println("Compare-> " + list.get(k) + " and " + q);
                }
                if (list.get(k).compareTo(q) > 0) {
                    k--;
                    x--;
                } else {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(k) + " and " + p);
                    }
                    if (list.get(k).compareTo(p) <= 0) {
                        if (statFlag > 0) {
                            System.err.println("Set " + list.get(j) + " on " + k + " position");
                            System.err.println("Set " + list.get(l) + " on " + j + " position");
                            System.err.println("Set " + list.get(k) + " on " + l + " position");
                        }
                        temp = list.get(k);
                        list.set(k, list.get(j));
                        list.set(j, list.get(l));
                        list.set(l,temp);
                        l++;
                        x++;
                        stats.sets += 3;
                    } else {
                        stats.sets+=2;
                        if (statFlag > 0) {
                            System.err.println("Set " + list.get(j) + " on " + k + " position");
                            System.err.println("Set " + list.get(k) + " on " + j + " position");
                        }
                        stats.sets++;
                        temp = list.get(j);
                        list.set(j, list.get(k));
                        list.set(k, temp);
                    }
                    j++;
                }
            }
        }
        if (statFlag > 0) {
            System.err.println("Set " + list.get(l-1) + " on " + left + " position");
            System.err.println("Set " + p + " on " + (l-1) + " position");
            System.err.println("Set " + list.get(k+1) + " on " + right + " position");
            System.err.println("Set " + q + " on " + (k+1) + " position");
        }
        list.set(left, list.get(l-1));
        list.set(l-1, p);
        list.set(right, list.get(k+1));
        list.set(k+1, q);
        dualPivotQuickSort(list, stats, statFlag, left, l-2);
        dualPivotQuickSort(list, stats, statFlag, l, k);
        dualPivotQuickSort(list, stats, statFlag, k+2, right);
    }

    public static <T extends Comparable> boolean isSorted(List<T> list, int descFlag) {
        if (descFlag == 0) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).compareTo(list.get(i+1)) > 0) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).compareTo(list.get(i+1)) < 0) {
                    return false;
                }
            }
        }
        //System.out.println(list);
        return true;
    }

    public static <T extends Comparable> void insertionSortReverse(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime();
        if (list.isEmpty()) {
            return;
        }

        for (int i = 1; i < list.size(); i++) {
            int j = i;
            T aux = list.get(i);
            if (statFlag > 0) {
                System.err.println("Compare-> " + aux + " and " + list.get(j-1));
            }
            stats.comparisons++;
            int checker = 1;
            while (j > 0 && aux.compareTo(list.get(j - 1)) >= 0) {
                if (checker == 0) {
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + aux + " and " + list.get(j-1));
                    }
                    stats.comparisons++;
                }
                list.set(j, list.get(j - 1));
                if (statFlag > 0) {
                    System.err.println("Set " + list.get(j-1) + " on " + j + " position");
                }
                stats.sets++;
                j--;
                checker = 0;
            }
            //System.out.println(list);
            list.set(j, aux);
            stats.sets++;
            if (statFlag > 0) {
                System.err.println("Set " + aux + " on " + j + " position");
            }
        }
        long finish = System.nanoTime();
        stats.time = (finish - start)/1000000;

    }

    public static <T extends Comparable> void quickSortReverse(List<T> list, Stats stats, int statFlag) {
        int begin = 0;
        int end = list.size()-1;
        quickSortReverse(list, begin, end, stats, statFlag);
    }

    private static <T extends Comparable> void quickSortReverse(List<T> list, int begin, int end, Stats stats, int statFlag) {
        T pivot = list.get((begin + end) / 2);
        long start = System.nanoTime();
        int i = begin;
        int j = end;
        int checker = 1;
        while (i < j) {
            checker = 1;
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + list.get(i) + " and " + pivot);
            }
            while (list.get(i).compareTo(pivot) > 0) {
                if (checker ==  0) {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(i) + " and " + pivot);
                    }
                }
                checker = 0;
                i++;
            }
            checker = 1;
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + list.get(j) + " and " + pivot);
            }
            while (list.get(j).compareTo(pivot) < 0) {
                if (checker ==  0) {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(j) + " and " + pivot);
                    }
                }
                checker = 0;
                j--;
            }

            if (i <= j) {
                T aux = list.get(i);
                list.set(i, list.get(j));
                if (statFlag > 0) {
                    System.err.println("Set " + list.get(j) + " on " + i + " position");
                }
                list.set(j, aux);
                if (statFlag > 0) {
                    System.err.println("Set " + aux + " on " + j + " position");
                }
                stats.sets++;
                i++;
                j--;
            }
        }

        if (j > begin) {
            quickSortReverse(list, begin, j, stats, statFlag);
        }

        if (i < end) {
            quickSortReverse(list, i, end, stats, statFlag);
        }
        long finish = System.nanoTime();
        stats.time = finish - start;

    }

    public static <T extends Comparable> void hybridSortReverse(List<T> list, Stats stats, int statFlag) {
        int size = list.size();
        long start = System.nanoTime();
        if (size <= 9) {
            if (list.isEmpty()) {
                return;
            }

            for (int i = 1; i < list.size(); i++) {
                int j = i;
                T aux = list.get(i);
                if (statFlag > 0) {
                    System.err.println("Compare-> " + aux + " and " + list.get(j-1));
                }
                stats.comparisons++;
                int checker = 1;
                while (j > 0 && aux.compareTo(list.get(j - 1)) >= 0) {
                    if (checker == 0) {
                        if (statFlag > 0) {
                            System.err.println("Compare-> " + aux + " and " + list.get(j-1));
                        }
                        stats.comparisons++;
                    }
                    list.set(j, list.get(j - 1));
                    if (statFlag > 0) {
                        System.err.println("Set " + list.get(j-1) + " on " + j + " position");
                    }
                    stats.sets++;
                    j--;
                    checker = 0;
                }
                stats.sets++;
                list.set(j, aux);
                if (statFlag > 0) {
                    System.err.println("Set " + aux + " on " + j + " position");
                }
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

            hybridSortReverse(leftList, stats, statFlag);
            hybridSortReverse(rigthList, stats, statFlag);

            mergeReverse(list, leftList, rigthList, stats, statFlag);
        } 
        long end = System.nanoTime();
        stats.time = end - start;
        System.out.println(list + " - wyjscie z funkcji");
   } 

    private static <T extends Comparable> void mergeReverse(List<T> list, List<T> leftList, List<T> rigthList, Stats stats, int statFlag) {
        list.clear();
        int index = 0;
        while (!rigthList.isEmpty() && !leftList.isEmpty()) {
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + rigthList.get(0) + " and " + leftList.get(0));
            }
            if (rigthList.get(0).compareTo(leftList.get(0)) < 0) {
                if (statFlag > 0) {
                    System.err.println("Set " + leftList.get(0) + " on " + index + " position");
                }
                stats.sets++;
                list.add(leftList.get(0));
                leftList.remove(0);
            } else {
                if (statFlag > 0) {
                    System.err.println("Set " + rigthList.get(0) + " on " + index + " position");
                }
                stats.sets++;
                list.add(rigthList.get(0));
                rigthList.remove(0);
            }
            index++;
        }

        while (!leftList.isEmpty()) {
            if (statFlag > 0) {
                System.err.println("Set " + leftList.get(0) + " on " + index + " position");
            }
            stats.sets++;
            list.add(leftList.get(0));
            leftList.remove(0);
        }

        while (!rigthList.isEmpty()) {
            if (statFlag > 0) {
                System.err.println("Set " + rigthList.get(0) + " on " + index + " position");
            }
            stats.sets++;
            list.add(rigthList.get(0));
            rigthList.remove(0);
        }

    }

    public static <T extends Comparable> void mergeSortReverse(List<T> list, Stats stats, int statFlag) {
        int size = list.size();
        long start = System.nanoTime();
        if (size >= 2) {

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

            mergeSortReverse(leftList, stats, statFlag);
            mergeSortReverse(rigthList, stats, statFlag);

            mergeReverse(list, leftList, rigthList, stats, statFlag);
        }
        long end = System.nanoTime();
        stats.time = end - start;

    }

    public static <T extends Comparable> void dualPivotQuickSortReverse(List<T> list, Stats stats, int statFlag) {
        long start = System.nanoTime();    
        int begin = 0;
        int end = list.size() - 1;
        dualPivotQuickSortReverse(list, stats, statFlag, begin, end);
        long finish = System.nanoTime();
        stats.time = finish - start;
    }

    private static <T extends Comparable> void dualPivotQuickSortReverse(List<T> list, Stats stats, int statFlag, int left, int right) {
        int index1 = 0, index2 = 0;
        T p, q;
        int j, k, l;
        int x;
        T temp;
        if (left >= right) {
            return;
        }

        MersenneTwister mt = new MersenneTwister();

        for (int i = 0; i < 5; i++) {
            index1 = left + mt.nextInt(right - left);
            index2 = left + mt.nextInt(right - left);

            stats.comparisons++;
            if (list.get(index1).compareTo(list.get(index2)) != 0) {
                break;
            }
        }
        if (list.get(index1).compareTo(list.get(index2)) != 0) {
            if(list.get(index1).compareTo(list.get(index2)) > 0) {
                T tmp = list.get(index1);
                if (statFlag > 0) {
                   System.err.println("Set " + list.get(right) + " on " + index1 + " position");
                   System.err.println("Set " + tmp + " on " + right + " position");
                   System.err.println("Set " + list.get(left) + " on " + index2 + " position");
                   System.err.println("Set " + list.get(index2) + " on " + left + " position");
                }
                list.set(index1, list.get(right));
                list.set(right, tmp);
                p = list.get(right);
                tmp = list.get(index2);
                list.set(index2, list.get(left));
                list.set(left, tmp);
                q = list.get(left);
                stats.sets+=4;
            }
            else {
                T tmp = list.get(index2);
                if (statFlag > 0) {
                   System.err.println("Set " + list.get(left) + " on " + index1 + " position");
                   System.err.println("Set " + tmp + " on " + right + " position");
                   System.err.println("Set " + list.get(right) + " on " + index2 + " position");
                   System.err.println("Set " + list.get(index1) + " on " + left + " position");
                }
                list.set(index2, list.get(right));
                list.set(right, tmp);
                p = list.get(right);
                tmp = list.get(index1);
                list.set(index1, list.get(left));
                list.set(left, tmp);
                q = list.get(left);
                stats.sets++;
            }
        } else {
            stats.comparisons++;
            if (statFlag > 0) {
                System.err.println("Compare-> " + list.get(left) + " and " + list.get(right));
            }
            if (list.get(right).compareTo(list.get(left)) >= 0) {
                p = list.get(right);
                q = list.get(left);
            } else {
                q = list.get(right);
                p = list.get(left);  
            }
        }
        l = left + 1;
        j = l;
        k = right - 1;

        x = 0;

        while (j <= k) {
            if (x >= 0) {
                stats.comparisons++;
                if (statFlag > 0) {
                    System.err.println("Compare -> " + list.get(j) + " and " + p);
                }
                System.err.println("Compare-> " + list.get(j) + " and " + p);
                if (list.get(j).compareTo(p) >= 0) {
                    stats.sets+=2;
                    if (statFlag > 0) {
                        System.err.println("Set " + list.get(j) + " on  " + l + " position");
                        System.err.println("Set " + list.get(l) + " on  " + j + " position");
                    }
                    T tmp = list.get(j);
                    list.set(j, list.get(l));
                    list.set(l, tmp);
                    l++;
                    j++;
                    x++;
                } else {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(j) + " and " + q);
                    }
                    if (list.get(j).compareTo(q) >= 0) {
                        j++;
                    } else {
                        stats.sets+=2;
                        if (statFlag > 0) {
                            System.err.println("Set " + list.get(j) + " on  " + l + " position");
                            System.err.println("Set " + list.get(l) + " on  " + j + " position");
                        }
                        T tmp = list.get(j);
                        list.set(j, list.get(k));
                        list.set(k, tmp);
                        k--;
                        x--;
                    }
                }
            } else {
                stats.comparisons++;
                if (statFlag > 0) {
                    System.err.println("Compare-> " + list.get(k) + " and " + q);
                }
                if (list.get(k).compareTo(q) <= 0) {
                    k--;
                    x--;
                } else {
                    stats.comparisons++;
                    if (statFlag > 0) {
                        System.err.println("Compare-> " + list.get(k) + " and " + p);
                    }
                    if (list.get(k).compareTo(p) >= 0) {
                        if (statFlag > 0) {
                            System.err.println("Set " + list.get(j) + " on " + k + " position");
                            System.err.println("Set " + list.get(l) + " on " + j + " position");
                            System.err.println("Set " + list.get(k) + " on " + l + " position");
                        }
                        temp = list.get(k);
                        list.set(k, list.get(j));
                        list.set(j, list.get(l));
                        list.set(l,temp);
                        l++;
                        x++;
                        stats.sets += 3;
                    } else {
                        stats.sets+=2;
                        if (statFlag > 0) {
                            System.err.println("Set " + list.get(j) + " on " + k + " position");
                            System.err.println("Set " + list.get(k) + " on " + j + " position");
                        }
                        temp = list.get(j);
                        list.set(j, list.get(k));
                        list.set(k, temp);
                    }
                    j++;
                }
            }
        }
        if (statFlag > 0) {
            System.err.println("Set " + list.get(l-1) + " on " + left + " position");
            System.err.println("Set " + p + " on " + (l-1) + " position");
            System.err.println("Set " + list.get(k+1) + " on " + right + " position");
            System.err.println("Set " + q + " on " + (k+1) + " position");
        }
        list.set(left, list.get(l-1));
        list.set(l-1, p);
        list.set(right, list.get(k+1));
        list.set(k+1, q);
        stats.sets+=4;
        dualPivotQuickSortReverse(list, stats, statFlag, left, l-2);
        dualPivotQuickSortReverse(list, stats, statFlag, l, k);
        dualPivotQuickSortReverse(list, stats, statFlag, k+2, right);
    }
}
