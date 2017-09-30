package com.edu.latte.rx;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by jl on 2017/9/18.
 *
 */

public interface RxRestService {
    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String,Object> params);

    /**
     * post原始数据不能加FormUrlEncoded
     */
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<String> detele(@Url String url, @QueryMap Map<String,Object> params);



    /**
     * 文件下载,Streaming 一边下载,一边写入, 需要单独的写在线程中
     * @param url
     * @param params
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String,Object> params);

    /**
     * post的变种
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file );
}
