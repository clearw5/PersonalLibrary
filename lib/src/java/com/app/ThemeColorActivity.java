package com.stardust.support.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.stardust.theme.ThemeColorManager;

/**
 * Created by Stardust on 2016/7/18.
 */
public class ThemeColorActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeColorManager.addActivityStatusBar(this);
        ThemeColorManager.addActivityNavigationBar(this);
    }

    public void setSupportActionBar(Toolbar toolbar) {
        ThemeColorManager.addToolBar(toolbar);
        super.setSupportActionBar(toolbar);
    }
}
