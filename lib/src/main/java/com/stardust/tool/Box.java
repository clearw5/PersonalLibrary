package com.stardust.tool;

/**
 * Created by Stardust on 2016/8/10.
 */
public class Box<T> {

    private T t;

    public Box() {
    }

    public Box(T t) {
        put(t);
    }

    public boolean empty() {
        return t == null;
    }

    public synchronized void put(T t) {
        this.t = t;
    }

    public synchronized T get() {
        return t;
    }
}
