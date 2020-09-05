package model;

public class Main {
	
	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>(false);
		bst.addAll(40, 20, 60, 10, 30, 50, 70, 75);
		
		bst.print2D();
		System.out.println(bst.height());
		System.out.println(bst.weight());		
		
		bst.delete(40);
		bst.delete(30);
		bst.delete(20);
		bst.delete(10);
		bst.print2D();

	}
}