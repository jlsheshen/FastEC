package com.edu.latte.net;

import android.content.Context;

import com.edu.latte.net.callback.IError;
import com.edu.latte.net.callback.IFailure;
import com.edu.latte.net.callback.IRequest;
import com.edu.latte.net.callback.ISuccess;
import com.edu.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jl on 2017/9/7.
 */

public class RestClientBuilder {
    private  String mUrl;
    private final Map<String ,Object> PARAMS = RestCreator.getParams();
    private  IRequest mRequest;
    private  ISuccess  mSuccess;
    private  IFailure  mFailure;
    private  IError mError;
    private  RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
    private File mFile;
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    public RestClientBuilder() {
    }

    public RestClientBuilder setmUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public RestClientBuilder setmParams(Map<String, Object> mParams) {
        PARAMS.putAll(mParams);
        return this;
    }
    public RestClientBuilder setmParams(String key, Object value) {
        PARAMS.put(key,value);
        return this;
    }


    public RestClientBuilder setmRequest(IRequest mRequest) {
        this.mRequest = mRequest;
        return this;
    }

    public RestClientBuilder setmSuccess(ISuccess mSuccess) {
        this.mSuccess = mSuccess;
        return this;
    }

    public RestClientBuilder setmFailure(IFailure mFailure) {
        this.mFailure = mFailure;
        return this;
    }

    public RestClientBuilder setmError(IError mError) {
        this.mError = mError;
        return this;
    }

    public RestClientBuilder setmBody(RequestBody mBody) {
        this.mBody = mBody;
        return this;
    }

    public RestClientBuilder setmFile(File mFile) {
        this.mFile = mFile;
        return this;
    }
    public RestClientBuilder setmFile(String mFile) {
        this.mFile = new File(mFile);
        return this;
    }

    public RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }



    public RestClientBuilder setLoader  (Context mContext) {
        this.mContext = mContext;
        return this;
    }


    public RestClientBuilder setmLoaderStyle(LoaderStyle mLoaderStyle) {
        this.mLoaderStyle = mLoaderStyle;
        return this;
    }

    public RestClientBuilder setmDownloadDir(String mDownloadDir) {
        this.mDownloadDir = mDownloadDir;
        return this;
    }

    public RestClientBuilder setmExtension(String mExtension) {
        this.mExtension = mExtension;
        return this;
    }

    public RestClientBuilder setmName(String mName) {
        this.mName = mName;
        return this;
    }

    public  final RestClient build(){
      return new RestClient(mUrl,PARAMS,mRequest,mSuccess,mFailure,mError,mBody,mContext,mLoaderStyle,mFile,mDownloadDir,mExtension,mName);
    }

}
