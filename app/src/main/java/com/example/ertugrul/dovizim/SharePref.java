package com.example.ertugrul.dovizim;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    static final String PREF_NAME = "Dosya";
    static final String PREF_KEY = "Key";


    public void save(Context context, String text) {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(PREF_KEY, text);
        editor.commit();
    }

    public String getValue(Context context) {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String text = setting.getString(PREF_KEY,null);
        return text;
    }

    public void clear(Context context) {
        SharedPreferences setting = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.clear();
    }
}
