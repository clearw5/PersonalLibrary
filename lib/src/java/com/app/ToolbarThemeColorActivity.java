package com.stardust.support.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.stardust.theme.ThemeColorManager;

/**
 * Created by Stardust on 2016/8/10.
 */
public class ToolbarThemeColorActivity extends ThemeColorActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeColorManager.addActivityStatusBar(this);
        ThemeColorManager.addActivityNavigationBar(this);
    }

    public void setSupportActionBar(Toolbar toolbar) {
        ThemeColorManager.addToolBar(toolbar);
        super.setSupportActionBar(toolbar);
    }

    public void setSupportActionBarAsUp(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }


}
