package com.brcorner.categoryselector.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by dong on 2015/9/18 0018.
 */
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
