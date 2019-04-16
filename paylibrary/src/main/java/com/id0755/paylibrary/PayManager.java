package com.id0755.paylibrary;

import android.util.Log;

import com.id0755.android.service.ILoginService;
import com.id0755.android.service.base.ServiceManager;
import com.id0755.android.service.base.ServiceName;

public class PayManager {
    private final static String TAG = "PayManager";

    public static void pay(int num) {
        ILoginService loginService = ServiceManager.getInstance().getService(ServiceName.LOGIN_SERVICE);
        if (loginService != null) {
            loginService.login();
            Log.d(TAG, "pay");
        }
    }
}
