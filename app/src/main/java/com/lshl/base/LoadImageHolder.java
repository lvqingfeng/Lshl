package com.lshl.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.lshl.R;

/**
 * 作者：吕振鹏
 * 创建时间：10月31日
 * 时间：14:21
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class LoadImageHolder implements Holder<String> {

    private ImageView mImageView;

    @Override
    public View createView(Context context) {

        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        if (TextUtils.isEmpty(data) || data.contains("null"))
            mImageView.setImageResource(R.drawable.em_group_icon);
        else
            Glide.with(context).load(data).into(mImageView);
    }
}
