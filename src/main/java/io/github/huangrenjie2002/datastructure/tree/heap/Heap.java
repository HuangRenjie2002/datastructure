package io.github.huangrenjie2002.datastructure.tree.heap;

public class Heap {
    int[] array;
    int size;
    boolean max;

    public Heap(int capacity, boolean max) {
        this.array = new int[capacity];
        this.max = max;
    }

    public Heap(int[] array, boolean max) {
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

    public int poll() {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        int top = array[0];
        swap(0, size - 1);
        size--;
        down(0);
        return top;
    }

    public int poll(int index) {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        int deleted = array[index];
        swap(index, size - 1);
        size--;
        down(index);
        return deleted;
    }

    public void replaceTop(int replaced) {
        array[0] = replaced;
        down(0);
    }

    public int peek() {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        return array[0];
    }

    public void offer(int offered) {
        if (isFull())
            grow();
        up(offered);
        size++;
    }

    private void grow() {
        int capacity = size + (size >> 1);
        int[] newArray = new int[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }

    private void up(int offered) {
        int child = size;
        while (child > 0) {
            int parent = (child - 1) / 2;
            if (max ? offered > array[parent] : offered < array[parent])
                array[child] = array[parent];
            else
                break;
            child = parent;
        }
        array[child] = offered;
    }


    private void down(int parent) {
        int left = 2 * parent + 1, right = left + 1, maxOrMin = parent;
        if (left < size && max ? array[left] > array[maxOrMin] : array[left] < array[maxOrMin])
            maxOrMin = left;
        if (right < size && max ? array[right] > array[maxOrMin] : array[right] < array[maxOrMin])
            maxOrMin = right;
        if (maxOrMin != parent) {
            swap(parent, maxOrMin);
            down(maxOrMin);
        }
    }

    private void swap(int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
