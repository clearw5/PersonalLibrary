package com.stardust.preference;

import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;

import com.stardust.tool.ContextReservoir;
import com.stardust.tool.SharedPreferencesTool;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/10/20.
 */

public class PreferenceSystem {

    private Map<String, PreferenceExecutor> mPreferenceExecutorMap = new TreeMap<>();
    private Map<String, String> mDefaultValueMap = new TreeMap<>();

    public void register(String preferenceKey, PreferenceExecutor executor) {
        mPreferenceExecutorMap.put(preferenceKey, executor);
        readInitialPreference(preferenceKey, executor);
    }

    public void unregister(String preferenceKey) {
        mDefaultValueMap.remove(preferenceKey);
    }

    public void correlate(final String preferenceKey, CheckBoxPreference checkBoxPreference) {
        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                setPreference(preferenceKey, newValue.toString());
                return true;
            }
        });
        checkBoxPreference.setChecked(Boolean.valueOf(getPreference(preferenceKey)));
    }

    public void correlate(final String preferenceKey, EditTextPreference editTextPreference) {
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                setPreference(preferenceKey, newValue.toString());
                return true;
            }
        });
        editTextPreference.setText(getPreference(preferenceKey));
    }

    public void setDefaultValue(String preferenceKey, String defaultValue) {
        mDefaultValueMap.put(preferenceKey, defaultValue);
    }

    public void setDefaultValue(String preferenceKey, int defaultValue) {
        setDefaultValue(preferenceKey, String.valueOf(defaultValue));
    }

    public void setPreference(String preferenceKey, String value) {
        SharedPreferencesTool.writeConfigString(ContextReservoir.getDefaultContext(), preferenceKey, value);
        PreferenceExecutor executor = mPreferenceExecutorMap.get(preferenceKey);
        if (executor != null) {
            executor.apply(preferenceKey, value);
        }
    }

    public String getPreference(String preferenceKey) {
        String value = SharedPreferencesTool.readConfigString(ContextReservoir.getDefaultContext(), preferenceKey);
        if (value == null) {
            value = mDefaultValueMap.get(preferenceKey);
        }
        return value;
    }

    private void readInitialPreference(String preferenceKey, PreferenceExecutor executor) {
        executor.apply(preferenceKey, getPreference(preferenceKey));
    }
}
