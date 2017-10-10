package com.lshl.ui.info.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.base.HttpResult;
import com.lshl.bean.MailFriendBean;
import com.lshl.databinding.ItemLayoutMailfriendBinding;
import com.lshl.db.HxUserDao;
import com.lshl.db.bean.HxUserBean;
import com.lshl.ui.info.activity.AddFriendActivity;

import java.util.List;

/**
 * 项目名称：鲁商互联
 * 类描述：
 * 创建人：吕清锋
 * 创建时间：2017/3/21 21:31
 * 修改备注：
 */
public class MailFriendAdaper extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutMailfriendBinding>> {
    private List<MailFriendBean.InfoBean> mList;
    private BaseActivity activity;

    public MailFriendAdaper(List<MailFriendBean.InfoBean> mList, BaseActivity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @Override
    public BindingViewHolder<ItemLayoutMailfriendBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_mailfriend, parent, false);
        return new BindingViewHolder<>(ItemLayoutMailfriendBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutMailfriendBinding> holder, int position) {
        final MailFriendBean.InfoBean infoBean = mList.get(position);
        Glide.with(holder.mContext).load(ApiClient.getHxFriendsImage(infoBean.getAvatar())).error(R.drawable.account_logo).into(holder.getBinding().headImage);
        holder.getBinding().name.setText(infoBean.getRealname());
        HxUserDao hxUserDao = new HxUserDao();
        List<HxUserBean> userList = hxUserDao.getUserDetailsList();
        if ("0".equals(infoBean.getInvitation())) {
            holder.getBinding().button.setBackgroundResource(R.drawable.bg_gray_btn);
            holder.getBinding().button.setText("已邀请");
        }
        if (infoBean.getIsmember() == 1) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getHx_id().equals(infoBean.getHx_id())) {
                    holder.getBinding().button.setBackgroundResource(R.drawable.bg_gray_btn);
                    holder.getBinding().button.setText("已添加");
                } else {
                    holder.getBinding().button.setBackgroundResource(R.drawable.bg_blue_btn);
                    holder.getBinding().button.setText("添加");
                }
            }

        } else {
            holder.getBinding().button.setBackgroundResource(R.drawable.bg_blue_btn);
            holder.getBinding().button.setText("邀请");
        }
        holder.getBinding().button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infoBean.getIsmember() == 1) {
                    AddFriendActivity.actionStart(activity, infoBean.getHx_id(), 200);
                } else {
                    addFriends(infoBean.getPhone().replaceAll(" ", ""), LoginHelper.getInstance().getUserBean().getUid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void addFriends(String phone, String uid) {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object).addFriend(phone, uid), new ProgressSubscriber<HttpResult<String>>(activity, new SubscriberOnNextListener<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                activity.onRefresh(true);
            }
        }));
    }
}
