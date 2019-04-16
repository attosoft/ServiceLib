package com.id0755.attoapplication;

import android.app.Application;

import com.id0755.android.service.base.ServiceManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ServiceManager.getInstance().init(this);
    }
}
