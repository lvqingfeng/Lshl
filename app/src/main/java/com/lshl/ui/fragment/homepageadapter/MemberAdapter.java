package com.lshl.ui.fragment.homepageadapter;

import android.app.Activity;
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
import com.lshl.bean.HomePageBean;
import com.lshl.databinding.ItemLayoutHomePageMemberBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/******
 * Created by 吕清锋 on 2017/3/14.
 */

public class MemberAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutHomePageMemberBinding>> {
    private Activity activity;
    private List<HomePageBean.InfoBean.MemberBean> memberBeanList;
    private ArrayList<String> mImages;
    public MemberAdapter(Activity activity, List<HomePageBean.InfoBean.MemberBean> memberBeanList) {
        this.activity = activity;
        this.memberBeanList = memberBeanList;
        mImages=new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ItemLayoutHomePageMemberBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_home_page_member,parent,false);
        return new BindingViewHolder<>(ItemLayoutHomePageMemberBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutHomePageMemberBinding> holder, int position) {
        final HomePageBean.InfoBean.MemberBean bean = memberBeanList.get(position);
        holder.getBinding().title.setText(bean.getRealname());
        holder.getBinding().time.setText(DateUtils.getDateToString2(Long.decode(bean.getAdd_time())*1000));
        Glide.with(holder.mContext).load(ApiService.BASE_URL+bean.getAvatar())
                .error(R.drawable.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImages.clear();
                mImages.add(ApiService.BASE_URL+bean.getAvatar());
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
        return memberBeanList.size();
    }
}
