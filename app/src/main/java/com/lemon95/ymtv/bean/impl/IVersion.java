package com.lemon95.ymtv.bean.impl;

import com.lemon95.ymtv.bean.listener.OnVersionLisener;

/**
 * Created by WXT on 2016/7/14.
 */
public interface IVersion {

    /**
     * 检测版本
     * @param version
     * @param onVersionLisener
     */
    public void checkVersion(String version,OnVersionLisener onVersionLisener);
}
