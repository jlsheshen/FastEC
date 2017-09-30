package com.edu.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.latte.activitys.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by jl on 2017/9/5.
 *委托基类
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder  = null;

    //设置layout
    public abstract Object setLayout();
    //绑定视图
    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = null;
        if (setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer) setLayout(),container,false);
        }else if (setLayout() instanceof View){
            rootView = (View) setLayout();
        }else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        if (rootView != null){

            //绑定ButterKnife
            mUnbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);

        }
        return rootView;
    }
    public final ProxyActivity getProxyActivity() {
        return (ProxyActivity) _mActivity;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
