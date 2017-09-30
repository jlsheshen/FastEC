package com.edu.fastec.example;

import android.app.Application;

import com.edu.latte.app.Latte;
import com.edu.latte.net.interceptors.DebugInterceptor;

/**
 * Created by jl on 2017/9/6.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1/")//"服务地址"
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
               .Configurator();
    }
}
