package com.edu.latte.app;

/**
 * Created by jl on 2017/9/5.
 * 枚举是全app唯一的单例,只能被初始化一次
 * 多线程使用枚举方便
 */

public enum ConfigKeys {
    API_HOST,
    APPLICATION_CONTEXT,
    CONFIG_READY,
    ICON,
    INTERCEPTOR
}
