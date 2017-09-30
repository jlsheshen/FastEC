package com.edu.latte.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by jl on 2017/9/15.
 */

public abstract class BaseInterceptor implements Interceptor {


    /**
     *
     * @param chain 拦截器内的接口
     * @return
     */
    protected LinkedHashMap<String ,String> getUrlParameter(Chain chain){
            final HttpUrl url = chain.request().url();
            int size = url.querySize();
        final  LinkedHashMap<String , String > params =  new LinkedHashMap<>();
        //取出所有的url参数对
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i),url.queryParameterValue(i));

        }
        return params;
    }
    protected String getUrlParameter (Chain chain,String key){
        final Request request = chain.request();
        return request.url().queryParameter(key);
    }

    /**
     * 从post中的请求体中获取
     * @param chain
     * @return
     */
    protected LinkedHashMap<String , String > getBodyParameters(Chain chain){
        FormBody formBody = (FormBody) chain.request().body();
        final  LinkedHashMap<String , String > params =  new LinkedHashMap<>();
        int size = formBody.size();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }

    protected String getBodyParameters(Chain chain,String key){
        return getBodyParameters(chain).get(key);

    }
}
