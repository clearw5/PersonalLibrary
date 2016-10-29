package com.stardust.text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Stardust on 2016/10/20.
 */

public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public abstract void afterTextChanged(Editable s);
}
