package com.lhs.btree;

import java.util.Arrays;

/**
 * BTree
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public class BTree {

    private TreeNode root;

    public BTree() {
        this.root = null;
    }

    /**
     * 查询key对应的value；如果没有key存在，返回null
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        DataItem searchKey = new DataItem(key);

        if (root == null) {
            return null;
        }

        TreeNode treeNode = root;
        while (treeNode instanceof InterNode) {
            treeNode = (TreeNode) treeNode.findChild(searchKey);
            if (treeNode == null) {
                return null;
            }
        }

        ValueNode valueNode = (ValueNode) treeNode.findChild(searchKey);
        if (valueNode == null) {
            return null;
        }

        byte[] value = valueNode.getValue().getData();
        return Arrays.copyOf(value, value.length);
    }

    /**
     * 插入 key/value 对
     *
     * @param key
     * @param value
     */
    public void put(byte[] key, byte[] value) {
        DataItem insertKey = new DataItem(key);
        DataItem insertValue = new DataItem(value);

        if (root == null) {
            root = new LeafNode();
        }

        if (root.needSplit()) {
            InterNode newRoot = new InterNode();
            newRoot.insertChild(DataItem.MAX_VALUE, root);
            root.split(newRoot);
            root = newRoot;
        }

        TreeNode treeNode = root;
        while (treeNode instanceof InterNode) {
            TreeNode child = (TreeNode) treeNode.findChild(insertKey);
            if (child.needSplit()) {
                child.split(treeNode);
            } else {
                treeNode = child;
            }
            ValueNode valueNode = new ValueNode(insertValue);
            treeNode.insertChild(insertKey, valueNode);
        }
    }

    /**
     * 删除key对应的value；如果没有key存在，返回null
     *
     * @param key
     * @return
     */
    public byte[] delete(byte[] key) {
        DataItem deleteKey = new DataItem(key);

        if (root == null) {
            root = new LeafNode();
        }

        TreeNode treeNode = root;
        while (treeNode instanceof InterNode) {
            treeNode = (TreeNode) treeNode.findChild(deleteKey);
            if (treeNode == null) {
                return null;
            }
        }

        ValueNode valueNode = (ValueNode) treeNode.removeChild(deleteKey);
        if (valueNode == null) {
            return null;
        }

        byte[] value = valueNode.getValue().getData();
        if (value == null) {
            return null;
        }

        return Arrays.copyOf(value, value.length);
    }
}
