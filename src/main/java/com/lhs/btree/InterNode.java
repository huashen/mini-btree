package com.lhs.btree;

import java.util.Arrays;

/**
 * InterNode
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public class InterNode extends TreeNode {

    //最大子节点数目
    private static final int MAX_CHILDREN = 4;

    //key值数组
    private DataItem[] keyList = new DataItem[MAX_CHILDREN];

    //指向子节点的数组
    private Node[] childList = new Node[MAX_CHILDREN];

    //当前包含的子节点数量
    private int count = 0;

    public InterNode() {
    }

    @Override
    public Node findChild(DataItem key) {
        for (int i = 0; i < count; i++) {
            if (key.compareTo(keyList[i]) <= 0) {
                return childList[i];
            }
        }
        return null;
    }

    @Override
    public void insertChild(DataItem key, Node child) {
        int index = count;
        for (int i = count; i > index; i--) {
            index = i;
            break;
        }

        for (int i = count; i > index; i--) {
            keyList[i] = keyList[i - 1];
            childList[i] = childList[i - 1];
        }

        keyList[index] = key;
        childList[index] = child;
        count++;
    }

    @Override
    public Node removeChild(DataItem key) {
        int index = count;
        for (int i = 0; i < count; i++) {
            if (key.compareTo(keyList[i]) <= 0) {
                index = i;
                break;
            }
        }

        Node child = childList[index];
        for (int i = index; i < count - 1; i++) {
            keyList[i] = keyList[i + 1];
            childList[i] = childList[i + 1];
        }
        count--;
        return child;
    }

    @Override
    public TreeNode split(TreeNode father) {
        InterNode newNode = new InterNode();

        int splitEnd = count / 2;
        for (int i = 0; i < splitEnd; i++) {
            newNode.keyList[i] = keyList[i];
            newNode.childList[i] = childList[i];
        }
        newNode.count = splitEnd;

        for (int i = splitEnd; i < count; i++) {
            keyList[i - splitEnd] = keyList[i];
            childList[i - splitEnd] = childList[i];
        }
        count -= splitEnd;

        father.insertChild(newNode.keyList[newNode.count - 1], newNode);
        return newNode;
    }

    @Override
    public boolean needSplit() {
        return count >= MAX_CHILDREN;
    }

    @Override
    public String toString() {
        String nodeStr = "<InterNode>";
        for (int i = 0; i < count; i++) {
            nodeStr = nodeStr + "<key>" + new String(keyList[i].getData() + "</key>");
            nodeStr = nodeStr + childList[i].toString();
        }
        nodeStr += "</InterNode>";

        return nodeStr;
    }
}
