package heap;

import java.util.Arrays;

public class HeapDemo {

	public static void main(String[] args) {
		PQ_ComplHeap<Integer> heap = new PQ_ComplHeap<Integer>();
		int a[] = { 9, 4, 7, 2, 3, 5, 1, 8, 6 };
		for (int i = 0; i < a.length; i++)
			heap.insert(a[i]);
		// heap.insert(10);
		// heap.delMax();
		// heap.delMax();
		for (int i = 0; i < a.length; i++)
			System.out.print(heap.delMax() + " ");
		// System.out.println(Arrays.toString(heap.toArray()));
	}

}
