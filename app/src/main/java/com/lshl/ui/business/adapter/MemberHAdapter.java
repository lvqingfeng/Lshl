package com.lshl.ui.business.adapter;

import android.app.Activity;
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
import com.lshl.bean.CommerceMemberBean;
import com.lshl.databinding.ItemLayoutMemberBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */

public class MemberHAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutMemberBinding>> {
    private List<CommerceMemberBean.InfoBean.HBean> mList;
    private BaseActivity activity;
    private ArrayList<String> mImages;
    public MemberHAdapter(List<CommerceMemberBean.InfoBean.HBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
        mImages=new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ItemLayoutMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_member,parent,false);
        return new BindingViewHolder<>(ItemLayoutMemberBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutMemberBinding> holder, final int position) {
        final CommerceMemberBean.InfoBean.HBean hBean = mList.get(position);
        holder.getBinding().name.setText(hBean.getRealname());
        holder.getBinding().address.setText(hBean.getLive_cityname());
        holder.getBinding().position.setText(hBean.getJobs());
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(hBean.getAvatar())).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImages.clear();
                mImages.add(ApiClient.getHxFriendsImage(hBean.getAvatar()));
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
