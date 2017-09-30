package com.edu.latte.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.edu.latte.R;
import com.edu.latte.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by jl on 2017/9/12.
 */

public class LatteLoader {

    //默认为缩放8倍
    private static final int LOADER_SIZE_SCALE = 8;
    //屏幕偏移量
    private static final int LOADER_OFFSE_SCALE = 10;
    //Loader集合
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();
    public  static void showLoading(Context context, Enum<LoaderStyle> type){

        showLoading(context,type.name());
    }
    public  static void showLoading(Context context, String type){
        AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        //创建AvLoadingView
        AVLoadingIndicatorView avLoadingIndicatorView = LoadingCreator.creat(type,context);
        dialog.setContentView(avLoadingIndicatorView);
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        final Window dialogWindow =    dialog.getWindow();

        if (dialogWindow != null){
            WindowManager.LayoutParams layoutParams =  dialogWindow.getAttributes();
            //对
            layoutParams.width = deviceWidth/LOADER_SIZE_SCALE;
            layoutParams.height = deviceHeight/LOADER_SIZE_SCALE;
            layoutParams.height = layoutParams.height+deviceHeight/LOADER_OFFSE_SCALE;
            layoutParams.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();

    }
        public static void showLoader(Context context){
            showLoading(context,DEFAULT_LOADER);

        }
        public static void stopLoading(){
            for (AppCompatDialog dialog : LOADERS) {
                if (dialog != null){
                    if (dialog.isShowing()){
                        //cancel会走onCancel的回调,之后会用到
                        dialog.cancel();

                    }
                }
            }
        }
}
