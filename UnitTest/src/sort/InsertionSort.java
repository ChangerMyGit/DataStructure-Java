package sort;

import java.util.LinkedList;
import java.util.List;

public class InsertionSort {

	public static void main(String[] args) {
		int a[] = { 9, 4, 7, 2, 3, 5, 10, 1, 8, 6, 3 };
		List list = new LinkedList();
		for (int i = 0; i < a.length; i++) {
			list.add(a[i]);
		}
		insertSort(list);
		System.out.println(list.toString());
	}

	private static void insertSort(List list) {
		for (int i = 1; i < list.size(); i++) {
			search(list,i,(int)list.remove(i));
		}
	}

	private static void search(List list, int n, int e) {
		int i;
		for (i = 0; i < n; i++) {
			if ((int) list.get(i) > e) {
				list.add(i, e);
				break;
			}
		}
		if (i == n)
			list.add(i, e);
	}
}
