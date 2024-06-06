package io.github.huangrenjie2002.datastructure.linear.linkedlist;

import java.util.Iterator;

public class DoublyLinkedListSentinel<E> implements Iterable<E>{

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

    private Node<E> head;
    private Node<E> tail;


    public DoublyLinkedListSentinel(){
        head = new Node<E>(null,null,null);
        tail = new Node<E>(null,null,null);
        head.next = tail;
        tail.prev = head;
    }

    private Node<E> findNode(int index){
        int i = -1;
        for (Node<E> p = head; p != tail; p = p.next, i++)
            if(i == index) return p;
        return null;
    }

    public void addFirst(E val){
        insert(0,val);
    }

    public void removeFirst(){
        remove(0);
    }
    public void addLast(E val){
        Node<E> last = tail.prev;
        Node<E> added = new Node<>(last, val, tail);
        last.next = added;
        tail.prev = added;
    }
    public void removeLast(){
        if (tail.prev == head)
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
    }

    public void insert(int index, E val){
        Node<E> prev = findNode(index - 1);
        if (prev == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        Node<E> next = prev.next;
        Node<E> inserted = new Node<>(prev, val, next);
        prev.next = inserted;
        next.prev = inserted;
    }

    public void remove(int index){
        Node<E> prev = findNode(index - 1);
        if (prev == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        if (prev.next == tail)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        prev.next = prev.next.next;
        prev.next.prev = prev;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
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
