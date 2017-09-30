package com.edu.latte.net.download;

import android.os.AsyncTask;

import com.edu.latte.net.RestCreator;
import com.edu.latte.net.callback.IError;
import com.edu.latte.net.callback.IFailure;
import com.edu.latte.net.callback.IRequest;
import com.edu.latte.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jl on 2017/9/13.
 */

public class DownloadHandler {
    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    //请求
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    //下载的目录
    private final String DOWNLOAD_DIR;
    //文件后缀
    private final String EXTENSION;
    //下载的文件名
    private final String NAME;


    public DownloadHandler(String URL, IRequest REQUEST, ISuccess SUCCESS, IFailure FAILURE, IError ERROR, String DOWNLOAD_DIR, String EXTENSION, String NAME) {
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
    }

    public void downloadHandler() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            ResponseBody responseBody = response.body();
                            SaveFileTask saveFileTask = new SaveFileTask(REQUEST, SUCCESS);
                            //以线程池的方式执行
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, response, NAME);
                            //下载后结束 这里一定要注意判断.否则文件会下载不全
                            if (saveFileTask.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestStart();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                        RestCreator.getParams().clear();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                        RestCreator.getParams().clear();

                    }
                });

    }
}
