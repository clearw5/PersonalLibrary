package com.stardust.function;

/**
 * Created by Stardust on 2016/11/2.
 */

@FunctionalInterface
public interface lambda<ReturnType> {

    ReturnType call(Object... args);

}
