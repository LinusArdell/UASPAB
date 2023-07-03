package com.twelve.mp3.player.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class KendaliLogin {

    private Context ctx;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    public static String keySP_username = "61nPQLtX6NYRuiGufJzPrA_username";
    public KendaliLogin(Context ctx) {
        this.ctx = ctx;
    }

    public void setPref(String key, String value){
        pref  = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefEditor = pref.edit();
        prefEditor.putString(key, value);
        prefEditor.commit();
    }

    public String getPref(String key){
        pref  = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getString(key, null);
    }

    public Boolean isLogin(String key){
        if(getPref(key) != null){
            return true;
        }
        else{
            return false;
        }
    }

    public void clearPreferences() {
        setPref(keySP_username, null);
    }
}
