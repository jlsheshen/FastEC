package com.edu.latte.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by jl on 2017/9/11.
 *Loading 创建类,用于
 */

public final class LoadingCreator {
    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();
    static AVLoadingIndicatorView creat(String type , Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;

    }
    private static Indicator getIndicator(String name){

        if (name == null||name.isEmpty()){
            return  null;
        }
        //内部有大量String操作,使用String便于提高效率
        final StringBuilder drawableClassName = new StringBuilder();
        //如果有点则是类名
        if (!name.contains(".")){
            //获得包名
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            //讲包名和类名拼接在一起
            drawableClassName.append(defaultPackageName).
                    append(".indicators")
                    .append(".");

        }
        drawableClassName.append(name);
        try {
            //创建一个新的类
            final Class drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
