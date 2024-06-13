package io.github.huangrenjie2002.datastructure.linear.queue.priority;

import io.github.huangrenjie2002.datastructure.linear.queue.Queue;

public class OrderedArrayPriorityQueue<E extends Priority> implements Queue<E> {

    private Priority[] array;
    private int size;

    public OrderedArrayPriorityQueue(int capacity) {
        array = new Priority[capacity];
    }

    @Override
    public boolean offer(E e) {
        if (isFull())
            return false;
        insert(e);
        size++;
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public E poll() {
        if (isEmpty())
            return null;
        E e = (E) array[size - 1];
        array[--size] = null;
        return e;
    }

    @Override
    @SuppressWarnings("all")
    public E peek() {
        if (isEmpty())
            return null;
        return (E) array[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }

    private void insert(E e) {
        int i = size - 1;
        while (i >= 0 && array[i].priority() > e.priority()) {
            array[i + 1] = array[i];
            i--;
        }
        array[i + 1] = e;
    }
}
