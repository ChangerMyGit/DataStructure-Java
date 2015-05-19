package search;

import java.util.List;

public class Search {

	public static void main(String[] args) {
		int a[] = { 1, 2, 3, 5, 5, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 9, 10,
				10 };
		// System.out.println(binSearchA(a, 1));
		// System.out.println(binSearchB(a, 1));
		System.out.println(searchElem(a, 5));
	}

	private static int searchElem(int[] list, int e) {
		int r = -1, i;
		if (list[0] > e)
			return r;
		for (i = 0; i < list.length && list[i] <= e; i++) {
			if (list[i] == e)
				return i;
		}
		if (i <= list.length && i > -1)
			return i - 1;
		return r;
	}

	public static int binSearchA(int a[], int e) {
		int lo = 0, hi = a.length;
		while (lo < hi) {
			int mi = (lo + hi) >> 1;
			if (a[mi] > e)
				hi = mi;
			else if (a[mi] < e)
				lo = mi + 1;
			else
				return mi;
		}
		return -1;
	}

	// 左右分支各为依次
	public static int binSearchB(int a[], int e) {
		int lo = 0, hi = a.length;
		while (hi - lo > 1) {
			int mi = (lo + hi) >> 1;
			if (a[mi] > e)
				hi = mi;
			else
				lo = mi;
		}
		return (a[lo] == e) ? lo : -1;
	}

	public static int binSearchC(int a[], int e) {
		int lo = 0, hi = a.length;
		while (lo < hi) {
			int mi = (lo + hi) >> 1;
			if (a[mi] > e)
				hi = mi;
			else
				lo = mi + 1;
		}
		return lo - 1;
	}
}
