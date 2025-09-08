package com.xiaomi.wusheng.course_0219.Tree;

class Tree {
    private TreeNode root;

    public Tree(TreeNode root) {
        this.root = root;
    }

    // 前序遍历
    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // 中序遍历
    public void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    // 后序遍历
    public void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + " ");
    }

    // 打印前序遍历结果
    public void preOrderTraversal() {
        System.out.print("Pre-order: ");
        preOrder(root);
        System.out.println();
    }

    // 打印中序遍历结果
    public void inOrderTraversal() {
        System.out.print("In-order: ");
        inOrder(root);
        System.out.println();
    }

    // 打印后序遍历结果
    public void postOrderTraversal() {
        System.out.print("Post-order: ");
        postOrder(root);
        System.out.println();
    }
}
