package com.edu.latte.net;

import com.edu.latte.app.ConfigKeys;
import com.edu.latte.app.Latte;
import com.edu.latte.rx.RxRestService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by jl on 2017/9/6.
 */

public class RestCreator {


    public static WeakHashMap<String ,Object>  getParams(){
        return ParamsHolder.PARAMS;
    }

    private static final class ParamsHolder{

        //讲Params设置为全局变量
        public static final WeakHashMap<String ,Object> PARAMS = new WeakHashMap<>();
    }



    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * 构建全局Retrofit客户端
     * Retrofit 单例
     */
    private static final class RetrofitHolder{
        /**
         * 获取BASE_URL
         */
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigKeys.API_HOST.name());

        private static final Retrofit RETROFIT_CLINT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

    }
    /**
     * Retrofit 单例
     */
    private static final class RxRetrofitHolder{
        /**
         * 获取BASE_URL
         */
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigKeys.API_HOST.name());

        private static final Retrofit RX_RETROFIT_CLINT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    /**
     * okHttp单例模式
     */
    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
    //获取拦截列表
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor(){
            if (INTERCEPTORS != null&& !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT =  addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 服务api单例
     *
     */
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLINT.create(RestService.class);

    }
    /**
     * 服务api单例x
     *
     */
    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE = RxRetrofitHolder.RX_RETROFIT_CLINT.create(RxRestService.class);

    }
    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }

}
