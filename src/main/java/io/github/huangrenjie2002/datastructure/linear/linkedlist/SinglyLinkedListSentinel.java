package io.github.huangrenjie2002.datastructure.linear.linkedlist;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 单向链表
 */
public class SinglyLinkedListSentinel<E> implements Iterable<E> {

    private final Node<E> head = new Node<>(null, null);

    /**
     * 节点类
     */
    private static class Node<E> {
        E val;
        Node<E> next;

        public Node(E val, Node<E> next) {
            this.val = val;
            this.next = next;
        }
    }

    public void addFirst(E val) {
        insert(0, val);
    }

    public void addLast(E val) {
        Node<E> last = findLast();
        last.next = new Node<>(val, null);
    }

    public void insert(int index, E val) {
        Node<E> prev = findNode(index - 1);
        if (prev == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        prev.next = new Node<>(val, prev.next);
    }

    public void removeFirst() {
        remove(0);
    }

    public void remove(int index) {
        Node<E> perv = findNode(index - 1);
        if (perv == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        if (perv.next == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        perv.next = perv.next.next;
    }

    /**
     * 根据索引查找
     *
     * @param index 索引
     * @return 节点值
     * @throws IndexOutOfBoundsException
     */
    public E get(int index) {
        Node<E> node = findNode(index);
        if (node == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        return node.val;
    }

    public Node<E> findNode(int index) {
        int i = -1;
        for (Node<E> p = head; p != null; p = p.next, i++)
            if (i == index) return p;
        return null;
    }

    /**
     * 遍历链表
     *
     * @param consumer 要执行的操作
     */
    public void loop(Consumer<E> consumer) {
        for (Node<E> p = head.next; p != null; p = p.next)
            consumer.accept(p.val);
    }

    /*
        public void loop(Consumer<Integer> consumer){
           Node p = head.next;
           while(p != null){
               consumer.accept(p.val);
               p = p.next;
           }
        }
     */


    private Node<E> findLast() {
        Node<E> p = head;
        while (p.next != null)
            p = p.next;
        return p;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != null;
            }

            @Override
            public E next() {
                E val = p.val;
                p = p.next;
                return val;
            }
        };
    }
}
