package com.lhs.btree;

import java.util.Arrays;

/**
 * DataItem
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public class DataItem implements Comparable<DataItem> {

    public static final DataItem MAX_VALUE = new DataItem();

    private final byte[] data;

    public DataItem() {
        this.data = null;
    }

    public DataItem(byte[] data) {
        if (data != null) {
            this.data = Arrays.copyOf(data, data.length);
        } else {
            this.data = null;
        }
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public int compareTo(DataItem other) {
        if (other == MAX_VALUE) {
            return -1;
        }

        if (other == null || other.data == null) {
            if (data == null) {
                return 0;
            }
            return 1;
        }

        for (int i = 0; i < data.length && i < other.data.length; i++) {
            if (data[i] > other.data[i]) {
                return 1;
            }

            if (data[i] < other.data[i]) {
                return -1;
            }
        }

        if (data.length > other.data.length) {
            return 1;
        }

        if (data.length < other.data.length) {
            return -1;
        }
        return 0;
    }
}
