package com.lhs.btree;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * LogManager
 *
 * @author longhuashen
 * @since 2022-01-17
 */
public class LogManager {

    private static final LogManager logManager = new LogManager();

    private static FileChannel fileChannel = null;

    private LogManager() {

    }

    public static LogManager getInstance() {
        return logManager;
    }

    public long write(Loggable log) throws IOException {
        int logType = log.getLogType();
        int logSize = log.getLogSize();
        LogHeader logHeader = new LogHeader(logType, logSize);
        ByteBuffer buffer = ByteBuffer.allocate(logHeader.getLogContentSize() + logSize);

        logHeader.writeIntoBuffer(buffer);
        log.writeIntoBuffer(buffer);
        buffer.flip();

        long lsn = fileChannel.size();
        return lsn;
    }

    public void read(long lsn) throws IOException {
        LogHeader logHeader = new LogHeader();
        ByteBuffer headBuffer = ByteBuffer.allocate(logHeader.getLogSize());
        fileChannel.position(lsn);
        fileChannel.read(headBuffer);
        headBuffer.flip();
        logHeader.readFromBuffer(headBuffer);


    }
}
