package com.example.library.objects.concrete;

import android.content.SharedPreferences;
import com.example.library.objects.UniversalObject;
import com.example.library.UniversalPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zoran on 09/09/2016.
 */
public class BooleanObject implements UniversalObject {
    private static SharedPreferences prefs;
    public static UniversalPreferences instance;


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
