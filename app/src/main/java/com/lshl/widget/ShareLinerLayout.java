package com.lshl.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.util.DensityUtil;
import com.lshl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月30日
 * 时间：10:10
 * 版本：v1.0.0
 * 类描述：这个控件主要是用来展示目前分享的可以选内容，它是一个组合控件
 * 使用方式：
 * 1.直接在xml文件中引入该控件
 * 2.如果需要对结果进行回调，可以通过{@link #addOnShareSelectCallback(OnShareSelectCallback)}方法设置监听回调
 * 3.通过{@link #getSelectResult()}方法获取结果
 */

public class ShareLinerLayout extends LinearLayout {

    private OnShareSelectCallback mCallback;

    private List<OnShareSelectCallback> mCallbackList;


    private CheckBox mCbxShareNews;//分享到新闻
    private CheckBox mCbxShareQQ;//分享到QQ
    private CheckBox mCbxShareWx;//分享到微信
    private CheckBox mCbxShareSina;//分享到新浪
    private CheckBox mCbxShareWxCircle;//分享到朋友圈

    public ShareLinerLayout(Context context) {
        this(context, null);
    }

    public ShareLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCallbackList = new ArrayList<>();
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.WHITE);
        int padding = DensityUtil.dip2px(context, 5);
        setPadding(padding, padding, padding, padding);
        init(context);
    }


    private void init(Context context) {
        TextView textView = new TextView(context);
        mCbxShareNews = new CheckBox(context);
        mCbxShareQQ = new CheckBox(context);
        mCbxShareWx = new CheckBox(context);
        mCbxShareSina = new CheckBox(context);
        mCbxShareWxCircle = new CheckBox(context);
        mCbxShareSina.setVisibility(GONE);
        textView.setText("同步分享到");
        textView.setTextColor(ContextCompat.getColor(context, R.color.textBlackColor));

        mCbxShareNews.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.news_selector));
        mCbxShareQQ.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.qq_selector));
        mCbxShareWx.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.weixin_selector));
        mCbxShareSina.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.sina_selector));
        mCbxShareWxCircle.setButtonDrawable(ContextCompat.getDrawable(context, R.drawable.friend_selector));

        mCbxShareWxCircle.setPadding(5, 5, 5, 5);
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.leftMargin = DensityUtil.dip2px(context, 5);

        LayoutParams weightLp = new LayoutParams(0, 0);
        weightLp.weight = 1;

        LayoutParams marginLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marginLp.leftMargin = DensityUtil.dip2px(context, 5);
        marginLp.rightMargin = DensityUtil.dip2px(context, 5);

        addView(textView, textLp);
        addView(new View(context), weightLp);
        addView(mCbxShareNews, marginLp);
        addView(mCbxShareSina, marginLp);
        addView(mCbxShareQQ, marginLp);
        addView(mCbxShareWx, marginLp);
        addView(mCbxShareWxCircle, marginLp);

        initClick();

    }

    private void initClick() {

        mCbxShareNews.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "分享到首页", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCbxShareQQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "分享到QQ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCbxShareWxCircle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "分享到朋友圈", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCbxShareWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "分享到微信", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public CheckBox getShareNewsButton() {
        return mCbxShareNews;
    }

    /**
     * 获取页面中获取选择的结果
     */
    public void getSelectResult() {

        boolean isShareNews = mCbxShareNews.isChecked();
        boolean isShareQQ = mCbxShareQQ.isChecked();
        boolean isShareWx = mCbxShareWx.isChecked();
        boolean isShareWxCircle = mCbxShareWxCircle.isChecked();
        boolean isSHareSina = mCbxShareSina.isChecked();

        if (mCallback != null) {
            mCallback.onSelectCallback(isShareNews, isShareQQ, isShareWx, isSHareSina, isShareWxCircle);
        }
        if (mCallbackList.size() > 0) {
            for (OnShareSelectCallback onShareSelectCallback : mCallbackList) {
                onShareSelectCallback.onSelectCallback(isShareNews, isShareQQ, isShareWx, isSHareSina, isShareWxCircle);
            }
        }
    }

    @Deprecated
    public void setOnShareSelectCallback(OnShareSelectCallback callback) {
        mCallback = callback;
    }

    public void addOnShareSelectCallback(OnShareSelectCallback callback) {
        mCallbackList.add(callback);
    }

    public interface OnShareSelectCallback {
        /**
         * 获取选择分享的回调
         *
         * @param isShareNews     是否分享到新闻（这个是咱们平台本地的）
         * @param isShareQQ       是否分享到QQ
         * @param isShareWx       是否分享到微信
         * @param isShareSina     是否分享到新浪
         * @param isShareWxCircle 是否分享到朋友圈
         */
        void onSelectCallback(boolean isShareNews, boolean isShareQQ, boolean isShareWx, boolean isShareSina, boolean isShareWxCircle);
    }

}
