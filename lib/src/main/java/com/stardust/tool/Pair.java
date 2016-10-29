package com.stardust.tool;

/**
 * Created by Stardust on 2016/5/11.
 * <p/>
 * 一个很简单的有序对类
 * 和C++的Pair差不多吧
 */
public class Pair<TypeA, TypeB> {
    /**
     * 第一个类型的变量
     */
    public TypeA first;
    /**
     * 第二个可续的变量
     */
    public TypeB second;

    /**
     * @param first  可以为NULL
     * @param second 可以为NULL
     */
    public Pair(TypeA first, TypeB second) {
        this.first = first;
        this.second = second;
    }
}
