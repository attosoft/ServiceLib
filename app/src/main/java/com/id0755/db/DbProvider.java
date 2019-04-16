package com.id0755.db;

import android.content.Context;

import com.id0755.android.service.IDbService;
import com.id0755.android.service.base.IProvider;
import com.id0755.android.service.base.ServiceName;

public class DbProvider implements IProvider<IDbService> {
    private IDbService mDbService = new IDbService() {
        @Override
        public String getToken() {
            return DbManager.getToken();
        }
    };

    @Override
    public IDbService newService(Context context) {
        return mDbService;
    }

    @Override
    public void applyOptions(Context context) {

    }

    @Override
    public String getServiceName() {
        return ServiceName.DB_SERVICE;
    }
}
