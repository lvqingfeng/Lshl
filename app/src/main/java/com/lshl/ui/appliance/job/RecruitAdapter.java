package com.lshl.ui.appliance.job;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiService;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.RecruitBean;
import com.lshl.databinding.ItemLayoutRecruitBinding;
import com.lshl.utils.DateUtils;

import java.util.List;

/***
 * Created by Administrator on 2016/12/21.
 */

public class RecruitAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutRecruitBinding>> {

    private List<RecruitBean.InfoBean.ListBean> mList;
    private Constant.JobListType mJobType;

    public RecruitAdapter(List<RecruitBean.InfoBean.ListBean> mList, Constant.JobListType type) {
        this.mList = mList;
        mJobType = type;
    }

    @Override
    public BindingViewHolder<ItemLayoutRecruitBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_recruit, parent, false);
        return new BindingViewHolder<>(ItemLayoutRecruitBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutRecruitBinding> holder, int position) {

        final RecruitBean.InfoBean.ListBean bean = mList.get(position);
        holder.getBinding().companyName.setText(bean.getEr_title());
        holder.getBinding().education.setText(bean.getEr_education());
        holder.getBinding().address.setText(bean.getEr_cityname());
        holder.getBinding().money.setText(bean.getEr_min_money()+"K-"+bean.getEr_max_money()+"K/月");
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getEr_addtime()) * 1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getEn_img()).error(R.drawable.account_logo).into(holder.getBinding().companyLogo);
//        switch (mJobType) {
//            case COLLECT://收藏类型
//                holder.getBinding().ivCollection.setVisibility(View.VISIBLE);
//                holder.getBinding().ivCollection.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final int fPosition = holder.getLayoutPosition();
//                        RetrofitManager.toSubscribe(
//                                ApiClient.getApiService(
//                                        ApiService.class, RetrofitManager.RetrofitType.Object
//                                ).followRecruit(LoginHelper.getInstance().getUserToken(), bean.getEr_id(), "2")
//                                , new ProgressSubscriber<>(holder.mContext, new SubscriberOnNextListener<HttpResult<String>>() {
//                                    @Override
//                                    public void onNext(HttpResult<String> result) {
//                                        Toast.makeText(holder.mContext, "取消收藏成功", Toast.LENGTH_SHORT).show();
//                                        mList.remove(fPosition);
//                                        notifyItemRemoved(fPosition);
//                                    }
//                                })
//                        );
//                    }
//                });
//                break;
//            case DELIVER://投递类型
//                holder.getBinding().llDeliveryStatus.setVisibility(View.VISIBLE);
//                if (bean.getStatus().equals("1")) {
//                    holder.getBinding().ivDeliveryReadStatus.setVisibility(View.VISIBLE);
//                } else {
//                    holder.getBinding().ivDeliveryReadStatus.setVisibility(View.GONE);
//                }
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
