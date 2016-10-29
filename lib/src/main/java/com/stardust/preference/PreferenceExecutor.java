package com.stardust.preference;

/**
 * Created by Stardust on 2016/10/20.
 */

public interface PreferenceExecutor {
    void apply(String preferenceKey, String value);
}