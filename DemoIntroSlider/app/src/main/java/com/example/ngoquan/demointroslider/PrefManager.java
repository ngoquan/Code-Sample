package com.example.ngoquan.demointroslider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NGOQUAN on 7/15/2016.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
//    shared pref mode
    int PRIVATE_MODE = 0;
//    shared pref file name
    private  static final String PREF_NAME = "androidhive_wellcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean getIsFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
