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

    public static final int ROOT_LSN_LOG = 3;

    private int logContentType;
    private int logContentSize;

    private int logSize;

    private int contentType;

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

    public int getLogSize() {
        return logSize;
    }

    public int getContentType() {
        return contentType;
    }

    public void writeIntoBuffer(ByteBuffer byteBuffer) {
        byteBuffer.putInt(logContentType);
        byteBuffer.putInt(logContentSize);
    }

    public void readFromBuffer(ByteBuffer buffer) {
        logContentType = buffer.getInt();
        logContentSize = buffer.getInt();
    }
}
