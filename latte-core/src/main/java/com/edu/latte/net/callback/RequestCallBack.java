package com.edu.latte.net.callback;

import android.os.Handler;

import com.edu.latte.ui.loader.LatteLoader;
import com.edu.latte.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jl on 2017/9/8.
 * 请求回调
 */

public class RequestCallBack implements Callback<String>{
    private final IRequest REQUSET;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();//讲Handler设置为static类型,可以避免内存泄露

    public RequestCallBack(IRequest request, ISuccess success, IFailure failure, IError error,LoaderStyle style) {
        this.REQUSET = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
            }else {
                if (ERROR != null){
                    ERROR.onError(response.code(),response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE!=null){
            FAILURE.onFailure();
        }
        if (REQUSET != null){
            REQUSET.onRequestEnd();
        }
        stopLoading();
    }

    /**
     * 停止进度圈,有一秒的延时
     */
    private void stopLoading(){
        if (LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
                //一秒的延时
            },1000);
        }
    }
}
