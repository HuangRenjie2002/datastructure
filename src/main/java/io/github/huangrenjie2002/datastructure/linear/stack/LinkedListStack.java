package io.github.huangrenjie2002.datastructure.linear.stack;

import java.util.Iterator;

public class LinkedListStack<E> implements Stack<E> ,Iterable<E>{

    private int capacity = Integer.MAX_VALUE;
    private int size;
    private final Node<E> head = new Node<>(null,null);

    public LinkedListStack(int capacity) {
        this.capacity = capacity;
    }
    public LinkedListStack() {
    }
    static class Node<E> {
        E val;
        Node<E> next;

        public Node(E val, Node<E> next) {
            this.val = val;
            this.next = next;
        }
    }

    @Override
    public boolean push(E val) {
        if (isFull())
            return false;
        head.next = new Node<>(val,head.next);
        size++;
        return true;
    }

    @Override
    public E pop() {
        if (isEmpty())
            return null;
        Node<E> first = head.next;
        head.next = first.next;
        size--;
        return first.val;
    }

    @Override
    public E peek() {
        if (isEmpty())
            return null;
        return head.next.val;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> p = head.next;
            @Override
            public boolean hasNext() {
                return p!=null;
            }

            @Override
            public E next() {
                E val = p.val;
                p = p.next;
                return val;
            }
        };
    }
}
