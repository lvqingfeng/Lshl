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
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import static vi.com.gdi.bgl.android.java.EnvDrawText.pt;

/**
 * Created by Administrator on 2016/11/17.
 */

public class MemberPAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutMemberBinding>> {
    private List<CommerceMemberBean.InfoBean.PBean> pList;
    private BaseActivity activity;
    private ArrayList<String> mImages;
    public MemberPAdapter(BaseActivity activity, List<CommerceMemberBean.InfoBean.PBean> pList) {
        this.activity = activity;
        this.pList = pList;
        mImages=new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ItemLayoutMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_member,parent,false);
        return new BindingViewHolder<>(ItemLayoutMemberBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutMemberBinding> holder, final int position) {
        final CommerceMemberBean.InfoBean.PBean bean = pList.get(position);
        holder.getBinding().name.setText(bean.getRealname());
        holder.getBinding().address.setText(bean.getLive_cityname());
        holder.getBinding().position.setText(bean.getJobs());
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(bean.getAvatar())).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
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
        return pList.size();
    }
}
