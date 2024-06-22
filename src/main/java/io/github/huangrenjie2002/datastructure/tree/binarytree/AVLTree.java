package io.github.huangrenjie2002.datastructure.tree.binarytree;

public class AVLTree<K extends Comparable<K>, V> {

    private AVLNode<K, V> root;

    private static class AVLNode<K, V> {
        K key;
        V val;
        AVLNode<K, V> left;
        AVLNode<K, V> right;
        int height = 1;

        public AVLNode(K key, V val, AVLNode<K, V> left, AVLNode<K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public AVLNode(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public AVLNode(K key) {
            this.key = key;
        }
    }

    private int height(AVLNode<K, V> node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(AVLNode<K, V> node) {
        node.height = Integer.max(height(node.left), height(node.right)) + 1;
    }

    private int bf(AVLNode<K, V> node) {
        return height(node.left) - height(node.right);
    }

    private AVLNode<K, V> rightRotate(AVLNode<K, V> node) {
        AVLNode<K, V> left = node.left;
        AVLNode<K, V> right = left.right;
        left.right = node;
        node.left = right;
        updateHeight(node);
        updateHeight(left);
        return left;
    }

    private AVLNode<K, V> leftRotate(AVLNode<K, V> node) {
        AVLNode<K, V> right = node.right;
        AVLNode<K, V> left = right.left;
        right.left = node;
        node.right = left;
        updateHeight(node);
        updateHeight(right);
        return right;
    }

    private AVLNode<K, V> leftRightRotate(AVLNode<K, V> node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    private AVLNode<K, V> rightLeftRotate(AVLNode<K, V> node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    private AVLNode<K, V> balance(AVLNode<K, V> node) {
        if (node == null)
            return null;
        int bf = bf(node);
        if (bf > 1 && bf(node.left) >= 0)
            return rightRotate(node);
        else if (bf > 1 && bf(node.right) < 0)
            return leftRightRotate(node);
        else if (bf < -1 && bf(node.right) > 0)
            return rightLeftRotate(node);
        else if (bf < -1 && bf(node.right) <= 0)
            return leftRotate(node);
        return node;
    }

    public V get(K key) {
        AVLNode<K, V> node = root;
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

    public void put(K key, V val) {
        root = doPut(root, key, val);
    }

    private AVLNode<K, V> doPut(AVLNode<K, V> node, K key, V val) {
        if (node == null)
            return new AVLNode<>(key, val);
        if (key == node.key) {
            node.val = val;
            return node;
        }
        if (key.compareTo(node.key) < 0)
            node.left = doPut(node.left, key, val);
        else
            node.right = doPut(node.right, key, val);
        updateHeight(node);
        return balance(node);
    }

    public void remove(K key) {
        root = doRemove(root, key);
    }

    private AVLNode<K, V> doRemove(AVLNode<K, V> node, K key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0)
            node.left = doRemove(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = doRemove(node.right, key);
        else {
            if (node.left == null && node.right == null)
                return null;
            else if (node.left == null)
                node = node.right;
            else if (node.right == null)
                node = node.left;
            else {
                AVLNode<K, V> s = node.right;
                while (s.left != null)
                    s = s.left;
                s.right = doRemove(node.right, s.key);
                s.left = node.left;
                node = s;
            }
        }
        updateHeight(node);
        return balance(node);
    }

}
