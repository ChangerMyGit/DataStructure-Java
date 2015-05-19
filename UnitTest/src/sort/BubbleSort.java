package sort;

import java.util.Arrays;

public class BubbleSort {

	// A[lo , hi)
	public static void bubbleSort(int a[], int lo, int hi) {
		while (lo < (hi = bubble(a, lo, hi)))
			;
	}

	private static int bubble(int a[], int lo, int hi) {
		int last = lo;
		while (++lo < hi) {
			if (a[lo - 1] > a[lo]) {
				last = lo;
				swap(a, lo - 1, lo);
			}
		}
		return last;
	}

	private static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		int a[] = { 9, 4, 7, 2, 3, 5, 1, 8, 6 };
		bubbleSort(a, 0, a.length);
		System.out.println(Arrays.toString(a));
	}

}
