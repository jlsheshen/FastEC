package com.edu.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by 傅令杰 on 2017/4/22
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }
//当线程执行完毕,执行监听
    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
