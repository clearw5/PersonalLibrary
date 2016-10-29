package com.stardust.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.stardust.lib.R;


/**
 * Created by Stardust on 2016/7/14.
 */
public class SimpleFolderLayout extends LinearLayout {
    private View folderContent;
    private boolean hasInit = false;

    public SimpleFolderLayout(Context context, View buttonContent, View folderContent) {
        super(context);
        addView(buttonContent);
        addView(folderContent);
    }

    public SimpleFolderLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void init() {
        int i = this.getChildCount();
        if (getChildCount() != 2)
            throw new IllegalArgumentException();
        setOrientation(LinearLayout.VERTICAL);
        View buttonContent = getChildAt(0);
        folderContent = getChildAt(1);
        removeView(buttonContent);
        LinearLayout button = new LinearLayout(getContext());
        button.setOrientation(LinearLayout.HORIZONTAL);
        button.addView(TextSpinner.createTinyArrow(getContext(), R.drawable.ic_keyboard_arrow_down_black_36dp));
        button.addView(buttonContent);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (folderContent.getVisibility() == VISIBLE) {
                    folderContent.setVisibility(GONE);
                } else {
                    folderContent.setVisibility(VISIBLE);
                }
            }
        });
        addView(button, 0);
        hasInit = true;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("SimpleFolderLayout", "onLayout");
        init();
        super.onLayout(changed, l, t, r, b);
    }
}
