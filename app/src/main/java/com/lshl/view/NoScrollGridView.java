package com.lshl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/1/29.
 */
public class NoScrollGridView extends GridView {


    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /**
     * 条目点击事件
     */
    public interface OnItemClickListener {
        /**
         * 点击的回调
         *
         * @param viewGroup 点击的列表对象
         * @param view      点击的具体条目
         * @param isChecked 是否被选中了
         */
        void onItemClick(ViewGroup viewGroup, View view, boolean isChecked);

    }

}
