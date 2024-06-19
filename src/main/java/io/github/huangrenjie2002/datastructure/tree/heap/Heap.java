package io.github.huangrenjie2002.datastructure.tree.heap;

public class Heap<E extends Comparable<E>> {
    E[] array;
    int size;
    boolean max;

    @SuppressWarnings("all")
    public Heap(int capacity, boolean max) {
        this.array = (E[]) new Object[capacity];
        this.max = max;
    }

    public Heap(E[] array, boolean max) {
        this.array = array;
        this.size = array.length;
        this.max = max;
        heapify();
    }

    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            down(i);
        }
    }

    public E poll() {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        E top = array[0];
        swap(0, size - 1);
        size--;
        down(0);
        return top;
    }

    public E poll(int index) {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        E deleted = array[index];
        swap(index, size - 1);
        size--;
        down(index);
        return deleted;
    }

    public void replaceTop(E replaced) {
        array[0] = replaced;
        down(0);
    }

    public E peek() {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        return array[0];
    }

    public void offer(E offered) {
        if (isFull())
            grow();
        up(offered);
        size++;
    }

    @SuppressWarnings("all")
    private void grow() {
        int capacity = size + (size >> 1);
        E[] newArray = (E[]) new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }

    private void up(E offered) {
        int child = size;
        while (child > 0) {
            int parent = (child - 1) / 2;
            if (max ? offered.compareTo(array[parent]) > 0 : offered.compareTo(array[parent]) < 0)
                array[child] = array[parent];
            else
                break;
            child = parent;
        }
        array[child] = offered;
    }


    private void down(int parent) {
        int left = 2 * parent + 1, right = left + 1, maxOrMin = parent;
        if (left < size && max ? array[left].compareTo(array[maxOrMin]) > 0 : array[left].compareTo(array[maxOrMin]) < 0)
            maxOrMin = left;
        if (right < size && max ? array[right].compareTo(array[maxOrMin]) > 0 : array[right].compareTo(array[maxOrMin]) < 0)
            maxOrMin = right;
        if (maxOrMin != parent) {
            swap(parent, maxOrMin);
            down(maxOrMin);
        }
    }

    private void swap(int i, int j) {
        E t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
