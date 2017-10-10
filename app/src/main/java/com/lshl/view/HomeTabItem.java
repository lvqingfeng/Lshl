package com.lshl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.utils.DensityUtil;

/**
 * 作者：吕振鹏
 * 创建时间：08月22日
 * 时间：16:56
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class HomeTabItem extends LinearLayout {
    public static final int NO_SELECT_IMAGE = -1;
    private static final int DEFAULT_SELECT_IMAGE_SIZE = 23;
    private static float DEFAULT_SELECT_TEXT_SIZE = 11.5f;

    private ImageView mSelectedImage;
    private ImageView mUnselectedImage;
    private int mSelectImageSize;
    private TextView mTitle;
    private TextView mNums;
    private boolean isNeedLogin;


    public boolean isNeedLogin() {
        return isNeedLogin;
    }

    public HomeTabItem setNeedLogin(boolean needLogin) {
        isNeedLogin = needLogin;
        return this;
    }

    public HomeTabItem(Context context) {
        this(context, null);
    }

    public HomeTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initViews(context);
    }

    public HomeTabItem setSelectedImageRes(int imageRes) {
        if (imageRes == NO_SELECT_IMAGE) return this;
        mSelectedImage.setImageResource(imageRes);
        return this;
    }

    public HomeTabItem setUnSelectedImageRes(int imageRes) {
        if (imageRes == NO_SELECT_IMAGE) return this;
        mUnselectedImage.setImageResource(imageRes);
        return this;
    }

    public HomeTabItem setTitleText(String titleText) {
        mTitle.setText(titleText);
        return this;
    }
    public HomeTabItem setNumText(String numText){
        mNums.setText(numText);
        return this;
    }
    public HomeTabItem setSelectImageSize(int imageSize) {


        LayoutParams lp = (LayoutParams) mSelectedImage.getLayoutParams();
        lp.width = DensityUtil.dip2px(mSelectedImage.getContext(), imageSize);
        lp.height = DensityUtil.dip2px(mSelectedImage.getContext(), imageSize);

        mSelectedImage.setLayoutParams(lp);
        mUnselectedImage.setLayoutParams(lp);

        return this;
    }

    public ImageView getSelectedImage() {
        return mSelectedImage;
    }

    public ImageView getUnselectedImage() {
        return mUnselectedImage;
    }

    public TextView getTitleTextView() {
        return mTitle;
    }

    public String getTitleText() {
        return mTitle.getText().toString();
    }

    /////////////////////////////////////////内部处理方法///////////////////////////////////////////

    /**
     * 初始化内部子View的属性
     *
     * @param context
     */
    private void initViews(Context context) {
        LayoutParams lp = new LayoutParams(DensityUtil.dip2px(context, DEFAULT_SELECT_IMAGE_SIZE), DensityUtil.dip2px(context, DEFAULT_SELECT_IMAGE_SIZE));
        mSelectedImage = new ImageView(context);
        mUnselectedImage = new ImageView(context);
        mSelectedImage.setLayoutParams(lp);
        mSelectedImage.setVisibility(GONE);
        mUnselectedImage.setLayoutParams(lp);

        mTitle = new TextView(context);
        mNums=new TextView(context);
        mTitle.setSingleLine();
        mNums.setSingleLine();
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.setMargins(0, DensityUtil.dip2px(context, 3), 0, 0);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,DensityUtil.dip2px(context,3),3,0);
        mNums.setLayoutParams(params);
        mNums.setTextSize(DEFAULT_SELECT_TEXT_SIZE);
        mTitle.setLayoutParams(textLp);
        mTitle.setTextSize(DEFAULT_SELECT_TEXT_SIZE);

        addView(mSelectedImage);
        addView(mUnselectedImage);
        addView(mTitle);
        addView(mNums);
    }

    /**
     * 初始化一些控件自身的属性
     */
    private void init() {
        setOrientation(VERTICAL);
        LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        setPadding(0, DensityUtil.dip2px(getContext(), 5), 0, 0);
        setGravity(Gravity.CENTER);
        setLayoutParams(lp);
    }


}
