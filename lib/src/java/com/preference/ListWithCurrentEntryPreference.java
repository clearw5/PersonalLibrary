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
public class ListWithCurrentEntryPreference extends MaterialListPreference {

    private TextView currentEntry;

    public ListWithCurrentEntryPreference(Context context) {
        super(context);
        init();
    }


    public ListWithCurrentEntryPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListWithCurrentEntryPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ListWithCurrentEntryPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        currentEntry = (TextView) view.findViewById(R.id.current_value);
        if (currentEntry != null) {
            currentEntry.setText(getEntry());
        }
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (currentEntry != null) {
            currentEntry.setText(getEntries()[findIndexOfValue(value)]);
        }
    }


    private void init() {
        setLayoutResource(R.layout.material_with_current_value_preferencce);
    }
}
