package com.lhs.btree;

import java.io.IOException;
import java.nio.ByteBuffer;
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

   private Node[] childList = new Node[MAX_CHILDREN];

   private long[] lsnList = new long[MAX_CHILDREN];

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

   @Override
   void writeChildren() {
      for (int i = 0; i < count; i++) {
         try {
            if (childList[i] == null) {
               childList[i].writeChildren();
               lsnList[i] = LogManager.getInstance().write(childList[i]);
               childList[i] = null;
            }
         } catch (IOException ex) {
               ex.printStackTrace();
         }
      }
   }

   @Override
   public void readFromBuffer(ByteBuffer buffer) {
      count = buffer.getInt();
      for (int i = 0; i < count; i++) {
         int size = buffer.getInt();
         if (size == 0) {
            keyList[i] = null;
         } else if(size == -1) {
            keyList[i] = DataItem.MAX_VALUE;
         } else {
            byte[] data = new byte[size];
            buffer.get(data);
            keyList[i] = new DataItem(data);
         }
      }

      for (int i = 0; i < count; i++) {
         lsnList[i] = buffer.getLong();
      }
   }
}
