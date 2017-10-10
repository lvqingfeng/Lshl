package com.lshl.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：吕振鹏
 * 创建时间：09月19日
 * 时间：17:30
 * 版本：v1.0.0
 * 类描述：用于Fragment做懒加载的父类
 * 修改时间：
 */

public abstract class LazyFragment<D extends ViewDataBinding> extends BaseFragment<D> {
    /**
     * 判断控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 判断数据是否已加载完成
     */
    private boolean isLoadDataCompleted;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        isViewCreated = true;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            loadData();
        }
    }

    protected abstract void loadData();

}
