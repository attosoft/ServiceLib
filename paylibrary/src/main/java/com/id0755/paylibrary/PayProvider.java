package com.id0755.paylibrary;

import android.content.Context;

import com.id0755.android.service.IPayService;
import com.id0755.android.service.base.IProvider;
import com.id0755.android.service.base.ServiceName;

public class PayProvider implements IProvider<IPayService> {

    private IPayService mPayService = new IPayService() {
        @Override
        public void pay(int num) {
            PayManager.pay(num);
        }
    };

    @Override
    public IPayService newService(Context context) {
        return mPayService;
    }

    @Override
    public void applyOptions(Context context) {

    }

    @Override
    public String getServiceName() {
        return ServiceName.PAY_SERVICE;
    }
}
