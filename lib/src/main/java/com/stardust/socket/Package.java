package com.stardust.socket;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Stardust on 2016/10/15.
 */

public class Package {

    private List<Integer> mRequestTypes;
    private byte[] mData;
    private byte[] mBytes;
    private int mOffset;

    public Package(byte[] data, int pos, int len, int... requestTypes) {
        mData = new byte[len];
        System.arraycopy(data, pos, mData, 0, len);
        mRequestTypes = new LinkedList<>();
        for (int requestType : requestTypes) {
            mRequestTypes.add(requestType);
        }
    }


    public Package(byte[] data, int pos, int len) {
        mBytes = new byte[len];
        System.arraycopy(data, pos, mBytes, 0, len);
    }

    public int getRequestType(int requestLevel) {
        if (mRequestTypes != null)
            return mRequestTypes.get(requestLevel);
        mOffset = Math.max(mOffset, requestLevel + 1);
        return mBytes[requestLevel];
    }


    public byte[] getData() {
        if (mData != null)
            return mData;
        if (mOffset == 0)
            return mBytes;
        mData = new byte[mBytes.length - mOffset];
        System.arraycopy(mBytes, mOffset, mData, 0, mData.length);
        return mData;
    }

    public int getLength() {
        return mData == null ? mBytes.length - mOffset : mData.length;
    }
}
