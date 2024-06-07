package io.github.huangrenjie2002.datastructure.linear.linkedlist;

import java.util.Iterator;

public class DoublyCircularLinkedListSentinel<E> implements Iterable<E>{

    private static class Node<E>{
        Node<E> prev;
        E val;
        Node<E> next;

        public Node(Node<E> prev, E val, Node<E> next) {
            this.prev = prev;
            this.val = val;
            this.next = next;
        }
    }

    private final Node<E> sentinel = new Node<>(null,null,null);

    public DoublyCircularLinkedListSentinel() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(E val) {
        sentinel.next = new Node<>(sentinel,val,sentinel.next);
        sentinel.next.prev = sentinel.next;
    }

    public void addLast(E val) {
        sentinel.prev.next = new Node<>(sentinel.prev,val,sentinel);
        sentinel.prev = sentinel.prev.next;
    }

    public void removeFirst() {
        if (sentinel.next == sentinel)
            throw new IllegalStateException(String.format("Index: %d", 0));
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
    }

    public void removeLast() {
        if (sentinel.prev == sentinel)
            throw new IllegalStateException(String.format("Index: %d", 0));
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
    }

    public void removeByValue(E val) {
        Node<E> removed = findByValue(val);
        if (removed == null)
            return;
        removed.prev.next = removed.next;
        removed.next.prev = removed.prev;
    }

    private Node<E> findByValue(E val) {
        Node<E> p = sentinel.next;
        while (p!=sentinel){
            if (p.val == val)
                return p;
            p = p.next;
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = sentinel.next;
            @Override
            public boolean hasNext() {
                return p != sentinel;
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
