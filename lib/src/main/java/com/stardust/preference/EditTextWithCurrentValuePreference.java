package com.stardust.preference;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.prefs.MaterialEditTextPreference;
import com.stardust.lib.R;

/**
 * Created by Stardust on 2016/7/28.
 */
public class EditTextWithCurrentValuePreference extends MaterialEditTextPreference {

    private TextView currentValue;

    public EditTextWithCurrentValuePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public EditTextWithCurrentValuePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EditTextWithCurrentValuePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithCurrentValuePreference(Context context) {
        super(context);
        init();
    }


    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        currentValue = (TextView) view.findViewById(R.id.current_value);
        if (currentValue != null) {
            currentValue.setText(getText());
        }
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (currentValue != null) {
            currentValue.setText(getText());
        }
    }

    private void init() {
        setLayoutResource(R.layout.material_with_current_value_preferencce);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        getEditText().addTextChangedListener(textWatcher);
    }

    public void setInputType(int type) {
        getEditText().setInputType(type);
    }
}