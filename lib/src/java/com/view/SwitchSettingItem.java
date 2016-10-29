package com.stardust.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.stardust.calculator.R;

/**
 * Created by Stardust on 2016/7/16.
 */
public class SwitchSettingItem extends RelativeLayout {

    private static final boolean isAndroidL = Build.VERSION.SDK_INT > 21;
    LinearLayout text;
    SwitchButton mSwitchButton;
    Switch mSwitch;


    public SwitchSettingItem(Context context) {
        super(context);
        init();
    }

    public SwitchSettingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setTitle(String title) {
        ((TextView) text.findViewById(R.id.headline)).setText(title);
    }

    public void setSubtitle(String subtitle) {
        ((TextView) text.findViewById(R.id.subtitle)).setText(subtitle);
    }

    public void setState(boolean checked) {
        if (isAndroidL)
            mSwitch.setChecked(checked);
        else
            mSwitchButton.setChecked(checked);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        if (isAndroidL)
            mSwitch.setOnCheckedChangeListener(listener);
        else
            mSwitchButton.setOnCheckedChangeListener(listener);
    }

    private void init() {
        text = (LinearLayout) View.inflate(getContext(), R.layout.double_title_item, null);
        text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAndroidL)
                    mSwitch.setChecked(!mSwitch.isChecked());
                else
                    mSwitchButton.setChecked(!mSwitchButton.isChecked());
            }
        });
        addView(text);
        if (isAndroidL) {
            mSwitch = (Switch) View.inflate(getContext(), R.layout.switch_setting_item_switch, null);
            addView(mSwitch);
        } else {
            mSwitchButton = (SwitchButton) View.inflate(getContext(), R.layout.switch_setting_item_3rd_swicth_button, null);
            addView(mSwitchButton);
        }
    }
}
