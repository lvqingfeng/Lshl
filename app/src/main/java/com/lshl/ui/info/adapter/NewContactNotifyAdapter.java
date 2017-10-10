package com.lshl.ui.info.adapter;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.NewContactNotifyItemBinding;
import com.lshl.db.HxNewContactNotifyDao;
import com.lshl.db.InviteMessageDao;
import com.lshl.db.bean.HxNewContactNotifyBean;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.view.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：10月20日
 * 时间：15:25
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class NewContactNotifyAdapter extends RecyclerView.Adapter<BindingViewHolder<NewContactNotifyItemBinding>> {

    private List<HxNewContactNotifyBean> mListData;
    //    private Context mContext;
    private LayoutInflater mInflater;
    private HxNewContactNotifyDao mNewContactNotifyDao;
    private String avatar;
    private BaseActivity activity;
    private ArrayList<String> mImages;

    public NewContactNotifyAdapter(List<HxNewContactNotifyBean> list, BaseActivity activity) {
//        this.mContext = mContext;
        mInflater = LayoutInflater.from(activity);
        mListData = list;
        this.activity = activity;
        mNewContactNotifyDao = new HxNewContactNotifyDao();
        mImages = new ArrayList<>();
    }


    @Override
    public BindingViewHolder<NewContactNotifyItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_layout_new_contact_notify, parent, false);
        return new BindingViewHolder<>(NewContactNotifyItemBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<NewContactNotifyItemBinding> holder, int position) {
        final int fPosition = position;
        final HxNewContactNotifyBean bean = mListData.get(fPosition);
        holder.getBinding().setNotifyBean(bean);
        if (TextUtils.isEmpty(bean.getReason())) {
            holder.getBinding().tvReason.setText("请求加您为好友");
        } else {
            holder.getBinding().tvReason.setText("请求加您为好友：" + bean.getReason());
        }
        avatar = ApiClient.getHxFriendsImage(bean.getAvatar());

        if (TextUtils.isEmpty(bean.getAvatar())) {
            Glide.with(activity).load(R.drawable.ease_default_avatar).transform(new GlideRoundTransform(activity)).into(holder.getBinding().ivContactAvatar);
        } else {
            Glide.with(activity).load(ApiClient.getHxFriendsImage(bean.getAvatar())).transform(new GlideRoundTransform(activity)).into(holder.getBinding().ivContactAvatar);
        }

        holder.getBinding().tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickRequest(holder.getBinding().tvAgree, fPosition, "已同意");

                new Thread() {
                    @Override
                    public void run() {
                        //收到好友邀请
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(bean.getHxId());
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        holder.getBinding().ivContactAvatar.setOnClickListener(new View.OnClickListener() {
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

    private void onClickRequest(View itemParent, int position, String disposeRequest) {
        itemParent.setBackgroundColor(Color.WHITE);
        ContentValues values = new ContentValues();
        values.put(InviteMessageDao.COLUMN_NAME_IS_DISPOSE, 1);
        values.put(InviteMessageDao.COLUMN_NAME_DISPOSE_REQUEST, disposeRequest);
        mNewContactNotifyDao.updateContactNotify(mListData.get(position).getId(), values);
        HxNewContactNotifyBean message = mListData.get(position);
        message.setDispose(true);
        message.setUnRead(false);
        message.setDisposeRequest(disposeRequest);
        notifyItemChanged(position);
    }

}
