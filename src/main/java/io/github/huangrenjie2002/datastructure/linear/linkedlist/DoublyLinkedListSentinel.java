package io.github.huangrenjie2002.datastructure.linear.linkedlist;

import java.util.Iterator;

public class DoublyLinkedListSentinel implements Iterable<Integer>{

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

    private Node head;
    private Node tail;


    public DoublyLinkedListSentinel(){
        head = new Node(null,-1,null);
        tail = new Node(null,-2,null);
        head.next = tail;
        tail.prev = head;
    }

    private Node findNode(int index){
        int i = -1;
        for (Node p = head; p != tail; p = p.next, i++)
            if(i == index) return p;
        return null;
    }

    public void addFirst(int val){
        insert(0,val);
    }

    public void removeFirst(){
        remove(0);
    }
    public void addLast(int val){
        Node last = tail.prev;
        Node added = new Node(last, val, tail);
        last.next = added;
        tail.prev = added;
    }
    public void removeLast(){
        if (tail.prev == head)
            throw new IndexOutOfBoundsException(String.format("Index: %d", 0));
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
    }

    public void insert(int index, int val){
        Node prev = findNode(index - 1);
        if (prev == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        Node next = prev.next;
        Node inserted = new Node(prev, val, next);
        prev.next = inserted;
        next.prev = inserted;
    }

    public void remove(int index){
        Node prev = findNode(index - 1);
        if (prev == null)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        if (prev.next == tail)
            throw new IndexOutOfBoundsException(String.format("Index: %d", index));
        prev.next = prev.next.next;
        prev.next.prev = prev;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node p = head.next;
            @Override
            public boolean hasNext() {
                return p != tail;
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
