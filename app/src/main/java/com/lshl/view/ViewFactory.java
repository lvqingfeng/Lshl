package com.lshl.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RadioButton;

import com.lshl.R;

/**
 * 作者：吕振鹏
 * 创建时间：12月30日
 * 时间：1:15
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class ViewFactory {

    public static RadioButton getNewsTabItem(Context context, String name) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.layout_news_tab, null);
        radioButton.setText(name);
        return radioButton;
    }

}
