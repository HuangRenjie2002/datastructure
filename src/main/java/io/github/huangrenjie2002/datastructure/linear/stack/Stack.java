package io.github.huangrenjie2002.datastructure.linear.stack;

public interface Stack<E> {

    boolean push(E val);

    E pop();

    E peek();

    boolean isEmpty();

    boolean isFull();
}
