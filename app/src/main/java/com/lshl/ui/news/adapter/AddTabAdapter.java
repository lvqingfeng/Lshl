package com.lshl.ui.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.NewsTabBean;
import com.lshl.databinding.NewsTabItemBinding;
import com.lshl.db.NewsTabDao;
import com.lshl.utils.NewsTabItemTouchHelperCallback;

import java.util.Collections;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月21日
 * 时间：22:52
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AddTabAdapter extends RecyclerView.Adapter<BindingViewHolder<NewsTabItemBinding>> implements NewsTabItemTouchHelperCallback.ItemTouchAdapter {

    private List<NewsTabBean> mListData;
    private NewsTabDao mNewsTabDao;

    public AddTabAdapter(List<NewsTabBean> mListData) {
        this.mListData = mListData;
        mNewsTabDao = new NewsTabDao();
    }

    @Override
    public BindingViewHolder<NewsTabItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_news_tab, parent, false);
        return new BindingViewHolder<>(NewsTabItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<NewsTabItemBinding> holder, int position) {
        NewsTabBean bean = mListData.get(position);
        bean.setPosition(position);
        mNewsTabDao.updateNewsTab(bean.getId(), bean);
        if (bean.isFixed()) {
            holder.getBinding().tvTab.setTextColor(0xff888888);
        }
        holder.getBinding().getRoot().setEnabled(!bean.isFixed());
        holder.getBinding().setBean(bean);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition == 0 || toPosition == 0) {
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mListData, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mListData, i, i - 1);
            }
        }
        Log.d("lvzp", "开始进行移动 fromPosition =  " + fromPosition + "---- toPosition = " + toPosition);
        notifyItemMoved(fromPosition, toPosition);
       /* NewsTabBean bean = mListData.get(fromPosition);
        bean.setPosition(toPosition);
        mNewsTabDao.updateNewsTab(bean.getId(), bean);*/
    }

    @Override
    public void onSwiped(int position) {

    }
}
