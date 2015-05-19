package sort;

import java.util.Arrays;
// �鲢����
public class MergeSort {

	public static void main(String[] args) {
		int a[] = { 9, 4, 7, 2, 3, 5,10, 1, 8, 6 ,3};
		mergeSort(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));
	}

	public static void mergeSort(int a[], int lo, int hi) {
		if (hi - lo < 2) {
			if (a[lo] > a[hi]) {// ����Ϊ2 ��Ԫ�ص���
				swap(a, lo, hi);
			}
		} else {// ���ȴ���2 �źϲ�
			int mi = (lo + hi) >> 1;
			mergeSort(a, lo, mi);
			mergeSort(a, mi, hi);
			merge(a, lo, mi, hi);
		}
	}

	private static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	private static void merge(int a[], int lo, int mi, int hi) {
		int i, j, k, bi, lb, lc;
		lb = mi - lo;
		int b[] = new int[lb];
		// ����Aǰ�ε�B
		for (i = lo, bi = 0; bi < lb; i++)
			b[bi++] = a[i];
		// C = [mi,hi)
		for (i = lo, j = 0, k = mi; (j < lb || k < hi);) {
			if (k >= hi || (j < lb && (b[j] <= a[k]))) {
				a[i++] = b[j++];
				//continue;
			}

			if (j >= lb || (mi < hi && (a[k] <= b[j]))) {
				a[i++] = a[k++];
				//continue;
			}
		}
	}
}
