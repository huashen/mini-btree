package com.lhs.btree;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TreeNode
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public abstract class TreeNode extends Node {

   /**
    * 最大子节点数目
    */
   protected final ReentrantReadWriteLock locker = new ReentrantReadWriteLock();

   public abstract Node findChild(DataItem key);

   public abstract void insertChild(DataItem key, Node child);

   public abstract Node removeChild(DataItem key);

   public abstract TreeNode split(TreeNode father);

   public abstract boolean needSplit();

   public void writeLock() {
      locker.writeLock().lock();
   }

   public void readLock() {
      locker.readLock().lock();
   }

   public void releaseLock() {
      if (locker.writeLock().isHeldByCurrentThread()) {
         locker.writeLock().unlock();
         return;
      }

      locker.readLock().unlock();
   }
}
