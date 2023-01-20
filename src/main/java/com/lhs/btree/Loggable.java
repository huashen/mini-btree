package com.lhs.btree;

import java.nio.ByteBuffer;

/**
 * 序列化接口
 *
 * @author longhuashen
 * @since 2023-01-13
 */
public interface Loggable {

    void writeIntoBuffer(ByteBuffer byteBuffer);

    int getLogType();

    int getLogSize();

    void readFromBuffer(ByteBuffer byteBuffer);
}
