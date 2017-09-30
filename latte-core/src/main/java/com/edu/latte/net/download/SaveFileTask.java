package com.edu.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.edu.latte.app.Latte;
import com.edu.latte.net.callback.IRequest;
import com.edu.latte.net.callback.ISuccess;
import com.edu.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by jl on 2017/9/13.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;


    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;



    }

    @Override
    protected File doInBackground(Object... params) {
        //目录
        String downDir  = (String) params[0];
        //后缀名
        String extension = (String) params[1];
        //请求体
        final ResponseBody body = (ResponseBody) params[2];

        String name = (String) params[3];

        //输入流
        final InputStream  inputStream = body.byteStream();

        if (downDir == null||downDir.equals("")){
            downDir = "down_loads";
        }
        if (extension == null||extension.equals("")){

            extension = "";
        }
            if (name == null) {
                return FileUtil.writeToDisk(inputStream, downDir, extension.toUpperCase(), extension);
            } else {
                return FileUtil.writeToDisk(inputStream, downDir, name);
            }
    }

    @Override
    protected void onPostExecute(File file) {

        if (SUCCESS != null){
                SUCCESS.onSuccess(file.getPath());

        }
        if (REQUEST !=null){
            REQUEST.onRequestEnd();
        }

        super.onPostExecute(file);
    }

    /**
     *
     * @param file
     */
    private void autoInstakkApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            //将文件传进来,
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            //默认进行安装
            Latte.getApplicationContext().startActivity(install);

        }
    }
}
