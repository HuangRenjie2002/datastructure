package io.github.huangrenjie2002.datastructure.linear.linkedlist;

import java.util.Iterator;

public class DoublyCircularLinkedListSentinel implements Iterable<Integer>{

    private static class Node{
        Node prev;
        int val;
        Node next;

        public Node(Node prev, int val, Node next) {
            this.prev = prev;
            this.val = val;
            this.next = next;
        }
    }

    private Node sentinel = new Node(null,-1,null);

    public DoublyCircularLinkedListSentinel() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(int val) {
        sentinel.next = new Node(sentinel,val,sentinel.next);
        sentinel.next.prev = sentinel.next;
    }

    public void addLast(int val) {
        sentinel.prev.next = new Node(sentinel.prev,val,sentinel);
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

    public void removeByValue(int val) {
        Node removed = findByValue(val);
        if (removed == null)
            return;
        removed.prev.next = removed.next;
        removed.next.prev = removed.prev;
    }

    private Node findByValue(int val) {
        Node p = sentinel.next;
        while (p!=sentinel){
            if (p.val == val)
                return p;
            p = p.next;
        }
        return null;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node p = sentinel.next;
            @Override
            public boolean hasNext() {
                return p != sentinel;
            }

            @Override
            public Integer next() {
                int val = p.val;
                p = p.next;
                return val;
            }
        };
    }
}
