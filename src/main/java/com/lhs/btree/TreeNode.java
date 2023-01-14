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

   private int count = 0;

   private static final int MAX_CHILDREN = 4;

   private DataItem[] keyList = new DataItem[MAX_CHILDREN];

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

   @Override
   public int getLogSize() {
      int logSize = Integer.BYTES;

      for (int i = 0; i < count; i++) {
         logSize += Integer.BYTES;
         if (keyList[i] != DataItem.MAX_VALUE) {
            logSize += keyList[i].getData().length;
         }
      }

      logSize += (count * Long.BYTES);
      return logSize;
   }
}
