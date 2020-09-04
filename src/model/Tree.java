package model;

import java.util.List;

public interface Tree<T extends Comparable<T>> {

	public boolean add(T data);

	public boolean search(T data);

	public T delete(T data);

	public int height();

	public int weight();

	public List<T> preorder();

	public List<T> inorder();

	public List<T> postorder();

}
