package io.github.huangrenjie2002.datastructure.tree.binarytree;

import static io.github.huangrenjie2002.datastructure.tree.binarytree.RedBlackTree.Color.BLACK;
import static io.github.huangrenjie2002.datastructure.tree.binarytree.RedBlackTree.Color.RED;

public class RedBlackTree<K extends Comparable<K>, V> {

    enum Color {
        RED, BLACK
    }

    private Node<K, V> root;

    private static class Node<K, V> {
        K key;
        V val;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        Color color = RED;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public Node(K key) {
            this.key = key;
        }

        public Node(K key, Color color) {
            this.key = key;
            this.color = color;
        }

        public Node(K key, Color color, Node<K,V> left, Node<K,V> right) {
            this.key = key;
            this.color = color;
            this.left = left;
            this.right = right;
            if (left != null) {
                left.parent = this;
            }
            if (right != null) {
                right.parent = this;
            }
        }

        private boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        private Node<K, V> uncle() {
            if (parent == null || parent.parent == null)
                return null;
            if (parent.isLeftChild())
                return parent.parent.right;
            else
                return parent.parent.left;
        }

        private Node<K, V> sibling() {
            if (parent == null)
                return null;
            if (this.isLeftChild())
                return parent.right;
            else
                return parent.left;
        }

    }

    private boolean isRed(Node<K, V> node) {
        return node != null && node.color == RED;
    }

    private boolean isBlack(Node<K, V> node) {
        return !isRed(node);
    }

    private void rightRotate(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        Node<K, V> left = node.left;
        Node<K, V> right = left.right;
        if (right != null)
            right.parent = node;
        left.right = node;
        left.parent = parent;
        node.left = right;
        node.parent = left;
        if (parent == null)
            root = left;
        else if (parent.left == node)
            parent.left = left;
        else
            parent.right = left;
    }

    private void leftRotate(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        Node<K, V> right = node.right;
        Node<K, V> left = right.left;
        if (left != null)
            left.parent = node;
        right.left = node;
        right.parent = parent;
        node.right = left;
        node.parent = right;
        if (parent == null)
            root = right;
        else if (parent.right == node)
            parent.right = right;
        else
            parent.left = right;
    }

    public void put(K key, V val) {
        Node<K, V> p = root;
        Node<K, V> parent = null;
        while (p != null) {
            parent = p;
            if (key.compareTo(p.key) < 0)
                p = p.left;
            else if (key.compareTo(p.key) > 0)
                p = p.right;
            else {
                p.val = val;
                return;
            }
        }
        Node<K, V> inserted = new Node<>(key, val);
        if (parent == null)
            root = inserted;
        else if (key.compareTo(parent.key) < 0) {
            parent.left = inserted;
            inserted.parent = parent;
        } else {
            parent.right = inserted;
            inserted.parent = parent;
        }
        fixRedRed(inserted);
    }

    public void remove(K key) {
        Node<K, V> deleted = find(key);
        if (deleted == null)
            return;
        doRemove(deleted);
    }

    private void doRemove(Node<K, V> deleted) {
        Node<K, V> replaced = findReplaced(deleted);
        Node<K, V> parent = deleted.parent;
        if (replaced == null) {
            if (deleted == root)
                root = null;
            else {
                if (isBlack(deleted))
                    fixDoubleBlack(deleted);
                if (deleted.isLeftChild())
                    parent.left = null;
                else
                    parent.right = null;
                deleted.parent = null;
            }
            return;
        }
        if (deleted.left == null || deleted.right == null) {
            if (deleted == root) {
                root.key = replaced.key;
                root.val = replaced.val;
                root.left = root.right = null;
            } else {
                if (deleted.isLeftChild())
                    parent.left = replaced;
                else
                    parent.right = replaced;
                replaced.parent = parent;
                deleted.left = deleted.right = deleted.parent = null;
                if (isBlack(deleted) && isBlack(replaced))
                    fixDoubleBlack(replaced);
                else
                    replaced.color = BLACK;
            }
            return;
        }

        K key = deleted.key;
        deleted.key = replaced.key;
        replaced.key = key;

        V val = deleted.val;
        deleted.val = replaced.val;
        replaced.val = val;
        doRemove(replaced);
    }

    private void fixDoubleBlack(Node<K, V> node) {
        if (node == root)
            return;
        Node<K, V> parent = node.parent;
        Node<K, V> sibling = node.sibling();
        if (isRed(sibling)) {
            if (node.isLeftChild())
                leftRotate(parent);
            else
                rightRotate(parent);
            parent.color = RED;
            sibling.color = BLACK;
            fixDoubleBlack(node);
            return;
        }
        if (sibling != null)
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                sibling.color = RED;
                if (isRed(parent))
                    parent.color = BLACK;
                else
                    fixDoubleBlack(parent);
            } else {
                if (sibling.isLeftChild() && isRed(sibling.left)) {
                    rightRotate(parent);
                    sibling.left.color = BLACK;
                    sibling.color = parent.color;
                } else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    sibling.right.color = parent.color;
                    leftRotate(sibling);
                    rightRotate(parent);
                } else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                } else {
                    leftRotate(parent);
                    sibling.right.color = BLACK;
                    sibling.color = parent.color;
                }
                parent.color = BLACK;
            }
        else
            fixDoubleBlack(parent);
    }

    private Node<K, V> find(K key) {
        Node<K, V> p = root;
        while (p != null) {
            if (key.compareTo(p.key) < 0)
                p = p.left;
            if (key.compareTo(p.key) > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }

    private Node<K, V> findReplaced(Node<K, V> deleted) {
        if (deleted.left == null && deleted.right == null)
            return null;
        if (deleted.left == null)
            return deleted.right;
        if (deleted.right == null)
            return deleted.left;
        Node<K, V> right = deleted.right;
        while (right.left != null)
            right = right.left;
        return right;
    }


    private void fixRedRed(Node<K, V> node) {
        if (node == root) {
            node.color = BLACK;
            return;
        }
        if (isBlack(node.parent))
            return;
        Node<K, V> parent = node.parent;
        Node<K, V> uncle = node.uncle();
        Node<K, V> grandparent = parent.parent;
        if (isRed(uncle)) {
            parent.color = BLACK;
            uncle.color = BLACK;
            grandparent.color = RED;
            fixRedRed(grandparent);
            return;
        }
        if (parent.isLeftChild() && node.isLeftChild()) {
            parent.color = BLACK;
            grandparent.color = RED;
            rightRotate(grandparent);
        } else if (parent.isLeftChild()) {
            leftRotate(parent);
            node.color = BLACK;
            grandparent.color = RED;
            rightRotate(grandparent);
        } else if (!node.isLeftChild()) {
            parent.color = BLACK;
            grandparent.color = RED;
            leftRotate(grandparent);
        } else {
            rightRotate(parent);
            node.color = BLACK;
            grandparent.color = RED;
            leftRotate(grandparent);
        }
    }
    public boolean contains(K key) {
        return find(key) != null;
    }

    public V get(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int res = key.compareTo(node.key);
            if (res < 0)
                node = node.left;
            else if (res > 0)
                node = node.right;
            else
                return node.val;
        }
        return null;
    }

}
