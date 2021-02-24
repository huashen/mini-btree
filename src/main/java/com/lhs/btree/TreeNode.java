package com.lhs.btree;

/**
 * TreeNode
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public abstract class TreeNode extends Node {

   public abstract Node findChild(DataItem key);

   public abstract void insertChild(DataItem key, Node child);

   public abstract Node removeChild(DataItem key);

   public abstract TreeNode split(TreeNode father);

   public abstract boolean needSplit();
}
