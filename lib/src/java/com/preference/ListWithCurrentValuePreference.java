
package com.stardust.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.prefs.MaterialListPreference;
import com.stardust.calculator.R;

/**
 * Created by Stardust on 2016/7/28.
 */
public class ListWithCurrentValuePreference extends MaterialListPreference {

    private TextView currentValue;

    public ListWithCurrentValuePreference(Context context) {
        super(context);
        init();
    }


    public ListWithCurrentValuePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListWithCurrentValuePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ListWithCurrentValuePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        currentValue = (TextView) view.findViewById(R.id.current_value);
        if (currentValue != null) {
            currentValue.setText(getValue());
        }
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (currentValue != null) {
            currentValue.setText(value);
        }
    }


    private void init() {
        setLayoutResource(R.layout.material_with_current_value_preferencce);
    }
}
