package com.lemon95.ymtv.bean.listener;

import com.lemon95.ymtv.bean.Version;

/**
 * Created by WXT on 2016/7/14.
 * 监听版本变化
 * 2中状态 升级  不升级
 */
public interface OnVersionLisener {


    //升级版本
    void updateVersion(Version version);

    //版本相同不升级
    void noUpdateVersion();

}
