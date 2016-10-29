package com.stardust.jnitool;

/**
 * Created by Stardust on 2016/8/4.
 */
public abstract class Pointer {

    public abstract void setAddress(long address);

    public abstract void setAddress(int address);

    public abstract int getAddress32();

    public abstract long getAddress64();

}
