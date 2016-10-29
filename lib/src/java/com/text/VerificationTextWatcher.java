package com.stardust.text;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Stardust on 2016/7/28.
 */
public class VerificationTextWatcher implements TextWatcher {

    private VerificationWatcher mVerificationWatcher;
    private EditText mEditText;

    public VerificationTextWatcher(EditText editText, VerificationWatcher watcher) {
        mVerificationWatcher = watcher;
        mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int code = mVerificationWatcher.verify(s.toString());
        String error = null;
        if (code != VerificationWatcher.VERIFY_PASS) {
            error = mVerificationWatcher.getError(code);
        }
        mEditText.setError(error);
    }

    public interface VerificationWatcher {
        int VERIFY_PASS = 1;

        int verify(String str);

        String getError(int verifyCode);
    }
}
