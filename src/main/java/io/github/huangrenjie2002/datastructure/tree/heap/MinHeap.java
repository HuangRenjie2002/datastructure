package io.github.huangrenjie2002.datastructure.tree.heap;

public class MinHeap<E extends Comparable<E>> {
    E[] array;
    int size;

    @SuppressWarnings("all")
    public MinHeap(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    public MinHeap(E[] array) {
        this.array = array;
        this.size = array.length;
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

    public boolean offer(E offered) {
        if (isFull())
            return false;
        up(offered);
        size++;
        return true;
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
            if (offered.compareTo(array[parent]) < 0)
                array[child] = array[parent];
            else
                break;
            child = parent;
        }
        array[child] = offered;
    }


    private void down(int parent) {
        int left = 2 * parent + 1, right = left + 1, min = parent;
        if (left < size && array[left].compareTo(array[min]) < 0)
            min = left;
        if (right < size && array[right].compareTo(array[min]) < 0)
            min = right;
        if (min != parent) {
            swap(parent, min);
            down(min);
        }
    }

    private void swap(int i, int j) {
        E t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
