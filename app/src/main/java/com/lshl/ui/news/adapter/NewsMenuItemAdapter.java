package com.lshl.ui.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.MenuItemBean;
import com.lshl.databinding.MyMenuTypeOneBinding;
import com.lshl.databinding.NewsMenu;
import com.lshl.ui.me.adapter.MeMenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Administrator on 2017/1/9.
 */

public class NewsMenuItemAdapter extends RecyclerView.Adapter<BindingViewHolder>{
    private List<MenuItemBean> mListData;
    private Context mContext;
    private LayoutInflater mInflater;
    private int[]  mMenuBg= {R.drawable.bg_round_blue,R.drawable.bg_round_yellow
            ,R.drawable.bg_round_deep_blue,R.drawable.bg_round_orange
            ,R.drawable.bg_round_yellow,R.drawable.bg_round_green};
    private int[] mMenuIcon = {R.drawable.ic_vector_dscs, R.drawable.ic_vector_project
            , R.drawable.ic_vector_reputation, R.drawable.ic_vector_trade
            , R.drawable.ic_vector_hot, R.drawable.ic_vector_recruit};
    public NewsMenuItemAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        mListData=new ArrayList<>();
        initData();
    }

    private void initData() {
        String[] menuStr = mContext.getResources().getStringArray(R.array.news_menu);
        for (int i = 0; i < menuStr.length; i++) {
            MenuItemBean bean = new MenuItemBean();
            bean.setMenuName(menuStr[i]);
            bean.setMenuRes(mMenuIcon[i]);
            bean.setMenuBg(mMenuBg[i]);
            mListData.add(bean);
        }
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootOne = mInflater.inflate(R.layout.item_layout_news_menu, parent, false);
        return new NewsMenuItemAdapter.ViewHolderTypeOne(rootOne);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        NewsMenu oneBinding = ((NewsMenuItemAdapter.ViewHolderTypeOne) holder).getBinding();
        MenuItemBean itemOneBean = mListData.get(position);
        oneBinding.image.setImageResource(itemOneBean.getMenuRes());
        oneBinding.image.setBackgroundResource(itemOneBean.getMenuBg());
        oneBinding.text.setText(itemOneBean.getMenuName());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
    private class ViewHolderTypeOne extends BindingViewHolder<NewsMenu> {


        ViewHolderTypeOne(View itemView) {
            super(NewsMenu.bind(itemView));
        }
    }
}

