package com.lshl.ui.info.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.RecommendFriendsBean;
import com.lshl.databinding.ItemLayoutRecommendBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/16.
 */

public class RecommendAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutRecommendBinding>> {
    private List<RecommendFriendsBean.InfoBean.ListBean> mList;
    private BaseActivity activity;
    private ArrayList<String> mImages;
    public RecommendAdapter(List<RecommendFriendsBean.InfoBean.ListBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
        mImages=new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ItemLayoutRecommendBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_recommend, parent, false);
        return new BindingViewHolder<>(ItemLayoutRecommendBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutRecommendBinding> holder, int position) {
        final RecommendFriendsBean.InfoBean.ListBean bean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).error(R.drawable.ease_default_avatar).into(holder.getBinding().headImage);
        holder.getBinding().name.setText(bean.getRealname());
        holder.getBinding().address.setText(bean.getOrigin_cityname() + bean.getOrigin_county());
        holder.getBinding().distance.setText(bean.getDistance());

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
        return mList.size();
    }
}
