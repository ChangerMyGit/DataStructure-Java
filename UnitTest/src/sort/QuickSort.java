package sort;

import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		int a[] = { 9, 4, 7, 2, 3, 5, 1, 8, 6 };
		QuickSort sort = new QuickSort();
		sort.quickSort(a, 0, a.length);
		System.out.println(Arrays.toString(a));
	}

	public void quickSort(int a[], int lo, int hi) {
		if (hi - lo < 2) // 单元素区间自然有序，否则...
			return;
		int mi = partition(a, lo, hi - 1);
		quickSort(a, lo, mi);
		quickSort(a, mi + 1, hi);
	}

	private int partition(int a[], int lo, int hi) {
		int pivot = a[lo];// 以首元素为候选轴点——经以上交换，等效于随机选取
		while (lo < hi) {
			while (lo < hi && a[hi] >= pivot)
				hi--;
			if (lo < hi)
				a[lo++] = a[hi];// 小于pivot者归入左侧子向量
			while (lo < hi && a[lo] <= pivot)
				lo++;
			if (lo < hi)
				a[hi--] = a[lo];
		}
		a[lo] = pivot;
		return lo;
	}
}
