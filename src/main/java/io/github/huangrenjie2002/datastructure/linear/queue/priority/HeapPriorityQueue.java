package io.github.huangrenjie2002.datastructure.linear.queue.priority;

import io.github.huangrenjie2002.datastructure.linear.queue.Queue;

public class HeapPriorityQueue<E extends Priority> implements Queue<E> {

    private Priority[] array;
    private int size;

    public HeapPriorityQueue(int capacity) {
        array = new Priority[capacity];
    }

    @Override
    public boolean offer(E e) {
        if (isFull())
            return false;
        int child = size++, parent = (child - 1) / 2;
        while (child > 0 && e.priority() > array[parent].priority()) {
            array[child] = array[parent];
            child = parent;
            parent = (child - 1) / 2;
        }
        array[child] = e;
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public E poll() {
        if (isEmpty())
            return null;
        swap(0, --size);
        Priority e = array[size];
        array[size] = null;
        down(0);
        return (E) e;
    }

    @Override
    @SuppressWarnings("all")
    public E peek() {
        if (isEmpty())
            return null;
        return (E) array[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }

    private void swap(int i, int j) {
        Priority t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    private void down(int parent) {
        int left = 2 * parent + 1, right = left + 1, max = parent;
        if (left < size && array[left].priority() > array[max].priority()) {
            max = left;
        }
        if (right < size && array[right].priority() > array[max].priority()) {
            max = right;
        }
        if (max != parent) {
            swap(parent, max);
            down(max);
        }
    }
}
