package com.edu.latte.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by jl on 2017/9/21.
 */

public class LauncherHolder implements Holder<Integer> {
    private AppCompatImageView mImageView = null;
    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);

    }
}
