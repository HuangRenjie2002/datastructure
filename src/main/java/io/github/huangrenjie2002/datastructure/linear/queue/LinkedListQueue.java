package io.github.huangrenjie2002.datastructure.linear.queue;

import java.util.Iterator;

public class LinkedListQueue<E> implements Queue<E>, Iterable<E> {

    private static class Node<E> {
        E val;
        Node<E> next;

        public Node(E val, Node<E> next) {
            this.val = val;
            this.next = next;
        }
    }

    private final Node<E> head = new Node<>(null, null);
    private Node<E> tail = head;
    private int size;
    private int capacity = Integer.MAX_VALUE;

    {
        tail.next = head;
    }

    public LinkedListQueue(int capacity) {
        this.capacity = capacity;
    }

    public LinkedListQueue() {
    }

    @Override
    public boolean offer(E val) {
        if (isFull())
            return false;
        Node<E> added = new Node<>(val, head);
        tail.next = added;
        tail = added;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty())
            return null;
        Node<E> first = head.next;
        head.next = first.next;
        if (first == tail)
            tail = head;
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
        return head == tail;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != head;
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
