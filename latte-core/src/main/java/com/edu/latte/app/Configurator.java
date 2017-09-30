package com.edu.latte.app;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * Created by jl on 2017/9/5.
 * 全局的配置方法类
 */

public class Configurator {

    private static final WeakHashMap<Object, Object> LATTE_CONFIGS = new WeakHashMap<>();
    //拦截器列表Interceptor
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();




    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }
    final WeakHashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;

    }

    public final void Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    /**
     * 检测是否配置完成
     */
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        //如果配置并没有完成
        if (isReady){

        }else {
            throw new RuntimeException("配置并没有完成  Configuration is not ready,call configure");

        }
    }
    public Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }
    public Configurator withInterceptor(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }


    /**
     * 获取配置信息
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key);
    }

    /**
     * 单例模式
     */
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }


}

