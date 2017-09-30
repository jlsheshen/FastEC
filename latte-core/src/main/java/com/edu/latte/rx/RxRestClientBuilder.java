package com.edu.latte.rx;

import android.content.Context;

import com.edu.latte.net.RestCreator;
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

public class RxRestClientBuilder {
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

    public RxRestClientBuilder() {
    }

    public RxRestClientBuilder setmUrl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public RxRestClientBuilder setmParams(Map<String, Object> mParams) {
        PARAMS.putAll(mParams);
        return this;
    }
    public RxRestClientBuilder setmParams(String key, Object value) {
        PARAMS.put(key,value);
        return this;
    }


    public RxRestClientBuilder setmRequest(IRequest mRequest) {
        this.mRequest = mRequest;
        return this;
    }

    public RxRestClientBuilder setmSuccess(ISuccess mSuccess) {
        this.mSuccess = mSuccess;
        return this;
    }

    public RxRestClientBuilder setmFailure(IFailure mFailure) {
        this.mFailure = mFailure;
        return this;
    }

    public RxRestClientBuilder setmError(IError mError) {
        this.mError = mError;
        return this;
    }

    public RxRestClientBuilder setmBody(RequestBody mBody) {
        this.mBody = mBody;
        return this;
    }

    public RxRestClientBuilder setmFile(File mFile) {
        this.mFile = mFile;
        return this;
    }
    public RxRestClientBuilder setmFile(String mFile) {
        this.mFile = new File(mFile);
        return this;
    }

    public RxRestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }



    public RxRestClientBuilder setLoader  (Context mContext) {
        this.mContext = mContext;
        return this;
    }


    public RxRestClientBuilder setmLoaderStyle(LoaderStyle mLoaderStyle) {
        this.mLoaderStyle = mLoaderStyle;
        return this;
    }

    public RxRestClientBuilder setmDownloadDir(String mDownloadDir) {
        this.mDownloadDir = mDownloadDir;
        return this;
    }

    public RxRestClientBuilder setmExtension(String mExtension) {
        this.mExtension = mExtension;
        return this;
    }

    public RxRestClientBuilder setmName(String mName) {
        this.mName = mName;
        return this;
    }

    public  final RxRestClient build(){
      return new RxRestClient(mUrl,PARAMS,mBody,mContext,mLoaderStyle,mFile,mDownloadDir,mExtension,mName);
    }

}
