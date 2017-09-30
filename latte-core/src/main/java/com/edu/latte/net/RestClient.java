package com.edu.latte.net;

import android.content.Context;

import com.edu.latte.net.callback.IError;
import com.edu.latte.net.callback.IFailure;
import com.edu.latte.net.callback.IRequest;
import com.edu.latte.net.callback.ISuccess;
import com.edu.latte.net.callback.RequestCallBack;
import com.edu.latte.net.download.DownloadHandler;
import com.edu.latte.ui.loader.LatteLoader;
import com.edu.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by jl on 2017/9/6.
 */

public class RestClient {
    private final String URL;
    private static final Map<String ,Object> PARAMS = RestCreator.getParams();
    //请求
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    //请求体
    private final RequestBody BODY;
    //loader的style
    private LoaderStyle LOADER_STYLE;
    //文件
    private final File FILE;

    //下载的目录
    private final String DOWNLOAD_DIR;
    //文件后缀
    private final String EXTENSION;
    //下载的文件名
    private final String NAME;


    private Context context;

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    public RestClient(String url, Map<String,
            Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      Context context,
                      LoaderStyle loaderStyle,
                      File file,
                      String downloadDir,
                      String extension,
                      String name ) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.context = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }
    private void requset(HttpMethod method){
    final RestService service = RestCreator.getRestService();
        Call<String> call = null;
        //请求开始监听
        if (REQUEST != null){
            REQUEST.onRequestStart();
        }
        //此时开始显示Loading
        if (LOADER_STYLE != null){
            LatteLoader.showLoading(context,LOADER_STYLE);
        }
        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.postRaw(URL,BODY);

                break;
            case DELETE:
                call = service.detele(URL,PARAMS);

                break;
            case UPLOAD:

                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                //将文件名传进来
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;
        }
        if (call!=null){
            //开启异步子线程
            call.enqueue(getRequestCallback());

        }

    }
    private Callback<String> getRequestCallback(){
        return  new RequestCallBack(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get(){
        requset(HttpMethod.GET);
    }
    public final void post(){
        if (BODY == null){
            requset(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                   throw new RuntimeException("params must br null");

            }
            requset(HttpMethod.POST_RAW);
        }
    } public final void put(){
        if (BODY == null){
            requset(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must br null");
            }
            requset(HttpMethod.PUT_RAW);

        }

    }
    public final void detele(){
        requset(HttpMethod.DELETE);
    }

    public final void upload(){
        requset(HttpMethod.UPLOAD);
    }
    public final void download(){
        new DownloadHandler(URL, REQUEST,SUCCESS,FAILURE,ERROR,DOWNLOAD_DIR,EXTENSION,NAME).downloadHandler();

    }
}
