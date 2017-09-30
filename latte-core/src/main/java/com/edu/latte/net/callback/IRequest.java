package com.edu.latte.net.callback;

/**
 * Created by jl on 2017/9/7.
 * 请求类.用于显示请求等待中的加载进度
 */

public interface IRequest {
    void onRequestStart();
    void onRequestEnd();
}
