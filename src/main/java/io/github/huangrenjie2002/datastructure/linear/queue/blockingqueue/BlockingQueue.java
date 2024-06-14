package io.github.huangrenjie2002.datastructure.linear.queue.blockingqueue;

public interface BlockingQueue<E> {

    void offer(E e) throws InterruptedException;

    /**
     *
     * @param e E
     * @param timeout MILLISECONDS
     * @return boolean
     * @throws InterruptedException
     */
    boolean offer(E e, long timeout) throws InterruptedException;

    E poll() throws InterruptedException;
}
