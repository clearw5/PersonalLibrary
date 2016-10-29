package com.stardust.theme;

import android.content.Context;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ThemeColorRecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by Stardust on 2016/8/15.
 */
public class ThemeColorNavigationView extends NavigationView {

    private static Field PRESENTER_FIELD;

    static {
        try {
            PRESENTER_FIELD = NavigationView.class.getField("mPresenter");
            PRESENTER_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public ThemeColorNavigationView(Context context) {
        super(context);
    }

    public ThemeColorNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemeColorNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        NavigationMenuView view = (NavigationMenuView) getChildAt(0);
        view.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }

    public static class ThemeColorNavigationMenuView extends ThemeColorRecyclerView implements MenuView {

        public ThemeColorNavigationMenuView(Context context) {
            this(context, null);
        }

        public ThemeColorNavigationMenuView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public ThemeColorNavigationMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        }

        @Override
        public void initialize(MenuBuilder menu) {

        }

        @Override
        public int getWindowAnimations() {
            return 0;
        }
    }

}
