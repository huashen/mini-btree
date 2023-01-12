package com.lhs.btree;

import java.nio.ByteBuffer;

/**
 * LogHeader
 *
 * @author longhuashen
 * @since 2022-01-12
 */
public class LogHeader {

    public static final int VALUE_NODE_LOG = 0;

    public static final int LEAF_NODE_LOG = 1;

    public static final int INTER_NODE_LOG = 2;

    private int logContentType;
    private int logContentSize;

    public LogHeader() {
    }

    public LogHeader(int logContentType, int logContentSize) {
        this.logContentType = logContentType;
        this.logContentSize = logContentSize;
    }

    public int getLogContentType() {
        return logContentType;
    }

    public int getLogContentSize() {
        return logContentSize;
    }

    public void writeIntoBuffer(ByteBuffer byteBuffer) {
        byteBuffer.putInt(logContentType);
        byteBuffer.putInt(logContentSize);
    }
}