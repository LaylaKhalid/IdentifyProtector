package com.example.library.objects.concrete;

import android.content.SharedPreferences;

import com.example.library.objects.UniversalObject;

/**
 * Created by Zoran on 09/09/2016.
 */
public class StringObject implements UniversalObject {

    private static SharedPreferences prefs;

    public StringObject(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void put(String key, Object value) {
        prefs.edit().putString(key, (String) value).apply();
    }

    @Override
    public Object get(String key, Object defaultValue) {
        return prefs.getString(key, (String) defaultValue);
    }
}
