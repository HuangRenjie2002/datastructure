package io.github.huangrenjie2002.datastructure.linear.queue.priority;

import io.github.huangrenjie2002.datastructure.linear.queue.Queue;

public class UnorderedArrayPriorityQueue<E extends Priority> implements Queue<E> {

    private Priority[] array;
    private int size;

    public UnorderedArrayPriorityQueue(int capacity) {
        array = new Priority[capacity];
    }

    @Override
    public boolean offer(E e) {
        if (isFull())
            return false;
        array[size++] = e;
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public E poll() {
        if (isEmpty())
            return null;
        int max = selectMax();
        E e = (E) array[max];
        remove(max);
        return e;
    }

    @Override
    @SuppressWarnings("all")
    public E peek() {
        if (isEmpty())
            return null;
        return (E) array[selectMax()];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }

    private int selectMax() {
        int max = 0;
        for (int i = 0; i < size; i++)
            if (array[i].priority() > array[max].priority())
                max = i;
        return max;
    }

    private void remove(int index) {
        if (index < size - 1)
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
    }
}
