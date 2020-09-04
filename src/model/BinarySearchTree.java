package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Implementation of a generic Binary Search Tree 
 * 
 * @author Sebastian Garcia Acosta
 * @param <T>, any class that implements the Comparable interface
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
	/**
	 * This class represents the node of the BST.
	 * 
	 * The wild-card Node<T extends Comparable<? super T>> allows T to be a type
	 * that is a sub-type of some type that implements Comparable
	 * 
	 * @param <T>, any class that implements the Comparable interface
	 */
	@SuppressWarnings("hiding")
	protected class Node<T extends Comparable<? super T>> {

		/** Parent node */
		private Node<T> parent;

		/** Left child */
		private Node<T> left;

		/** Right child */
		private Node<T> right;

		/** T, the data that the Node encapsulates */
		private T data;

		/**
		 * The data that Node encapsulates
		 * 
		 * @param data, an object of a class T that implements Comparable interface
		 */
		public Node(T data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
		}

		/**
		 * Determines whether this is a leaf node
		 * 
		 * @return boolean, true if is leaf node; otherwise, false.
		 */
		public boolean isLeafNode() {
			return (right == null && left == null);
		}

		/**
		 * Determines whether this node has two children
		 * 
		 * @return boolean, true if this node has two children; otherwise, false.
		 */
		public boolean hasTwoChildren() {
			return (right != null && left != null);
		}

		/**
		 * Determines whether this node has only one child
		 * 
		 * @return boolean, true if this node has only one child; otherwise, false.
		 */
		public boolean hasOnlyOneChild() {
			return hasOnlyLeftChild() || hasOnlyRightChild();
		}

		/**
		 * Determines whether this node has only right child
		 * 
		 * @return boolean, true if this node has only right child; otherwise, false.
		 */
		public boolean hasOnlyRightChild() {
			return (right != null && left == null);
		}

		/**
		 * Determines whether this node has only left child
		 * 
		 * @return boolean, true if this node has only left child; otherwise, false.
		 */
		public boolean hasOnlyLeftChild() {
			return (right == null && left != null);
		}

		/**
		 * Determines whether this node is the right node of its parent node
		 * 
		 * @return boolean, true if this is the right node of its parent node or false
		 *         if this is root node or if this is not the right node of its parent
		 *         node
		 */
		public boolean isRightNode() {
			return parent != null && parent.right == this;
		}

		/**
		 * Determines whether this node is the left node of its parent node
		 * 
		 * @return boolean, true if this is the left node of its parent node or false if
		 *         this is root node or if this is not the left node of its parent node
		 */
		public boolean isLeftNode() {
			return parent != null && parent.left == this;
		}

		/**
		 * Get the minimum node out of itself and the descendants of this node.
		 * 
		 * @return Node<T> {@link Node}, the maximum node under this node; if this is
		 *         leaf node, returns itself.
		 */
		public Node<T> getMaximumNode() {
			Node<T> curr = this;
			while (curr.right != null)
				curr = curr.right;
			return curr;
		}

		/**
		 * Get the minimum node out of itself and the descendants of this node.
		 * 
		 * @return Node<T> {@link Node}, the minimum node under this node; if this is
		 *         leaf node, returns itself.
		 */
		public Node<T> getMinimumNode() {
			Node<T> curr = this;
			while (curr.left != null)
				curr = curr.left;
			return curr;
		}

		/**
		 * Deletes itself. <b>pre:</b> This is not the root.
		 * 
		 * @return Node<T> {@link Node} this, the deleted node.
		 */
		public Node<T> delete() {
			if (isLeafNode()) {
				if (this.isLeftNode())
					parent.left = null;
				else
					parent.right = null;
			} else if (hasOnlyOneChild()) {
				Node<T> child = (hasOnlyLeftChild()) ? this.left : this.right;
				if (this.isLeftNode())
					this.parent.left = child;
				else
					this.parent.right = child;
				child.parent = this.parent;
			} else {
				this.data = this.left.getMaximumNode().delete().data;
			}
			return this;
		}

		/**
		 * Compares this node with another
		 * 
		 * @param another, Node<T> {@link Node}
		 * @return true if the data of this node us less than the data of another node
		 *         based on its comparison criterion
		 */
		public boolean isLessThan(Node<T> another) {
			return this.data.compareTo(another.data) < 0;
		}

		/**
		 * Compares this node with another
		 * 
		 * @param another, Node<T> {@link Node}
		 * @return true if the data of this node us grater than the data of another node
		 *         based on its comparison criterion
		 */
		public boolean isGreaterThan(Node<T> another) {
			return this.data.compareTo(another.data) > 0;
		}

		/**
		 * Determines whether this node's data is equal to another's node data.
		 * 
		 * @param another, Node<T> {@link Node}
		 * @return true if the data of this node is equal than the data of another node
		 *         based on its comparison criterion
		 */
		public boolean dataIsEqualTo(Node<T> another) {
			return this.data.compareTo(another.data) == 0;
		}

		@Override
		public String toString() {
			return this.data.toString();
		}
	}

	/** Root node of the tree */
	private Node<T> root;

	/** Number of modes in tree */
	private int numberOfElements;
	
	/** Whether to allow duplicate nodes */
	private boolean allowDuplicates;

	/**
	 * Constructor
	 */
	public BinarySearchTree() {
		this.root = null;
		this.numberOfElements = 0;
		this.allowDuplicates = false;
	}
	
	/**
	 * Constructor
	 */
	public BinarySearchTree(boolean allowDuplicates) {
		this.root = null;
		this.numberOfElements = 0;
		this.allowDuplicates = allowDuplicates;
	}
	 
	/**
	 * Adds all the elements from the collection to the Tree
	 * 
	 * @param collection, an object that implements the Collection interface
	 */
	public void addAll(Collection<T> collection) {
		for (T element : collection)
			add(element);
	}

	/**
	 * Add all the elements of array to the three
	 * 
	 * @param array of elements of type T
	 */
	@SuppressWarnings("unchecked")
	public void addAll(T... array) {
		for (T element : array)
			add(element);
	}

	/**
	 * @param root
	 * @param space
	 */
	public void print2DUtil(Node<T> root, int space) {
		int count = 5;
		// Base case
		if (root == null)
			return;

		// Increase distance between levels
		space += count;

		// Process right child first
		print2DUtil(root.right, space);

		// Print current node after space
		// count
		for (int i = count; i < space; i++)
			System.out.print(" ");
		System.out.print(root.data + "\n");

		// Process left child
		print2DUtil(root.left, space);
	}

	// Wrapper over print2DUtil()
	public void print2D() {
		// Pass initial space count as 0
		print2DUtil(root, 0);
	}

	/**
	 * Searches an element in the tree
	 * 
	 * @param data, T
	 * @return Node<T> {@link Node}, the node that contains the data searched. If
	 *         not found, returns null
	 */
	private Node<T> searchNode(T data) {
		Node<T> nodeFound = root;
		boolean found = false;
		if (root != null) {
			while (!found && nodeFound != null) {
				if (data.compareTo(nodeFound.data) < 0)
					nodeFound = nodeFound.left;
				else if (data.compareTo(nodeFound.data) > 0)
					nodeFound = nodeFound.right;
				else
					found = true;
			}
		}
		if (!found)
			nodeFound = null;
		return nodeFound;
	}

	/**
	 * Determines whether an element is in the tree
	 * 
	 * @param data
	 * @return boolean, true if the element is within the tree; otherwise, false.
	 */
	public boolean search(T data) {
		Node<T> found = searchNode(data);
		return found == null;
	}

	/**
	 * 
	 * @param data
	 */
	public void add(T data) {
		Node<T> newNode = new Node<>(data);
		boolean duplicate = false;
		boolean added = false;

		if (root == null) {
			root = newNode;
		} else {
			Node<T> currentNode = root;
			while (!added && !duplicate) {
				if (newNode.isLessThan(currentNode)) {
					if (currentNode.left == null) {
						newNode.parent = currentNode;
						currentNode.left = newNode;
						added = true;
					} else {
						currentNode = currentNode.left;
					}
				} else if (newNode.isGreaterThan(currentNode)) {
					if (currentNode.right == null) {
						newNode.parent = currentNode;
						currentNode.right = newNode;
						added = true;
					} else {
						currentNode = currentNode.right;
					}
				} else {
					duplicate = true;
				}
			}
		}

		if (!duplicate)
			numberOfElements++;
	}
	
	public Node<T> getMaximum(Node<T> localRoot) {
		Node<T> currentNode = localRoot;
		while (currentNode.right != null)
			currentNode = currentNode.right;
		return currentNode;
	}

	public Node<T> getMinimum(Node<T> localRoot) {
		Node<T> currentNode = localRoot;
		while (currentNode.left != null)
			currentNode = currentNode.left;
		return currentNode;
	}

	public void delete(T data) {
		if (root != null) {
			if (data.compareTo(root.data) == 0) { // If the root is the element that we want to delete
				if (root.isLeafNode())
					root = null;
				else if (root.hasOnlyOneChild())
					root = (root.hasOnlyLeftChild()) ? root.left : root.right;
				else
					root.data = root.left.getMaximumNode().delete().data;
			} else { // If the element to delete is not the root
				Node<T> nodeFound = searchNode(data);
				if (nodeFound != null)
					nodeFound.delete();
			}
			numberOfElements--;
		}
	}

	public T getRootData() {
		return this.root.data;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void reset() {
		this.root = null;
	}

	public List<T> inorder() {
		List<T> list = new ArrayList<>();
		inorder(root, list);
		return list;
	}

	private void inorder(Node<T> root, List<T> list) {
		if (root != null) {
			inorder(root.left, list);
			list.add(root.data);
			inorder(root.right, list);
		}
	}

	public List<T> preorder() {
		List<T> list = new ArrayList<>();
		preorder(root, list);
		return list;
	}

	private void preorder(Node<T> root, List<T> list) {
		if (root != null) {
			list.add(root.data);
			preorder(root.left, list);
			preorder(root.right, list);
		}
	}

	public List<T> postorder() {
		List<T> list = new ArrayList<>();
		postorder(root, list);
		return list;
	}

	private void postorder(Node<T> root, List<T> list) {
		if (root != null) {
			postorder(root.left, list);
			postorder(root.right, list);
			list.add(root.data);
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new InorderIterator();
	}

	/* Iterator */
	private class InorderIterator implements Iterator<T> {
		/** The nodes that are still to be visited. */
		private Stack<Node<T>> stack;

		/** Construct. */
		private InorderIterator() {
			stack = new Stack<Node<T>>();
			pushPathToMin(root);
		}

		/**
		 * Push all the nodes in the path from a given node to the leftmost node in the
		 * subtree.
		 */
		private void pushPathToMin(Node<T> localRoot) {
			Node<T> current = localRoot;
			while (current != null) {
				stack.push(current);
				current = current.left;
			}
		}

		/** Is there another element in this iterator? */
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		/**
		 * The next element in this iterator; Advance the iterator.
		 */
		public T next() {
			Node<T> node = stack.peek();
			stack.pop();
			pushPathToMin(node.right);
			return node.data;
		}

		/** (Don't) remove an element from this iterator. */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
