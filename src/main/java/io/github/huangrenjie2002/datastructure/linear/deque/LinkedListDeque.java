package io.github.huangrenjie2002.datastructure.linear.deque;

import java.util.Iterator;

public class LinkedListDeque<E> implements Deque<E>, Iterable<E> {

    private static class Node<E> {
        Node<E> prev;
        E val;
        Node<E> next;

        public Node(Node<E> prev, E val, Node<E> next) {
            this.prev = prev;
            this.val = val;
            this.next = next;
        }
    }

    private final int capacity;
    private int size;
    private final Node<E> sentinel = new Node<>(null, null, null);

    public LinkedListDeque(int capacity) {
        this.capacity = capacity;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public boolean offerFirst(E e) {
        if (isFull())
            return false;
        Node<E> added = new Node<>(sentinel, e, sentinel.next);
        sentinel.next.prev = added;
        sentinel.next = added;
        size++;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (isFull())
            return false;
        Node<E> added = new Node<>(sentinel.prev, e, sentinel);
        sentinel.prev.next = added;
        sentinel.prev = added;
        size++;
        return true;
    }

    @Override
    public E pollFirst() {
        if (isEmpty())
            return null;
        Node<E> removed = sentinel.next;
        removed.next.prev = sentinel.next;
        sentinel.next = removed.next;
        size--;
        return removed.val;
    }

    @Override
    public E pollLast() {
        if (isEmpty())
            return null;
        Node<E> removed = sentinel.prev;
        removed.prev.next = sentinel;
        sentinel.prev = removed.prev;
        size--;
        return removed.val;
    }

    @Override
    public E peekFirst() {
        if (isEmpty())
            return null;
        return sentinel.next.val;
    }

    @Override
    public E peekLast() {
        if (isEmpty())
            return null;
        return sentinel.prev.val;
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
        return new Iterator<>() {
            Node<E> p = sentinel.next;

            @Override
            public boolean hasNext() {
                return p != sentinel;
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
