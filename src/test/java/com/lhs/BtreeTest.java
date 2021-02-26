package com.lhs;

import com.lhs.btree.BTree;
import org.junit.Assert;
import org.junit.Test;

/**
 * com.lhs.BtreeTest
 *
 * @author longhuashen
 * @since 2021-02-19
 */
public class BtreeTest {

    @Test
    public void testPut() {
        BTree bTree = new BTree();
        bTree.put("a".getBytes(), "a".getBytes());
        bTree.put("b".getBytes(), "b".getBytes());
        bTree.put("c".getBytes(), "c".getBytes());
        bTree.put("d".getBytes(), "d".getBytes());
        bTree.put("e".getBytes(), "e".getBytes());
        bTree.put("ac".getBytes(), "e".getBytes());

        byte[] bytes = bTree.get("a".getBytes());
        Assert.assertArrayEquals("a".getBytes(), bytes);
    }
}
