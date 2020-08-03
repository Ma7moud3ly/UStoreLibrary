/*
 * UPref .. shared preferences handler
 * By : Mahmoud Aly
 * engma7moud3l@gmail.com
 */
package com.ma7moud3ly.ustore;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UPref {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPref;

    public UPref(Activity activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    //store restore any value
    public <DATA> boolean put(String key, DATA val) {
        if (val.getClass() == Integer.class)
            editor.putInt(key, (Integer) val);
        else if (val.getClass() == Boolean.class)
            editor.putBoolean(key, (Boolean) val);
        else if (val.getClass() == Float.class)
            editor.putFloat(key, (Float) val);
        else if (val.getClass() == String.class)
            editor.putString(key, (String) val);
        else return false;
        editor.commit();
        return true;
    }

    //restore any value
    public <DATA> DATA get(String key, DATA def) {
        Class<DATA> type = (Class<DATA>) (def.getClass());
        try {
            if (def.getClass() == Integer.class)
                return type.cast(sharedPref.getInt(key, (Integer) def));
            else if (def.getClass() == Boolean.class)
                return type.cast(sharedPref.getBoolean(key, (Boolean) def));
            else if (def.getClass() == Float.class)
                return type.cast(sharedPref.getFloat(key, (Float) def));
            else if (def.getClass() == String.class)
                return type.cast(sharedPref.getString(key, (String) def));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type.cast(def);
    }

    //restore list in shared preferences
    public void putList(String key, List list) {
        Set<String> set = new HashSet<>();
        set.addAll(list);
        editor.putStringSet(key, set);
        editor.commit();
    }

    //store list in shared preferences
    public List getList(String key) {
        try {
            Set<String> set = sharedPref.getStringSet(key, null);
            List list = new ArrayList<>();
            for (String s : set) list.add(s);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
