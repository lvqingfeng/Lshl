package com.lshl.ui.me.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.PraiseListBean;
import com.lshl.databinding.ClickPraiseItemBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class DianzanAdapter extends RecyclerView.Adapter<BindingViewHolder<ClickPraiseItemBinding>> {
    private List<PraiseListBean.ListBean> mListData;
    private BaseActivity activity;
    private ArrayList<String> mImages;
    public DianzanAdapter(List<PraiseListBean.ListBean> mListData, BaseActivity activity) {
        this.mListData = mListData;
        this.activity = activity;
        mImages=new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ClickPraiseItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_click_praise,parent,false);
        return new BindingViewHolder<>(ClickPraiseItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ClickPraiseItemBinding> holder, int position) {
        final PraiseListBean.ListBean bean = mListData.get(position);
        holder.getBinding().setBean(bean);
        holder.getBinding().headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImages.clear();
                mImages.add(ApiClient.getHxFriendsImage(bean.getAvatar()));
                Intent intent = new Intent(holder.mContext, PhotoShowActivity.class);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}
