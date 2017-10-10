package com.lshl.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.AddImageItemBinding;
import com.lshl.view.GlideRoundTransform;

import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月11日
 * 时间：11:41
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class GoodsImageEditAdapter extends RecyclerView.Adapter<BindingViewHolder<AddImageItemBinding>> {

    private OnImageNumChangeCallback mCallback;
    private List<String> mListData;

    public GoodsImageEditAdapter(List<String> listData) {
        mListData = listData;
    }

    public void setOnImageNumChangeCallback(OnImageNumChangeCallback callback) {
        mCallback = callback;
    }

    @Override
    public BindingViewHolder<AddImageItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layut_add_image, parent, false);
        return new BindingViewHolder<>(AddImageItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<AddImageItemBinding> holder, int position) {
        final int fPosition = position;
        final BindingViewHolder<AddImageItemBinding> fHolder = holder;
        Glide.with(fHolder.mContext).load(mListData.get(fPosition)).transform(new GlideRoundTransform(holder.mContext, 5)).into(fHolder.getBinding().ivGoodsImg);
        fHolder.getBinding().ibDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = fHolder.getLayoutPosition();
                Log.d("removePosition", "当前删除的为：" + position);
                mListData.remove(position);
                notifyItemRemoved(position);
                if (mCallback != null) {
                    mCallback.onChangeCallback(mListData.size(), fPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public interface OnImageNumChangeCallback {
        void onChangeCallback(int num, int position);
    }

}
