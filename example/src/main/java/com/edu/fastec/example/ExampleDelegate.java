package com.edu.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.edu.latte.delegates.LatteDelegate;
import com.edu.latte.net.RestClient;
import com.edu.latte.net.RestCreator;
import com.edu.latte.net.callback.IError;
import com.edu.latte.net.callback.IFailure;
import com.edu.latte.net.callback.ISuccess;
import com.edu.latte.rx.RxRestClient;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jl on 2017/9/6.
 */

public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClint();//正常网络请求
        Log.d("ExampleDelegate", "onBindView");
        onCallRxGet();//rx网络请求
    }

    private void onCallRxGet() {
        String url = "index.php";
        WeakHashMap<String ,Object> params =  new WeakHashMap<>();
        Observable<String> observable = RestCreator.getRxRestService().get(url,params);
        observable .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("ExampleDelegate", "onSubscribe" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("ExampleDelegate", "onNext"+ Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("ExampleDelegate", "onError"+ Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {
                        Log.d("ExampleDelegate", "onComplete"+ Thread.currentThread().getName());

                    }
                });
    }
         public void rxCallRestClint(){
        String url = "index.php";
        RxRestClient.builder()
                .setmUrl(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("ExampleDelegate", "onSubscribe" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("ExampleDelegate", "onNext"+ Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("ExampleDelegate", "onError"+ Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {
                        Log.d("ExampleDelegate", "onComplete"+ Thread.currentThread().getName());

                    }
                });
    }


    /**
     * 发出网络请求
     */
    private void testRestClint() {
        RestClient.builder().
                setmUrl("http://127.0.0.1/index")
                .setLoader(getContext())
                .setmSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                        Log.d("ExampleDelegate", "response" + response);
                    }
                })
                .setmError(new IError() {
                    @Override
                    public void onError(int code, String message) {
                        Log.d("ExampleDelegate", "message" + message);

                    }
                }).setmFailure(new IFailure() {
            @Override
            public void onFailure() {
                Log.d("ExampleDelegate", "onFailure");
            }
        }).build()
                .get();
    }
}
