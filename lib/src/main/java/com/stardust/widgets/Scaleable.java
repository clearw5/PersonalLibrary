package com.stardust.widgets;

/**
 * Created by Stardust on 2016/8/9.
 */
public interface Scaleable {

    void scale(float scaleFactor, float pivotX, float pivotY);

    void restore();

}
