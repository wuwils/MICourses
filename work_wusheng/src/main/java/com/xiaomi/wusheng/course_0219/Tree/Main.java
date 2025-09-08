package com.xiaomi.wusheng.course_0219.Tree;

public class Main {
    public static void main(String[] args) {
        // 创建树节点
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        // 创建树对象并进行遍历
        Tree tree = new Tree(root);

        tree.preOrderTraversal();   // 输出：Pre-order: 1 2 4 5 3 6 7
        tree.inOrderTraversal();    // 输出：In-order: 4 2 5 1 6 3 7
        tree.postOrderTraversal();  // 输出：Post-order: 4 5 2 6 7 3 1
    }
}
