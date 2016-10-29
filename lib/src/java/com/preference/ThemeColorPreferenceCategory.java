package com.stardust.preference;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stardust.calculator.R;
import com.stardust.theme.ThemeColorManager;

/**
 * Created by Stardust on 2016/8/8.
 */
public class ThemeColorPreferenceCategory extends PreferenceCategory {

    private TextView mTitleTextView;
    private int mColor = Color.TRANSPARENT;

    public ThemeColorPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ThemeColorPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ThemeColorPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThemeColorPreferenceCategory(Context context) {
        super(context);
        init();
    }

    private void init() {
        ThemeColorManager.addPreferenceCategory(this);
        setLayoutResource(R.layout.preference_category_widget);
    }

    public void setTitleTextColor(int titleTextColor) {
        mColor = titleTextColor;
        if (mTitleTextView != null)
            mTitleTextView.setTextColor(titleTextColor);
    }

    public View onCreateView(ViewGroup parent){
        View view = super.onCreateView(parent);
        mTitleTextView = (TextView) view.findViewById(android.R.id.title);
        if(mColor!= Color.TRANSPARENT)
            mTitleTextView.setTextColor(mColor);
        return view;
    }
}
