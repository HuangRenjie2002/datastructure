package io.github.huangrenjie2002.datastructure.tree.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<K extends Comparable<K>, V> {

    private BSTNode<K, V> root;

    static class BSTNode<K, V> {
        K key;
        V val;
        BSTNode<K, V> left;
        BSTNode<K, V> right;

        public BSTNode(K key, V val, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public BSTNode(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public BSTNode(K key) {
            this.key = key;
        }
    }

    public V get(K key) {
        BSTNode<K, V> node = root;
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

    public V min() {
        return min(root);
    }

    private V min(BSTNode<K, V> node) {
        if (node == null)
            return null;
        BSTNode<K, V> p = node;
        while (p.left != null)
            p = p.left;
        return p.val;
    }

    public V max() {
        return max(root);
    }

    private V max(BSTNode<K, V> node) {
        if (node == null)
            return null;
        BSTNode<K, V> p = node;
        while (p.right != null)
            p = p.right;
        return p.val;
    }

    public void put(K key, V val) {
        BSTNode<K, V> node = root;
        BSTNode<K, V> parent = null;
        while (node != null) {
            parent = node;
            int res = key.compareTo(node.key);
            if (res < 0)
                node = node.left;
            else if (res > 0)
                node = node.right;
            else {
                node.val = val;
                return;
            }
        }
        if (parent == null) {
            root = new BSTNode<>(key, val);
            return;
        }
        if (key.compareTo(parent.key) < 0)
            parent.left = new BSTNode<>(key, val);
        else if (key.compareTo(parent.key) > 0)
            parent.right = new BSTNode<>(key, val);
    }

    public V successor(K key) {
        BSTNode<K, V> p = root;
        BSTNode<K, V> ancestorFromRight = null;
        while (p != null) {
            if (p.key.compareTo(key) < 0) {
                ancestorFromRight = p;
                p = p.left;
            } else if (p.key.compareTo(key) > 0)
                p = p.right;
            else
                break;
        }
        if (p == null)
            return null;
        if (p.left != null)
            return min(p.right);
        return ancestorFromRight != null ? ancestorFromRight.val : null;
    }

    public V predecessor(K key) {
        BSTNode<K, V> p = root;
        BSTNode<K, V> ancestorFromLeft = null;
        while (p != null) {
            if (p.key.compareTo(key) < 0)
                p = p.left;
            else if (p.key.compareTo(key) > 0) {
                ancestorFromLeft = p;
                p = p.right;
            } else
                break;
        }
        if (p == null)
            return null;
        if (p.left != null)
            return max(p.left);
        return ancestorFromLeft != null ? ancestorFromLeft.val : null;
    }

    public V delete(K key) {
        BSTNode<K, V> p = root;
        BSTNode<K, V> parent = null;
        while (p != null) {
            if (p.key.compareTo(key) < 0) {
                parent = p;
                p = p.left;
            } else if (p.key.compareTo(key) > 0) {
                parent = p;
                p = p.right;
            } else
                break;
        }
        if (p == null)
            return null;
        if (p.left == null) {
            shift(parent, p, p.right);
        } else if (p.right == null) {
            shift(parent, p, p.left);
        } else {
            BSTNode<K, V> s = p.right;
            BSTNode<K, V> sParent = p;
            while (s.left != null) {
                sParent = s;
                s = s.left;
            }
            if (sParent != p) {
                shift(sParent, s, s.right);
                s.right = p.right;
            }
            shift(parent, p, s);
            s.left = p.left;
        }
        return p.val;
    }

    private void shift(BSTNode<K, V> parent, BSTNode<K, V> deleted, BSTNode<K, V> child) {
        if (parent == null)
            root = child;
        else if (deleted == parent.left)
            parent.left = child;
        else
            parent.right = child;
    }

    public List<V> less(K key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K, V> p = root;
        LinkedList<BSTNode<K, V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<K, V> pop = stack.pop();
                if (pop.key.compareTo(key) < 0)
                    result.add(pop.val);
                else
                    break;
                p = pop.right;
            }
        }
        return result;
    }

    public List<V> greater(K key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K, V> p = root;
        LinkedList<BSTNode<K, V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.right;
            } else {
                BSTNode<K, V> pop = stack.pop();
                if (pop.key.compareTo(key) > 0)
                    result.add(pop.val);
                else
                    break;
                p = pop.left;
            }
        }
        return result;
    }

    public List<V> between(K key1, K key2) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<K, V> p = root;
        LinkedList<BSTNode<K, V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<K, V> pop = stack.pop();
                if (pop.key.compareTo(key1) > 0 && pop.key.compareTo(key2) < 0)
                    result.add(pop.val);
                else if (pop.key.compareTo(key2) > 0)
                    break;
                p = pop.right;
            }
        }
        return result;
    }
}
