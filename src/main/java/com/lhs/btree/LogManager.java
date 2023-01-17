package com.lhs.btree;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * LogManager
 *
 * @author longhuashen
 * @since 2022-01-17
 */
public class LogManager {

    public void write(Loggable log) throws IOException {
        int logType = log.getLogType();
        int logSize = log.getLogSize();
        LogHeader logHeader = new LogHeader(logType, logSize);
        ByteBuffer buffer = ByteBuffer.allocate(logHeader.getLogContentSize() + logSize);

        logHeader.writeIntoBuffer(buffer);
        log.writeIntoBuffer(buffer);
        buffer.flip();


    }
}
