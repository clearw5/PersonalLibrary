package com.stardust.theme;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Stardust on 2016/8/15.
 */
public class ThemeColorMaterialDialog extends MaterialDialog {

    public static final int DEFAULT_NEGATIVE_COLOR = 0xff7a7a7a;
    public static final int DEFAULT_NEUTRAL_COLOR = 0xff616161;

    protected ThemeColorMaterialDialog(Builder builder) {
        super(builder);
    }

    public static class Builder extends MaterialDialog.Builder {

        public Builder(@NonNull Context context) {
            super(context);
            positiveColor(ThemeColorManager.getThemeColor());
            negativeColor(DEFAULT_NEGATIVE_COLOR);
            neutralColor(DEFAULT_NEUTRAL_COLOR);
            itemsColor(ThemeColorManager.getThemeColor());
            linkColor(ThemeColorManager.getThemeColor());
            widgetColor(ThemeColorManager.getThemeColor());
        }

    }
}
