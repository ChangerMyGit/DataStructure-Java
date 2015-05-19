package sort;

import java.util.ArrayList;
import java.util.List;

// 选择排序
public class SelectionSort {

	public static void main(String[] args) {
		int a[] = { 9, 4, 7, 2, 3, 5, 10, 1, 8, 6, 3 };
		List list = new ArrayList();
		for (int i = 0; i < a.length; i++) {
			list.add(a[i]);
		}
		selectionSort(list);
		System.out.println(list.toString());
		// System.out.println(selectMax(list,6));
	}

	// 取到n的最大者
	private static int selectMax(List<Integer> list, int n) {
		int max = list.get(0);
		int seq = 0;
		for (int i = 1; i <= n; i++) {
			if (max < list.get(i)) {
				max = list.get(i);
				seq = i;
			}
		}
		return seq;
	}

	private static void selectionSort(List<Integer> list) {
		for (int j = list.size() - 1, p = list.get(j); j >= 0; j--) {
			int maxSeq = selectMax(list, j - 1);
			if (list.get(maxSeq) > list.get(j))
				swap(list, maxSeq, j);
		}
	}

	private static void swap(List<Integer> list, int i, int j) {
		int temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}
}
