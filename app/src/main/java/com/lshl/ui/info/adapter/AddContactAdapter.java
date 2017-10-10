package com.lshl.ui.info.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.FindUserBean;
import com.lshl.databinding.FindFriendItemBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月15日
 * 时间：23:48
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AddContactAdapter extends RecyclerView.Adapter<BindingViewHolder<FindFriendItemBinding>> {

    private List<FindUserBean> mListData;
    private LayoutInflater mInflater;
    private String mSearchStr;
    private Context mContext;
    private BaseActivity activity;
    private ArrayList<String> mImages;

    public AddContactAdapter(BaseActivity activity, Context mContext) {
        mInflater = LayoutInflater.from(mContext);
        this.activity = activity;
        this.mContext = mContext;
        mImages = new ArrayList<>();
    }

    public void addAllData(List<FindUserBean> list, String searchStr) {
        if (mListData == null) {
            mListData = list;
        } else {
            mListData.addAll(list);
        }

        mSearchStr = searchStr;
        notifyItemRangeInserted(0, mListData.size());
    }

    public void cleanData() {
        if (mListData != null) {
            mListData.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public BindingViewHolder<FindFriendItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_layout_find_friend, parent, false);
        return new BindingViewHolder<>(FindFriendItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<FindFriendItemBinding> holder, int position) {

        final FindUserBean bean = mListData.get(position);
        holder.getBinding().setFriendBean(bean);
        holder.getBinding().tvSexIcon.setTypeface(Constant.getIconTypeface(mContext));

        //创建一个 SpannableString对象
        String realName = bean.getRealname();
        TextView tvRealName = holder.getBinding().tvFriendName;

        if (TextUtils.isEmpty(realName))
            tvRealName.setVisibility(View.GONE);
        else {
            tvRealName.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(mSearchStr) && !TextUtils.isEmpty(realName)) {
                SpannableString msp = new SpannableString(realName);
                int start = realName.indexOf(mSearchStr);
                int end = 0;
                if (start != -1)
                    end = start + mSearchStr.length();
                else
                    start = 0;
                msp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.indicatorColor)), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                tvRealName.setText(msp);
            }
        }
        if (bean.getSex() == 1) {
            holder.getBinding().sex.setImageResource(R.drawable.ic_vector_mans);
        } else {
            holder.getBinding().sex.setImageResource(R.drawable.ic_vector_womans);
        }
        ImageView showView = holder.getBinding().ivFriendHead;

        String imageUrl = ApiClient.getHxFriendsImage(bean.getAvatar());

        if (TextUtils.isEmpty(bean.getAvatar()))
            Glide.with(mContext).load(R.drawable.ease_default_avatar).transform(new GlideCircleTransform(mContext)).into(showView);
        else
            Glide.with(mContext)
                    .load(imageUrl)
                    //.transform(new GlideCircleTransform(mContext))
                    .error(R.drawable.ease_default_avatar)
                    .into(showView);
        holder.getBinding().ivFriendHead.setOnClickListener(new View.OnClickListener() {
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
        return mListData == null ? 0 : mListData.size();
    }

    public FindUserBean getItem(int position) {
        return mListData.get(position);
    }
}
