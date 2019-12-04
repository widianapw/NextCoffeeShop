package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
