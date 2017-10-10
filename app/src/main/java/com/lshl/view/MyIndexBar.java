package com.lshl.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/11/17.
 */

public class MyIndexBar extends View {
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"
            , "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private int cellHeight;//字母所在表格的高度
    private int cellWidth;//字母所在表格的宽度
    private OnIndexBarChangeListener onIndexBarChangeListener;//索引变化的监听

    public void setOnIndexBarChangeListener(OnIndexBarChangeListener onIndexBarChangeListener) {
        this.onIndexBarChangeListener = onIndexBarChangeListener;
    }

    public MyIndexBar(Context context) {
        this(context, null);
    }

    public MyIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(20);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cellHeight == 0) cellHeight = getMeasuredHeight() / 26;//初始化格子的高度,为整个控件高度的1/26
        if (cellWidth == 0) cellWidth = getMeasuredWidth();//表格的宽度为控件的宽度
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            float x = 0;
            float y = 0;
            x = cellWidth / 2 - mPaint.measureText(word) / 2;//measureText返回指定字符串的宽度
            Rect rect = new Rect();//创建一个矩形
            mPaint.getTextBounds(word, 0, word.length(), rect);//返回与这个字符串有关的属性, 宽高度都有
            y = i * cellHeight + cellHeight / 2 + rect.height() / 2;
            mPaint.setColor(Color.parseColor(lastIndex == i ? "#000000" : "#000000"));// 如果当前绘制的字母刚好是点击或者是滑动到的字母 就改变颜色
            canvas.drawText(word, x, y, mPaint);
        }
    }

    private int lastIndex = -1;// 用于记录上次点击或者滑动的位置

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下去
            case MotionEvent.ACTION_MOVE://滑动

                int y = (int) event.getY();//获取当前的Y轴坐标
                int index = y / cellHeight;//获取到当前点击的区域相对于字母是什么索引
                if (lastIndex != index && index < words.length) {// 两次的索引不一样,并且当前触摸区域计算出来的索引小宇字母的集合
                    String word = words[index];
                    lastIndex = index;
                    Log.e("自定义控件", "" + word);
                    if (onIndexBarChangeListener != null) {
                        onIndexBarChangeListener.onIndexChange(word);
                    }
                }

                break;
            case MotionEvent.ACTION_UP://抬起
                lastIndex = -1;//抬起的时候恢复初始化
                break;

        }
        invalidate();//刷新界面
        return true;

    }

    public interface OnIndexBarChangeListener {
        void onIndexChange(String word);
    }
}