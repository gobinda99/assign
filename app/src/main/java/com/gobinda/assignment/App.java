package com.gobinda.assignment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.gobinda.assignment.dao.DaoUtils;
import com.gobinda.assignment.dao.models.User;

import timber.log.Timber;

/**
 * Created by gobinda on 14/12/16.
 */
public class App extends MultiDexApplication{
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        DaoUtils.init(this);

    }

    @Override
    public void onTerminate() {
        DaoUtils.shutdown();
        super.onTerminate();
    }

    public  static SharedPreferences getPrefs(){
       return sContext.getSharedPreferences("pref",0);

    }
}
