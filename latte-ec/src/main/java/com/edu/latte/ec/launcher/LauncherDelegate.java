package com.edu.latte.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.edu.latte.delegates.LatteDelegate;
import com.edu.latte.ec.R;
import com.edu.latte.ec.R2;
import com.edu.latte.util.timer.BaseTimerTask;
import com.edu.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jl on 2017/9/19.
 */

public class LauncherDelegate  extends LatteDelegate implements ITimerListener{

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){

    }
    private Timer mTimer = null;

    private int mCount = 5;


    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }


    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);//任务,延迟的时间,间隔的时间
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();

    }
    //判断是否显示滑动启动页
//    private void checkIsShowScroll() {
//        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
//            getSupportDelegate().start(new LauncherScrollDelegate(), SINGLETASK);
//        } else {
//            //检查用户是否登录了APP
//            AccountManager.checkAccount(new IUserChecker() {
//                @Override
//                public void onSignIn() {
//                    if (mILauncherListener != null) {
//                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
//                    }
//                }
//
//                @Override
//                public void onNotSignIn() {
//                    if (mILauncherListener != null) {
//                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
//                    }
//                }
//            });
//        }
//    }
    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
//                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
