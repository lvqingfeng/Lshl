package com.lshl;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lshl.utils.DateUtils;
import com.lshl.view.GlideRoundTransform;

/**
 * 作者：吕振鹏
 * 创建时间：10月13日
 * 时间：10:42
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class BindingComponent {


    @BindingAdapter({"strikethrough"})
    public static void setTextStrikethrough(TextView tv, String text) {
        tv.setText(text);
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }

    @BindingAdapter({"imageRoundLoad", "defaultIcon", "radius"})
    public static void setImageRoundLoad(ImageView imageView,
                                         String imageUrl,
                                         Drawable defaultImage,
                                         int radius) {
        System.out.println("---- 图片地址 ---- " + imageUrl);
        if (TextUtils.isEmpty(imageUrl) || imageUrl.contains("null"))
            imageView.setImageDrawable(defaultImage);
        else
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    .transform(new GlideRoundTransform(imageView.getContext(), radius))
                    .error(defaultImage)
                    .into(imageView);
    }

    @BindingAdapter({"personGrade"})
    public static void setImageRes(ImageView imageView,
                                   int grade) {
        Constant.getGradeForAll(grade, imageView);
    }

    @BindingAdapter({"imageCircleLoad", "defaultIcon"})
    public static void setImageCircleLoad(ImageView imageView,
                                          String imageUrl,
                                          Drawable defaultImage) {
        System.out.println("---- 图片地址 ---- " + imageUrl);
        if (TextUtils.isEmpty(imageUrl) || imageUrl.contains("null"))
            imageView.setImageDrawable(defaultImage);
        else {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(imageView.getContext())
                    .load(imageUrl)
                    // .transform(new GlideCircleTransform(imageView.getContext()))
                    .error(defaultImage)
                    .into(imageView);
        }
    }

    @BindingAdapter({"imageLoad", "defaultIcon"})
    public static void setImageLoad(ImageView imageView,
                                    String imageUrl,
                                    Drawable defaultImage) {
        System.out.println("---- 图片地址 ---- " + imageUrl);
        if (TextUtils.isEmpty(imageUrl) || imageUrl.contains("null"))
            imageView.setImageDrawable(defaultImage);
        else
            Glide.with(imageView.getContext()).load(imageUrl).error(defaultImage).into(imageView);
    }

    @BindingAdapter({"stringRes", "formatText"})
    public static void setTextFormat(TextView textView, String stringRes, String textArgs) {
        textView.setText(String.format(stringRes, textArgs));
    }

    @BindingAdapter({"stringRes", "formatText", "textColor"})
    public static void setTextFormat(TextView textView, String stringRes, String textArgs, int textColor) {
        if (TextUtils.isEmpty(textArgs))
            return;
        String text = String.format(stringRes, textArgs);
        SpannableString spanText = new SpannableString(text);
        int index = text.lastIndexOf(textArgs);
        spanText.setSpan(new ForegroundColorSpan(textColor), index, index + textArgs.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanText);
    }

    @BindingAdapter({"imageSize"})
    public static void setViewSize(View view, int size) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            Log.d("BindingComponent", "------ 获取到的LayoutParams为空------");
            lp = new LinearLayout.LayoutParams(size, size);
        }
        lp.width = size;
        lp.height = size;
        view.setLayoutParams(lp);
    }


    @BindingAdapter({"time"})
    public static void setTextTime(TextView textView, String timeStr) {
        if (!TextUtils.isEmpty(timeStr)) {
            long time = Long.decode(timeStr) * 1000;
            textView.setText(DateUtils.getDateToString(time));
        }
    }

    @BindingAdapter({"timeFormat2"})
    public static void setTextTimeForat2(TextView textView, String timeStr) {
        if (!TextUtils.isEmpty(timeStr)) {
            long time = Long.decode(timeStr) * 1000;
            textView.setText(DateUtils.getDateToString2(time));
        }

    }

    @BindingAdapter({"timeFormat"})
    public static void setTextTimeFormat(TextView textView, String timeStr) {
        if (!TextUtils.isEmpty(timeStr)) {
            long time = Long.decode(timeStr) * 1000;
            textView.setText(DateUtils.getDateToString2(time));
        }
    }

    @BindingAdapter({"timeDate"})
    public static void setTextTimeDate(TextView textTimeDate, String timeStr) {
        if (!TextUtils.isEmpty(timeStr)) {
            long time = Long.decode(timeStr) * 1000;
            textTimeDate.setText(DateUtils.getDateToString2(time));
        }
    }
}
