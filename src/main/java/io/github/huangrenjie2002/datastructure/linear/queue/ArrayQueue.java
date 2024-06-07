package io.github.huangrenjie2002.datastructure.linear.queue;

import java.util.Iterator;

public class ArrayQueue<E> implements Queue<E>, Iterable<E> {

    private final E[] array;
    private int head = 0;
    private int tail = 0;

    @SuppressWarnings("all")
    public ArrayQueue(int capacity) {
        array = (E[]) new Object[capacity + 1];
    }

    @Override
    public boolean offer(E val) {
        if (isFull())
            return false;
        array[tail] = val;
        tail = (tail + 1) % array.length;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty())
            return null;
        E val = array[head];
        head = (head + 1) % array.length;
        return val;
    }

    @Override
    public E peek() {
        if (isEmpty())
            return null;
        return array[head];
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean isFull() {
        return (tail + 1) % array.length == head;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            int p = head;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E val = array[p];
                p = (p + 1) % array.length;
                return val;
            }
        };
    }
}
