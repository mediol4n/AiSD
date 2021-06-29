import java.util.Arrays;

public class SelectingAlgorithms {


    //------------------------ RANDOMIZED SELECT -----------------------
    public static int randomizedSelect(int[] A, int low, int high, int i, Stats stats) {
        System.err.println("We look for " + i + " position stat in this array ↓");
        System.err.print("[");
        for (int g = low; g <= high; g++) {
            System.err.print(A[g] + ", ");
        }
        System.err.println("]");
        
        if (low == high) {
            return A[low];
        }
        int q = randomizedPartition(A, low, high, stats);
        int k = q - low +  1;
        System.err.println("Pivot: " + A[q]);
        if (i == k) {
            return A[q];
        } else if (i <= k) { 
            return randomizedSelect(A, low, q, i, stats);
        } else {
            return randomizedSelect(A, q + 1, high, i - k, stats);
        }
    }

    private static int partition(int A[], int low, int high, Stats stats) {
		int i = low - 1;
		int j = low;
		int pivot = A[high];
		while(j <= high - 1) {
            stats.comparisons++;
            System.err.println("Compare: " + A[j] + " and " + pivot);
			if (A[j] <= pivot) {
				i++;
                stats.swaps++;
				swap(A, i, j);
			}
			j++;
		}
        stats.swaps++;
		swap(A, high, i + 1);
		return i + 1;
	}
	
	private static int randomizedPartition(int[] A, int low, int high, Stats stats) {
		int pivotIndex = (int)(Math.random() * (high - low) + low);
        stats.swaps++;
		swap(A, pivotIndex, high);
		return partition(A, low, high, stats);
	}
	
	private static void swap(int[] A, int i, int j) {
        System.err.println("Swap: " + A[i] + " ⮂ " + A[j]);
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
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
                System.err.println("Set " + A[i] + " on " + i+1 + " position.");
                stats.swaps++;
                i -= 1;
            }
            A[i+1] = key;
        }
    }

    public static int select(int A[], int low, int high, int k, Stats stats) {

        if (k > 0 && k <= high - low + 1) {
            // number of elements in array
            int n = high - low + 1;

            int i, median[] = new int[(n + 4) / 5];

            for (i = 0; i < median.length - 1; i++) {
                median[i] = getMedian(Arrays.copyOfRange(A, 5 * i + low, 5 * i + low + 4), 5, stats);
            }

            if (n % 5 == 0) {
                median[i] = getMedian(Arrays.copyOfRange(A, 5 * i + low, 5 * i + low + 4), 5, stats);
                i++;
            } else {
                median[i] = getMedian(Arrays.copyOfRange(A, 5 * i + low, 5 * i + low + (n % 5)), n % 5, stats);
                i++;
            }

            int medOfMed = i == 1 ? median[i - 1]
                    : select(median, 0, i - 1, i / 2, stats);
            System.err.println("Median of medians: " + medOfMed + " (and our pivot)");

            int partition = partitionPractise(A, low, high, medOfMed, stats);

            if (partition - low == k - 1) {
                return A[partition];
            }

            if (partition - low > k - 1) {
                return select(A, low, partition - 1, k, stats);
            }

            return select(A, partition + 1, high, k - (partition + 1) + low, stats);
        }

        return -1;
    }

    private static int getMedian(int A[], int n, Stats stats) {
        System.out.print("Sorting [");
        for (int i = 0; i < A.length; i++) {
            System.err.print(A[i] + ", ");
        }
        System.err.println("] using ➔ Inserion Sort");
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


    //binary searching

    public static int binarySearch(int[] A, int low, int high, int key) {
        if (high >= low) {
            int mid = low + (high - low) / 2;
            if (A[mid] == key) {
                return 1;
            } 

            if (A[mid] > key) {
                return binarySearch(A, low, mid - 1, key);
            } else {
                return binarySearch(A, mid + 1, high, key);
            }
        }
        return -1;
    }

}

