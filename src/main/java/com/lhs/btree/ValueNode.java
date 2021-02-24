package com.lhs.btree;

/**
 * ValueNode
 *
 * @author longhuashen
 * @since 2021-02-12
 */
public class ValueNode extends Node {

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
}
