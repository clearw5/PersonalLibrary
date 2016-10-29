package com.stardust.jnitool;

/**
 * Created by Stardust on 2016/8/4.
 */
public class Pointer64 extends Pointer {

    private long mAddress;

    public Pointer64() {
    }

    public Pointer64(long address) {
        setAddress(address);
    }

    public void setAddress(long address) {
        this.mAddress = address;
    }

    @Override
    public void setAddress(int address) {
        setAddress(address);
    }

    public long getAddress64() {
        return this.mAddress;
    }

    public int getAddress32() {
        return (int) mAddress;
    }

}
