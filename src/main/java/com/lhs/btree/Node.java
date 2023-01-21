package com.lhs.btree;

import java.nio.ByteBuffer;

/**
 * Node
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public abstract class Node implements Loggable {

    public int getLogSize() {
        return 0;
    }


    void writeChildren() {

    }

    public void readFromBuffer(ByteBuffer buffer) {

    };
}
