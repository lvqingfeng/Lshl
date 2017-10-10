package com.lshl.ui.me.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.DynamicNewsInfo;
import com.lshl.databinding.ItemLayoutDynamicNewsInfoBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * Created by Administrator on 2017/2/25.
 */

public class DynamicNewsInfoAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutDynamicNewsInfoBinding>> {
    private List<DynamicNewsInfo.InfoBean.ListBean> mList;
    private BaseActivity activity;
    private ArrayList<String> mImages;


    public DynamicNewsInfoAdapter(List<DynamicNewsInfo.InfoBean.ListBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
        mImages=new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ItemLayoutDynamicNewsInfoBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_dynamic_news_info,parent,false);
        return new BindingViewHolder<>(ItemLayoutDynamicNewsInfoBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutDynamicNewsInfoBinding> holder, int position) {
        final DynamicNewsInfo.InfoBean.ListBean listBean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(listBean.getAvatar())).error(R.mipmap.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().name.setText(listBean.getRealname());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(listBean.getDn_addtime())*1000));
        holder.getBinding().info.setText(listBean.getDn_info());
        if (ApiService.STATUS_SUC.equals(listBean.getDn_type())){
            holder.getBinding().dianzan.setVisibility(View.VISIBLE);
            holder.getBinding().commentInfo.setVisibility(View.GONE);
        }else {
            holder.getBinding().commentInfo.setVisibility(View.VISIBLE);
            holder.getBinding().dianzan.setVisibility(View.GONE);
            holder.getBinding().commentInfo.setText(listBean.getDn_comment_info());
        }
        holder.getBinding().headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImages.clear();
                mImages.add(ApiClient.getHxFriendsImage(listBean.getAvatar()));
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
