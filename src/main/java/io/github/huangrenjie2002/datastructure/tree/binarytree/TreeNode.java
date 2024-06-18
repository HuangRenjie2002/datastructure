package io.github.huangrenjie2002.datastructure.tree.binarytree;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val){
        this.val = val;
    }

    public TreeNode(TreeNode left,int val,TreeNode right){
        this.left = left;
        this.val = val;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
