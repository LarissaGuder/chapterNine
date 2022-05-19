package main.lists.util;

public interface IList<T> {
    public boolean add(T x);
    public boolean remove(T x);
    public boolean contains(T x);
    public int count();
}