package io.github.huangrenjie2002.datastructure.linear.queue;

public interface Queue<E> {

    boolean offer(E val);

    /**
     * 返回并移除
     * @return 结果
     */
    E poll();

    /**
     * 返回不移除
     * @return 结果
     */
    E peek();

    boolean isEmpty();

    boolean isFull();
}
