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

    public Node read(long lsn) throws IOException {
        LogHeader logHeader = new LogHeader();
        ByteBuffer headBuffer = ByteBuffer.allocate(logHeader.getLogSize());
        fileChannel.position(lsn);
        fileChannel.read(headBuffer);
        headBuffer.flip();
        logHeader.readFromBuffer(headBuffer);

        ByteBuffer buffer = ByteBuffer.allocate(logHeader.getLogContentSize());
        fileChannel.read(buffer);
        buffer.flip();

        Node node;
        if (logHeader.getContentType() == LogHeader.VALUE_NODE_LOG) {
            node = new ValueNode();
        } else if (logHeader.getContentType() == LogHeader.LEAF_NODE_LOG) {
            node = new LeafNode();
        } else if (logHeader.getContentType() == LogHeader.INTER_NODE_LOG) {
            node = new InterNode();
        } else {
            throw new RuntimeException("Invalid Log Type " + logHeader.getContentType());
        }
        node.readFromBuffer(buffer);
        return node;
    }

    public long writeRootLSN(long rootLSN) throws IOException {
        int logType = LogHeader.ROOT_LSN_LOG;
        int logSize = Long.BYTES;

        LogHeader logHeader = new LogHeader(logType, logSize);
        ByteBuffer buffer = ByteBuffer.allocate(logHeader.getLogSize() + logSize);

        logHeader.writeIntoBuffer(buffer);
        buffer.putLong(rootLSN);
        buffer.flip();
        long lsn = fileChannel.size();
        fileChannel.position(lsn);
        fileChannel.write(buffer);
        return lsn;
    }
}
