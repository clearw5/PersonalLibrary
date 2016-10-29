package com.stardust.jnitool;

/**
 * Created by Stardust on 2016/8/4.
 */
public class Pointer32 extends Pointer {

    private int mAddress;


    public Pointer32(int address) {
        setAddress(address);
    }

    @Override
    public void setAddress(long address) {
        setAddress((int) address);
    }

    public void setAddress(int address) {
        this.mAddress = address;
    }

    @Override
    public int getAddress32() {
        return mAddress;
    }

    @Override
    public long getAddress64() {
        return mAddress;
    }

}
