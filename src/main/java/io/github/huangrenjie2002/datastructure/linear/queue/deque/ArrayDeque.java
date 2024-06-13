package io.github.huangrenjie2002.datastructure.linear.queue.deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E>, Iterable<E> {

    private E[] array;
    private int head;
    private int tail;

    @SuppressWarnings("all")
    public ArrayDeque(int capacity) {
        array = (E[]) new Object[capacity + 1];
    }


    @Override
    public boolean offerFirst(E e) {
        if (isFull())
            return false;
        head = dec(head, array.length);
        array[head] = e;
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if (isFull())
            return false;
        array[tail] = e;
        tail = inc(tail, array.length);
        return true;
    }

    @Override
    public E pollFirst() {
        if (isEmpty())
            return null;
        E e = array[head];
        array[head] = null;
        head = inc(head, array.length);
        return e;
    }

    @Override
    public E pollLast() {
        if (isEmpty())
            return null;
        tail = dec(tail, array.length);
        E e = array[tail];
        array[tail] = null;
        return e;
    }

    @Override
    public E peekFirst() {
        if (isEmpty())
            return null;
        return array[head];
    }

    @Override
    public E peekLast() {
        if (isEmpty())
            return null;
        return array[dec(tail, array.length)];
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean isFull() {
        if (head == tail)
            return false;
        else
            return head > tail ? head - tail == 1 : tail - head == array.length - 1;
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
                E e = array[p];
                p = inc(p, array.length);
                return e;
            }
        };
    }

    static int inc(int i, int length) {
        return i + 1 >= length ? 0 : i + 1;
    }

    static int dec(int i, int length) {
        return i - 1 < 0 ? length - 1 : i - 1;
    }
}
