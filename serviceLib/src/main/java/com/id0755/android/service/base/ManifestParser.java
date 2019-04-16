package com.id0755.android.service.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andy
 */
public final class ManifestParser {
    private final static String TAG = ManifestParser.class.getSimpleName();
    private final Context context;

    public ManifestParser(@NonNull Context context) {
        this.context = context;
    }

    public List<IProvider> parse() {
        ArrayList<IProvider> providers = new ArrayList<>();
        try {
            ApplicationInfo appInfo = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if ("IProvider".equals(appInfo.metaData.get(key))) {
                            providers.add(parseProvider(key.trim()));
                    }
                }
            }
            return providers;
        } catch (PackageManager.NameNotFoundException var5) {
            throw new RuntimeException("Unable to find metadata to parse IProvider", var5);
        }
    }

    private static IProvider parseProvider(String className) {
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException var6) {
            throw new IllegalArgumentException("Unable to find IProvider implementation", var6);
        }

        Object provider;
        try {
            provider = clazz.newInstance();
        } catch (InstantiationException var4) {
            throw new RuntimeException("Unable to instantiate IProvider implementation for " + clazz, var4);
        } catch (IllegalAccessException var5) {
            throw new RuntimeException("Unable to instantiate IProvider implementation for " + clazz, var5);
        }

        if (!(provider instanceof IProvider)) {
            throw new RuntimeException("Expected instanceof IProvider, but found: " + provider);
        } else {
            return (IProvider) provider;
        }
    }
}
