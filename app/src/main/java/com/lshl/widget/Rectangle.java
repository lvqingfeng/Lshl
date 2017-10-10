package com.lshl.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.lshl.R;

/**
 * 作者：吕振鹏
 * 创建时间：11月24日
 * 时间：11:36
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class Rectangle extends View {
    private int mProgress;
    private Paint mCurrentProgress;
    private Paint mMaxProgress;
    private Paint mPaintText;
    private int mWidth;
    private int mHeight;

    private int mTextSize;
    private int mTextColor;
    private int mProgressBg;

    public Rectangle(Context context) {
        super(context);
    }

    public Rectangle(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTextColor = Color.RED;
        mProgressBg = Color.YELLOW;
        mTextSize = 15;

        TypedArray b = context.obtainStyledAttributes(attrs, R.styleable.Rectangle);
        mTextSize = b.getDimensionPixelSize(R.styleable.Rectangle_percent_text_size, mTextSize);
        mTextColor = b.getColor(R.styleable.Rectangle_percent_text_color, mTextColor);
        mProgressBg = b.getColor(R.styleable.Rectangle_progressBarBgColor, mProgressBg);

        mCurrentProgress = new Paint();
        mCurrentProgress.setColor(mProgressBg);
        mCurrentProgress.setAntiAlias(true);

        mMaxProgress = new Paint();
        mMaxProgress.setColor(Color.WHITE);
        mMaxProgress.setAntiAlias(true);

        mPaintText = new Paint();
        mPaintText.setColor(mTextColor);
        mPaintText.setStrokeWidth(5);
        mPaintText.setAntiAlias(true);
        setRawTextSize(mTextSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);

        b.recycle();
    }


    public int getmProgress() {
        return mProgress;
    }


    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        if (mProgress >= 55) {
            mPaintText.setColor(Color.WHITE);
        }
        invalidate();
    }

    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     */
    public void setTextSize(int unit, float size) {
        Context c = getContext();
        Resources r;

        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        setRawTextSize(TypedValue.applyDimension(
                unit, size, r.getDisplayMetrics()));
    }

    private void setRawTextSize(float size) {
        if (size != mPaintText.getTextSize()) {
            mPaintText.setTextSize(size);
            requestLayout();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mWidth / 2 - 160, mHeight / 2 - 250, mWidth / 2 + 160, mHeight / 2 + 250, mMaxProgress);
        canvas.drawRect(mWidth / 2 - 160, mHeight / 2 + 250 - (mProgress / 100f * 500), mWidth / 2 + 160, mHeight / 2 + 250, mCurrentProgress);
        canvas.drawText(mProgress + "%", mWidth / 2, mHeight / 2, mPaintText);
    }
}
