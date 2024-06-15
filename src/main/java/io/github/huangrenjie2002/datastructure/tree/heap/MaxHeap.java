package io.github.huangrenjie2002.datastructure.tree.heap;

import io.github.huangrenjie2002.datastructure.linear.queue.priority.Priority;

public class MaxHeap {
    int[] array;
    int size;

    public MaxHeap(int capacity) {
        this.array = new int[capacity];
    }

    public MaxHeap(int[] array) {
        this.array = array;
        this.size = array.length;
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

    public void replaceTop(int replaced){
        array[0] = replaced;
        down(0);
    }

    public int peek() {
        if (isEmpty())
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        return array[0];
    }

    public boolean offer(int offered) {
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

    private void up(int offered) {
        int child = size;
        while (child > 0){
            int parent = (child - 1) / 2;
            if (offered>array[parent])
                array[child] =array[parent];
            else
                break;
            child = parent;
        }
        array[child] = offered;
    }


    private void down(int parent) {
        int left = 2 * parent + 1, right = left + 1, max = parent;
        if (left < size && array[left] > array[max])
            max = left;
        if (right < size && array[right] > array[max])
            max = right;
        if (max != parent) {
            swap(parent, max);
            down(max);
        }
    }

    private void swap(int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
