package com.lshl.ui.appliance.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.InfoContrastBean;
import com.lshl.bean.MemberBasicInfoBean;
import com.lshl.databinding.SellerInfoItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：11月03日
 * 时间：11:52
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SellerInfoItemAdapter extends RecyclerView.Adapter<BindingViewHolder<SellerInfoItemBinding>> {

    private List<InfoContrastBean> mListData;

    public SellerInfoItemAdapter(Context context, MemberBasicInfoBean.InfoBean infoBean) {
        mListData = new ArrayList<>();
        init(context, infoBean.getRealname(), MemberBasicInfoBean.InfoBean.getSex(infoBean.getSex()), infoBean.getAge(), infoBean.getAll_live(), infoBean.getAll_origin());
    }

    private void init(Context context, String... values) {

        String[] itemNames = context.getResources().getStringArray(R.array.seller_info_menu);
        for (int i = 0; i < itemNames.length; i++) {
            InfoContrastBean bean = new InfoContrastBean();
            bean.setName(itemNames[i]);
            bean.setValue(values[i]);
            mListData.add(bean);
        }
    }

    @Override
    public BindingViewHolder<SellerInfoItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_seller_info_list, parent, false);
        return new BindingViewHolder<>(SellerInfoItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<SellerInfoItemBinding> holder, int position) {
        holder.getBinding().setInfo(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
}
