package com.identifyprotector.identifyprotector.objects;


/**
 * Created by Zoran on 09/09/2016.
 */
public interface UniversalObject {


    void put(String key, Object value);

    Object get(String key, Object defaultValue);

}
