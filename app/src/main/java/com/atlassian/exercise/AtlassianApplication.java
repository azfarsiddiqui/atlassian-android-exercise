package com.atlassian.exercise;

import android.app.Application;

public class AtlassianApplication extends Application {

    private static AtlassianApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static AtlassianApplication getInstance() {
        return sInstance;
    }
}
