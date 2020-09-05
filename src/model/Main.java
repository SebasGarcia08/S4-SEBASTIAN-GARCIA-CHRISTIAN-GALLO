package model;

import java.util.function.Consumer;

public class Main {
	
	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>(false);
		bst.addAll(1,2,3,4,5,5,5,6,87,87,65,3,42,45,43,2);
		Consumer<Integer> printer = (e) -> System.out.print(e + " ");
		
		System.out.print("Inorder: ");
		bst.inorder().forEach(printer);
		System.out.print("\n");		

		System.out.print("Preorder: ");
		bst.preorder().forEach(printer); 
		System.out.print("\n");		

		System.out.print("PostOrder: ");
		bst.postorder().forEach(printer); 		
	}
}