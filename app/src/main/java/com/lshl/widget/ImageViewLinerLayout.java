package com.lshl.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.util.DensityUtil;
import com.lshl.R;

/**
 * Created by Administrator on 2017/1/17.
 */

public class ImageViewLinerLayout extends LinearLayout {
    private ImageView mPresident;
    private ImageView mShiming;
    private ImageView mJuankuan;
    private ImageView mWeishiming;
    private boolean isShiming;
    private boolean isJuankuan;
    private boolean isHUizhang;


    public ImageViewLinerLayout(Context context) {
        super(context);
    }

    public ImageViewLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.WHITE);
        int padding = DensityUtil.dip2px(context, 5);
        setPadding(padding, padding, padding, padding);
        init(context);
    }

    private void init(Context context) {
        mPresident = new ImageView(context);
        mShiming = new ImageView(context);
        mJuankuan = new ImageView(context);
        mWeishiming = new ImageView(context);
        mPresident.setImageResource(R.drawable.ic_vector_new_president);
        mShiming.setImageResource(R.drawable.ic_vector_new_yishiming);
        mJuankuan.setImageResource(R.drawable.ic_vector_new_juankuan);
        mWeishiming.setImageResource(R.drawable.ic_vector_new_weishiming);

        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.leftMargin = DensityUtil.dip2px(context, 5);

        LayoutParams weightLp = new LayoutParams(0, 0);
        weightLp.weight = 1;

        LayoutParams marginLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLp.leftMargin = DensityUtil.dip2px(context, 5);
        marginLp.rightMargin = DensityUtil.dip2px(context, 5);

        addView(mWeishiming, marginLp);
        addView(mShiming, marginLp);
        addView(mJuankuan, marginLp);
        addView(mPresident, marginLp);
        isShowView(isShiming,isJuankuan,isHUizhang);
    }

    private void isShowView(boolean isShiming, boolean isJuankuan, boolean isHUizhang) {
        if (isShiming){
            mWeishiming.setVisibility(View.GONE);
            mShiming.setVisibility(View.VISIBLE);
        }else {
            mShiming.setVisibility(View.GONE);
            mWeishiming.setVisibility(View.VISIBLE);
        }
        if (isJuankuan){
            mJuankuan.setVisibility(View.VISIBLE);
        }else {
            mJuankuan.setVisibility(View.GONE);
        }
        if (isHUizhang){
            mPresident.setVisibility(View.VISIBLE);
        }else {
            mPresident.setVisibility(View.GONE);
        }
    }
    public void ifIsChoose(IsChoose choose){
        if (choose!=null){
            choose.onSelectCallback(isShiming,isJuankuan,isHUizhang);
        }
    }
    public interface IsChoose{
        void onSelectCallback(boolean isShiming,boolean isJuankuan,boolean isHUizhang);
    }
}
