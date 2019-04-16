package com.id0755.android.service.base;

import android.content.Context;

/**
 * @param <E>
 * @author andy
 */
public interface IProvider<E extends IService> extends IProguard {
    /**
     * 新建服务
     *
     * @param context applicationContext
     * @return 返回服务对象
     */
    E newService(Context context);

    /**
     * 服务提供者初始化，不可为耗时任务
     *
     * @param context 这里提供的是applicationContext
     */
    void applyOptions(Context context);

    /**
     * 获取服务名称
     *
     * @return 服务名
     */
    String getServiceName();
}
