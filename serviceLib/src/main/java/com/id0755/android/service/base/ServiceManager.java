package com.id0755.android.service.base;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author andy
 */
public final class ServiceManager {
    private final static String TAG = ServiceManager.class.getSimpleName();

    private ServiceManager() {
    }

    private final static Map<String, IProvider> PROVIDERS = new ConcurrentHashMap<>();
    private static volatile ServiceManager INSTANCE = null;
    /**
     * 这里会泄露Application，预期之中
     */
    private Context mApplicationContext = null;

    public void init(Context context) {
        if (context != null) {
            mApplicationContext = context.getApplicationContext();
        }
    }

    public static ServiceManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ServiceManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * provider register
     *
     * @param name     服务的名字，也是唯一标识
     * @param provider 服务提供者
     */
    public void registerProvider(String name, IProvider provider) {
        if (PROVIDERS.containsKey(name)) {
            Log.e(TAG, "registerProvider provider already existed");
        }
        PROVIDERS.put(name, provider);
    }

    /**
     * provider Unregister
     *
     * @param name 服务的名字，也是唯一标识
     */
    public void unRegisterProvider(String name) {
        if (!PROVIDERS.containsKey(name)) {
            Log.e(TAG, "registerProvider provider is not exist");
        } else {
            PROVIDERS.remove(name);
        }
    }

    /**
     * 判断是否已经从manifest中解析了
     */
    private boolean bInit = false;

    /**
     * 获取服务对象
     *
     * @param name 服务名
     * @return 服务对象
     */
    @SuppressWarnings("unchecked")
    public <E extends IService> E getService(String name) {
        if (mApplicationContext == null) {
            Log.e(TAG, "getService ServiceManager is not init!");
            throw new IllegalArgumentException("ServiceManager is not init!");
        }

        if (!bInit) {
            synchronized (ServiceManager.class) {
                if (!bInit) {
                    ManifestParser parser = new ManifestParser(mApplicationContext);
                    List<IProvider> providers = parser.parse();
                    for (IProvider provider : providers) {
                        provider.applyOptions(mApplicationContext);
                        PROVIDERS.put(provider.getServiceName(), provider);
                    }
                    bInit = true;
                }
            }
        }
        IProvider provider = PROVIDERS.get(name);
        E service = null;
        if (provider == null) {
            Log.w(TAG, "name = " + name + " provider==null");
        } else {
            try {
                service = (E) (provider.newService(mApplicationContext));
            } catch (ClassCastException ex) {
                ex.printStackTrace();
                Log.e(TAG, ex.getMessage());
            }
        }
        return service;
    }
}
