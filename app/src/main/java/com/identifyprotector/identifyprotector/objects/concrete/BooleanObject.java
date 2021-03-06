package com.identifyprotector.identifyprotector.objects.concrete;

import android.content.SharedPreferences;

import com.identifyprotector.identifyprotector.objects.UniversalObject;

/**
 * Created by Zoran on 09/09/2016.
 */
public class BooleanObject implements UniversalObject {

    private static SharedPreferences prefs;

    public BooleanObject(SharedPreferences prefs) {
        this.prefs = prefs;
    }
    @Override
    public void put(String key, Object value) {
        prefs.edit().putBoolean(key, (Boolean) value).apply();
    }

    @Override
    public Object get(String key, Object defaultValue) {
        return prefs.getBoolean(key, (Boolean) defaultValue);
    }
}
