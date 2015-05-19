package tree;

import java.util.TreeMap;

public class TreeDemo {

	public static void main(String[] args) {
		/*
		 * BinTree<String> tree = new BinTree<String>(); BinNode<String> node =
		 * new BinNode<String>("A"); tree.setRoot(node); tree.insertAsLC(node,
		 * "B"); tree.insertAsLC(tree.getRoot().getLeft(), "C");
		 * tree.insertAsRC(tree.getRoot().getLeft(), "D");
		 * 
		 * tree.insertAsLC(tree.getRoot().getLeft().getRight(), "E");
		 * tree.insertAsRC(tree.getRoot().getLeft().getRight(), "G");
		 * tree.insertAsRC(tree.getRoot().getLeft().getRight().getLeft(), "F");
		 * 
		 * System.out.println("前序遍历"); tree.travPre(tree.getRoot());
		 * System.out.println(); tree.travPre2(tree.getRoot());
		 * System.out.println(); tree.travPre3(tree.getRoot());
		 * System.out.println("\n中序遍历"); tree.intrav(tree.getRoot());
		 * System.out.println(); tree.intrav2(tree.getRoot());
		 * System.out.println("\n后序遍历"); tree.travPost(tree.getRoot());
		 * System.out.println(); tree.travPost2(tree.getRoot());
		 * System.out.println("\n层次遍历"); tree.travLevel(tree.getRoot());
		 */
		/*
		 * int a[] = { 16, 10, 25, 19, 28, 5, 11, 4, 33, 22, 17, 18 }; // int
		 * a[] = {3,2,4}; BST<Integer> bst = new BST<Integer>(); for (int i = 0;
		 * i < a.length; i++) bst.insert(a[i]); // System.out.println("中序遍历");
		 * // bst.remove(25); // bst.intrav2(bst.getRoot());
		 * 
		 * for (int i = 0; i < a.length; i++) { bst.remove(a[i]);
		 * System.out.print(a[i] + " "); } System.out.println(); if
		 * (bst.getRoot() != null) { bst.travLevel(bst.getRoot()); //
		 * System.out.println(); // bst.intrav2(bst.getRoot()); }
		 */

		/*
		 * AVL<Integer> avl = new AVL<Integer>(); for (int i = 1; i < 20; i++) {
		 * avl.insert(i); //System.out.print(i + " "); } System.out.println();
		 * for (int i = 1; i < 15; i++) { avl.remove(i); //System.out.print(i +
		 * " "); } System.out.println(); if (avl.getRoot() != null) {
		 * avl.travLevel(avl.getRoot()); //avl.intrav(avl.getRoot()); }
		 */

		/*
		 * SplayTree<Integer> splay = new SplayTree<Integer>(); for (int i = 1;
		 * i < 20; i++) { splay.insert(i); } // splay.searchSplay(19);
		 * splay.insertSplay(20); splay.removeSplay(18); if (splay.getRoot() !=
		 * null) { splay.travLevel(splay.getRoot()); }
		 */

/*		BTree<Integer> tree = new BTree<Integer>(6);
		for (int i = 1; i < 30; i++)
			tree.insert(i);
		//tree.remove(8);
//		tree.remove(9);
//		tree.remove(10);
//		tree.remove(7);
//		tree.remove(13);
		for (int i = 1; i < 10; i++)
			//tree.remove(i);
			;
		//BTNode<Integer> x = tree.search(28);
		TreeMap map = new TreeMap();
		tree.insert(30);
		tree.printBtree();*/
		//System.out.println(Arrays.toString(x.getKey().toArray()));
		//tree.printBtree();
		RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		for (int i = 1; i < 20; i++)
			tree.insert(i);
		for (int i = 1; i < 15; i++)
			tree.remove(i);
		tree.travLevel(tree.getRoot());
	}
}
