package com.stardust.view;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 一些典型动画
 */
public class SimpleAnimations {

    /**
     * 隐藏动画。实际上只是将View向上移动自身高度
     *
     * @return
     */
    public static Animation getHideToTopAnimation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(500);

        return mHiddenAction;
    }

    /**
     * 显示动画。实际上只是将View向下移动自身高度
     *
     * @return
     */
    public static Animation getShowFromTopAnimation() {
        TranslateAnimation mShowAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        return mShowAction;

    }

    /**
     * 隐藏动画。实际上只是将View向上移动自身高度
     *
     * @return
     */
    public static Animation getMoveUpAnimation(long duration) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(duration);

        return mHiddenAction;
    }

    public static Animation getHideToBottomAnimation(long duration){
        return null;
    }

    /**
     * 显示动画。实际上只是将View向下移动自身高度
     *
     * @return
     */
    public static Animation getMoveDownAnimation(long duration) {
        TranslateAnimation mShowAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(duration);
        return mShowAction;

    }

    /**
     * 中心旋转动画，让View以自己的中心为中心旋转一周
     *
     * @return
     */
    public static Animation getCenterRotationAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 359f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        return rotateAnimation;
    }
}
