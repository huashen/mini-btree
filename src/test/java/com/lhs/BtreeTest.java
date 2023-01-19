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
//        BTree bTree = new BTree();
//        bTree.put("a".getBytes(), "a".getBytes());
//        bTree.put("b".getBytes(), "b".getBytes());
//        bTree.put("c".getBytes(), "c".getBytes());
//        bTree.put("d".getBytes(), "d".getBytes());
//        bTree.put("e".getBytes(), "e".getBytes());
//        bTree.put("ac".getBytes(), "e".getBytes());
//
//        byte[] bytes = bTree.get("a".getBytes());
//        Assert.assertArrayEquals("a".getBytes(), bytes);

        double a = (21600) * (20 - 2.683);
        System.out.println(a);

        double b = (8 - 4.28) * 14800;
        System.out.println(b);

        double c = (15 - 6.32) * 11000;
        System.out.println(c);
        System.out.println(a + b + c);
    }
}
