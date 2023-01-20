package com.lhs.btree;

import java.nio.ByteBuffer;

/**
 * ValueNode
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public class ValueNode extends Node implements Loggable {

    private DataItem value;

    public ValueNode(DataItem value) {
        this.value = value;
    }

    public DataItem getValue() {
        return value;
    }

    @Override
    public String toString() {
        String nodeStr = "<ValueNode>" + new String(value.getData()) + "</ValueNode>";
        return nodeStr;
    }

    @Override
    public void writeIntoBuffer(ByteBuffer buffer) {
        int size = 0;
        if (value != null) {
            size = value.getData().length;
            buffer.putInt(size);
            if (size != 0) {
                buffer.put(value.getData(), 0, size);
            }
        } else {
            buffer.putInt(size);
        }
    }

    @Override
    public int getLogType() {
        return 0;
    }

    @Override
    public int getLogSize() {
        int logSize = Integer.BYTES;
        if (value != null) {
            logSize += value.getData().length;
        }
        return logSize;
    }

    @Override
    public void readFromBuffer(ByteBuffer byteBuffer) {

    }
}
