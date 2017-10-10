package com.lshl.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lshl.base.LshlApplication;

import java.lang.ref.WeakReference;

/**
 * 作者：吕振鹏
 * 创建时间：12月30日
 * 时间：1:29
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewsTabLayout extends RadioGroup implements RadioGroup.OnCheckedChangeListener {

    private ViewPager mViewPager;

    private NewsTabPageChangeListener mPageChangeListener;

    private PagerAdapter mPagerAdapter;

    private DataSetObserver mPagerAdapterObserver;

    private int mSelectPosition = -1;

    public NewsTabLayout(Context context) {
        super(context);
        init();
    }

    public NewsTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnCheckedChangeListener(this);
        setOrientation(HORIZONTAL);
    }

    public void setupViewPager(ViewPager viewPager) {
        if (mViewPager != null) {
            if (mPageChangeListener != null) {
                mViewPager.removeOnPageChangeListener(mPageChangeListener);
            }
        }

        if (viewPager != null) {
            mViewPager = viewPager;

            if (mPageChangeListener == null) {
                mPageChangeListener = new NewsTabPageChangeListener(this);
            }
            viewPager.addOnPageChangeListener(mPageChangeListener);
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter);
            }
        }

    }

    private void setPagerAdapter(PagerAdapter adapter) {

        mPagerAdapter = adapter;

        if (mPagerAdapter != null && mPagerAdapterObserver != null) {
            // If we already have a PagerAdapter, unregister our observer
            mPagerAdapter.unregisterDataSetObserver(mPagerAdapterObserver);
        }
        if (adapter != null) {
            // Register our observer on the new adapter
            if (mPagerAdapterObserver == null) {
                mPagerAdapterObserver = new PagerAdapterObserver();
            }
            adapter.registerDataSetObserver(mPagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mViewPager != null) {
            int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (group.getChildAt(i) != null) {
                    RadioButton button = (RadioButton) group.getChildAt(i);
                    if (button.isChecked()) {
                        mViewPager.setCurrentItem(i);
                    }
                }
            }

        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        PagerAdapterObserver() {
        }

        @Override
        public void onChanged() {
            setSelectPosition(-1);
            populateFromPagerAdapter();
        }

        @Override
        public void onInvalidated() {
            populateFromPagerAdapter();
        }
    }

    private void populateFromPagerAdapter() {

        removeAllViews();

        if (mPagerAdapter != null) {
            int adapterCount = mPagerAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                RadioButton button = ViewFactory.getNewsTabItem(getContext(), (String) mPagerAdapter.getPageTitle(i));
                addView(button);
            }

            if (mViewPager != null && adapterCount > 0) {
                int curItem = mViewPager.getCurrentItem();
                if (curItem != getSelectPosition() && curItem < getChildCount()) {
                    ((RadioButton) getChildAt(curItem)).setChecked(true);
                    mSelectPosition = curItem;
                }
            }

        }

    }

    public static class NewsTabPageChangeListener implements ViewPager.OnPageChangeListener {

        private final WeakReference<NewsTabLayout> mRadioGroupRef;//当前的NewsTabLayout对象本身
        private final WeakReference<HorizontalScrollView> mHScrollViewRef;//NewsTabLayout嵌套的父组件

        private int mLastPosition = 0;//记录上一次的位置
        private int mCurrentPosition = 0;//记录当前的位置

        public NewsTabPageChangeListener(NewsTabLayout radioGroup) {
            mRadioGroupRef = new WeakReference<>(radioGroup);
            ViewParent groupParent = mRadioGroupRef.get().getParent();
            if (groupParent != null && groupParent instanceof HorizontalScrollView) {
                mHScrollViewRef = new WeakReference<>((HorizontalScrollView) groupParent);
            } else {
                mHScrollViewRef = null;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mRadioGroupRef.get().setSelectPosition(position);
            if (mRadioGroupRef.get().getChildAt(position) != null) {
                RadioButton button = (RadioButton) mRadioGroupRef.get().getChildAt(position);
                button.setChecked(true);
                if (mHScrollViewRef != null) {
                    int groupWidth = LshlApplication.sWindowWidth - 150;
                    if (button.getRight() >= groupWidth) {
                        int scrollX = button.getRight() - groupWidth;
                        mHScrollViewRef.get().smoothScrollBy(scrollX, 0);
                    } else if (button.getLeft() <= groupWidth) {
                        mCurrentPosition = button.getLeft();
                        if (mCurrentPosition < mLastPosition) {
                            int scrollX = button.getRight() - groupWidth;
                            mHScrollViewRef.get().smoothScrollBy(scrollX, 0);
                        }
                        mLastPosition = mCurrentPosition;
                    }
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public int getSelectPosition() {
        return mSelectPosition;
    }

    public void setSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
    }
}
