package com.edu.latte.rx;

import android.content.Context;

import com.edu.latte.net.HttpMethod;
import com.edu.latte.net.RestCreator;
import com.edu.latte.ui.loader.LatteLoader;
import com.edu.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by jl on 2017/9/6.
 */

public class RxRestClient {
    private final String URL;
    private static final Map<String ,Object> PARAMS = RestCreator.getParams();

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

    public static       RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }


    public RxRestClient(String url, Map<String,
            Object> params,
                        RequestBody body,
                        Context context,
                        LoaderStyle loaderStyle,
                        File file,
                        String downloadDir,
                        String extension,
                        String name ) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.BODY = body;
        this.context = context;
        this.LOADER_STYLE = loaderStyle;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }
    private Observable<String> request(HttpMethod method){
    final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        //此时开始显示Loading
        if (LOADER_STYLE != null){
            LatteLoader.showLoading(context,LOADER_STYLE);
        }
        switch (method){
            case GET:
                observable = service.get(URL,PARAMS);
                break;
            case POST:
                observable = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL,BODY);
                break;
            case PUT:
                observable = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                observable = service.postRaw(URL,BODY);

                break;
            case DELETE:
                observable = service.detele(URL,PARAMS);

                break;
            case UPLOAD:

                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                //将文件名传进来
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                observable = service.upload(URL,body);
                break;
            default:
                break;
        }
       return observable;

    }


    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }
    public final Observable<String> post(){
        if (BODY == null){
            return request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                   throw new RuntimeException("params must br null");

            }
            return request(HttpMethod.POST_RAW);
        }
    }
    public final Observable<String> put(){
        if (BODY == null){
            return  request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must br null");
            }
            return request(HttpMethod.PUT_RAW);

        }

    }
    public final Observable<String> detele(){
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload(){
        return request(HttpMethod.UPLOAD);
    }
    public final Observable<ResponseBody> download(){
        Observable<ResponseBody> observable =  RestCreator.getRxRestService().download(URL,PARAMS);
        return observable;

    }
}
