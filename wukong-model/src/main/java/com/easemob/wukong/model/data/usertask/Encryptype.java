package com.easemob.wukong.model.data.usertask;

/**
 * Created by yangbs on 16-10-14.
 */
public enum Encryptype {
    MD5(0), SHA(1);

    private final int value;
    public int getValue() {
        return value;
    }
    private Encryptype(int value) {
        this.value = value;
    }
}
