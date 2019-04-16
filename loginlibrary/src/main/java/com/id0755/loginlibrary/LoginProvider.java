package com.id0755.loginlibrary;

import android.content.Context;

import com.id0755.android.service.ILoginService;
import com.id0755.android.service.base.IProvider;
import com.id0755.android.service.base.ServiceName;

public class LoginProvider implements IProvider<ILoginService> {
    private ILoginService mLoginService = new ILoginService() {
        @Override
        public void login() {
            LoginManager.login();
        }
    };
    @Override
    public ILoginService newService(Context context) {
        return mLoginService;
    }

    @Override
    public void applyOptions(Context context) {

    }

    @Override
    public String getServiceName() {
        return ServiceName.LOGIN_SERVICE;
    }
}
