package com.lshl.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.lshl.R;

/**
 * 作者：吕振鹏
 * 创建时间：12月28日
 * 时间：15:44
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class LshlLogoLoadingDialog extends Dialog {


    private AnimationDrawable anim;

    private CharSequence mMessageText;
    private TextView mTvMessage;

    protected LshlLogoLoadingDialog(Context context) {
        super(context);

        setContentView(R.layout.loading_layout);
        setCancelable(false);
        getWindow().setDimAmount(0);
        getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));

        ImageView ivLoading = (ImageView) findViewById(R.id.iv_loading);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        anim = (AnimationDrawable) ivLoading.getBackground();
        setMessage(mMessageText);
    }

    public void setMessage(CharSequence text) {
        mMessageText = text;
        if (mTvMessage != null && mMessageText != null) {
            mTvMessage.setText(mMessageText);
        }
    }

    @Override
    public void show() {
        if (anim != null) {
            anim.start();
        }
        super.show();
    }

    @Override
    public void hide() {
        if (anim != null) {
            anim.stop();
        }
        super.hide();
    }
}
