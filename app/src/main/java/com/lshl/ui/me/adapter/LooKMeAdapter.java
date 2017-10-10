package com.lshl.ui.me.adapter;

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
import com.lshl.bean.LookMeBean;
import com.lshl.databinding.ItemLayoutLookMeBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */

public class LooKMeAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutLookMeBinding>> {
    private List<LookMeBean.InfoBean.ListBean> mList;
    private BaseActivity activity;
    private ArrayList<String> mImages;
    public LooKMeAdapter(List<LookMeBean.InfoBean.ListBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
        mImages=new ArrayList<>();
    }


    @Override
    public BindingViewHolder<ItemLayoutLookMeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_look_me,parent,false);
        return new BindingViewHolder<>(ItemLayoutLookMeBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutLookMeBinding> holder, int position) {
        final LookMeBean.InfoBean.ListBean listBean = mList.get(position);
        holder.getBinding().address.setText(listBean.getOrigin_cityname());
        holder.getBinding().name.setText(listBean.getRealname());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(listBean.getRe_addtime())*1000));
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(listBean.getAvatar())).error(R.drawable.account_logo).into(holder.getBinding().image);
        holder.getBinding().image.setOnClickListener(new View.OnClickListener() {
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
