package io.github.huangrenjie2002.datastructure.linear.queue.blockingqueue;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DoubleBlockingQueue<E> implements BlockingQueue<E> {

    private final E[] array;
    private int head;
    private int tail;
    private AtomicInteger size = new AtomicInteger();

    private ReentrantLock tailLock = new ReentrantLock();
    private ReentrantLock headLock = new ReentrantLock();

    private Condition tailWaits = tailLock.newCondition();
    private Condition headWaits = headLock.newCondition();

    @SuppressWarnings("all")
    public DoubleBlockingQueue(int capacity) {
        array = (E[]) new Object[capacity];
    }

    @Override
    public void offer(E e) throws InterruptedException {
        tailLock.lockInterruptibly();
        int count;
        try {
            while (isFull())
                tailWaits.await();
            array[tail] = e;
            if (++tail == array.length)
                tail = 0;
            count = size.getAndIncrement();
            if (count + 1 < array.length)
                tailWaits.signal();
        } finally {
            tailLock.unlock();
        }
        headLock.lock();
        if (count == 0) {
            try {
                headWaits.signal();
            } finally {
                headLock.unlock();
            }
        }
    }

    @Override
    public boolean offer(E e, long timeout) throws InterruptedException {
        tailLock.lockInterruptibly();
        int count;
        try {
            long t = TimeUnit.MILLISECONDS.toNanos(timeout);
            while (isFull()) {
                if (t <= 0)
                    return false;
                t = tailWaits.awaitNanos(t);
            }
            array[tail] = e;
            if (++tail == array.length)
                tail = 0;
            count = size.getAndIncrement();
            if (count + 1 < array.length)
                tailWaits.signal();
        } finally {
            tailLock.unlock();
        }
        headLock.lock();
        if (count == 0) {
            try {
                headWaits.signal();
            } finally {
                headLock.unlock();
            }
        }
        return true;
    }

    @Override
    public E poll() throws InterruptedException {
        E e;
        int count;
        headLock.lockInterruptibly();
        try {
            while (isEmpty())
                headWaits.await();
            e = array[head];
            array[head] = null;
            if (++head == array.length)
                head = 0;
            count = size.getAndDecrement();
            if (count > 1) {
                headWaits.signal();
            }
        } finally {
            headLock.unlock();
        }
        if (count == array.length) {
            tailLock.lock();
            try {
                tailWaits.signal();
            } finally {
                tailLock.unlock();
            }
        }
        return e;
    }

    private boolean isEmpty() {
        return size.get() == 0;
    }

    private boolean isFull() {
        return size.get() == array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
