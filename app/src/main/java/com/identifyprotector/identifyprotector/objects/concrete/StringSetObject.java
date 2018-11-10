package com.identifyprotector.identifyprotector.objects.concrete;

import android.content.SharedPreferences;

import com.identifyprotector.identifyprotector.objects.UniversalObject;

import java.util.Set;

/**
 * Created by Zoran on 09/09/2016.
 */
public class StringSetObject implements UniversalObject {

    private SharedPreferences prefs;

    public  StringSetObject(SharedPreferences prefs){
        this.prefs = prefs;
    }

    @Override
    public void put(String key, Object value) {
        prefs.edit().putStringSet(key, (Set<String>) value).apply();
    }

    @Override
    public Object get(String key, Object defaultValue) {
        return prefs.getStringSet(key, (Set<String>) defaultValue);
    }
}
