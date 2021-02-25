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
        bTree.put("a".getBytes(), "b".getBytes());

        byte[] bytes = bTree.get("a".getBytes());
        Assert.assertArrayEquals("b".getBytes(), bytes);
    }
}
