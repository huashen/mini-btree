package com.lhs.btree;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * BTree
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public class BTree {

    private TreeNode root;

    /**
     * 在BTree中增加一个锁
     */
    private final ReentrantReadWriteLock treeLocker = new ReentrantReadWriteLock();

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
        treeLocker.readLock().lock();

        DataItem searchKey = new DataItem(key);

        if (root == null) {
            treeLocker.readLock().unlock();
            return null;
        }

        TreeNode parent = root;
        parent.readLock();
        while (parent instanceof InterNode) {
            TreeNode child = (TreeNode) parent.findChild(searchKey);
            child.readLock();
            parent.releaseLock();
            parent = child;
        }

        ValueNode valueNode = (ValueNode) parent.findChild(searchKey);
        parent.releaseLock();

        if (valueNode == null) {
            return null;
        }

        byte[] value = valueNode.getValue().getData();
        if (value == null) {
            return null;
        }
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

        treeLocker.writeLock().lock();
        if (root == null) {
            root = new LeafNode();
        }
        root.writeLock();

        if (root.needSplit()) {
            InterNode newRoot = new InterNode();
            newRoot.insertChild(DataItem.MAX_VALUE, root);
            root.split(newRoot);
            root.releaseLock();
            root = newRoot;
            root.writeLock();
        }

        TreeNode parent = root;
        treeLocker.writeLock().lock();
        while (parent instanceof InterNode) {
            TreeNode child = (TreeNode) parent.findChild(insertKey);
            child.writeLock();
            if (child.needSplit()) {
                child.split(parent);
                child.releaseLock();
            } else {
                parent.releaseLock();
                parent = child;
            }
        }
        ValueNode valueNode = new ValueNode(insertValue);
        if (parent.needSplit()) {
            System.out.println("SomeThing wrong");
            if (parent == root) {
                System.out.println("Root is not splitted, werid!!!");
            }
        }
        parent.insertChild(insertKey, valueNode);
        parent.releaseLock();
    }

    /**
     * 删除key对应的value；如果没有key存在，返回null
     *
     * @param key
     * @return
     */
    public byte[] delete(byte[] key) {
        DataItem deleteKey = new DataItem(key);

        treeLocker.readLock().lock();
        if (root == null) {
            treeLocker.readLock().unlock();
            return null;
        }

        TreeNode parent = root;
        if (parent instanceof LeafNode) {
            parent.writeLock();
        } else {
            parent.readLock();
        }
        treeLocker.readLock().unlock();


        while (parent instanceof InterNode) {
            TreeNode child = (TreeNode) parent.findChild(deleteKey);
            if (child instanceof LeafNode) {
                child.writeLock();
            } else {
                child.readLock();
            }
            parent.releaseLock();
            parent = child;
        }

        ValueNode valueNode = (ValueNode) parent.removeChild(deleteKey);
        parent.releaseLock();

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
