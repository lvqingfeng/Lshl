package com.lshl.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by l_zp on 2016/1/31.
 * <p>
 * 这是一个字体管理类，用来引用我们的字体图标
 * <p>
 * 创建的基本步骤：
 * 1，在src文件夹下的main文件夹下新建一个assets文件
 * 2，将下载好的ttf文件放入的这个文件夹下，也可以在这个文件夹下在新建一个文件夹（也可以不新建）
 * 3，将ttf文件的路径通过静态方法暴露出去
 * <p>
 * 使用的基本步骤：
 * 1，将对应图标的编码放入到string文件中（也可以在自己新建一个文件类型与icons文件）
 * 其中的name是自己取得，value值为图标对应的编码
 * 2，在xml文件中的text属性直接饮用它
 * 3，在Java代码中利用IconFontManager.getTypeface（）方法获取Typeface对象
 * 4，然后利用帮助类的markAsIconContainer（）方法，将对应的控件和Typeface对象放入进去，就会将图标创建到TextView中
 */
public class IconFontManager {

    public static final String ROOT = "fonts/",
            FONT_NAME = ROOT + "lshl.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    /**
     * 将图标放入到指定的view控件中
     *
     * @param v        可以通过findViewById（）方法直接设置进来，不用类型转换
     * @param typeface Typeface类指定typeface 以及一个字体的特征
     */
    public static void markAsIconContainer(View v, Typeface typeface) {
        //图标一般都是包含在一个ViewGroup，比如一个RelativeLayout或者LinearLayout中。
        // 我们可以写一个方法，爬遍指定xml parent 并且递归的覆盖每个TextView的字体。
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                markAsIconContainer(child, typeface);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTypeface(typeface);
        }
    }


}
