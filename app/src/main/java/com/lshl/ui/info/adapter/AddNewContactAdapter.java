package com.lshl.ui.info.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.bean.AddContactBean;
import com.lshl.databinding.ItemAddContactBinding;
import com.lshl.ui.me.imagegrid.photo_show.PhotoShowActivity;
import com.lshl.utils.DateUtils;
import com.lshl.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class AddNewContactAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemAddContactBinding>> {
    private Context mContext;
    private List<AddContactBean.InfoBean.ContactListBean> contactListBean;
    private String fromType;
    private String groupId;
    private ArrayList<String> mImages;

    public AddNewContactAdapter(Context context, List<AddContactBean.InfoBean.ContactListBean> list, String fromType, String groupId) {
        this.mContext = context;
        this.contactListBean = list;
        this.fromType = fromType;
        this.groupId = groupId;
        mImages = new ArrayList<>();
    }

    @Override
    public BindingViewHolder<ItemAddContactBinding> onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_contact, viewGroup, false);
        return new BindingViewHolder<>(ItemAddContactBinding.bind(view));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(BindingViewHolder<ItemAddContactBinding> holder, final int position) {
        final AddContactBean.InfoBean.ContactListBean bean = contactListBean.get(position);
        holder.getBinding().tvUserName.setText(bean.getRealname());
        holder.getBinding().tvUserAddress.setText(bean.getOrigin_cityname());
        if (!TextUtils.isEmpty(fromType)) {
            holder.getBinding().tvUserDistance.setVisibility(View.GONE);
            holder.getBinding().tvInviteJoin.setVisibility(View.VISIBLE);
        } else {
            holder.getBinding().tvUserDistance.setText(bean.getDistance());
        }
        String imageUrl = ApiClient.getHxFriendsImage(bean.getAvatar());

        if (TextUtils.isEmpty(bean.getAvatar()))
            Glide.with(mContext).load(R.drawable.account_logo).transform(new GlideCircleTransform(mContext)).into(holder.getBinding().ivHeadPortrait);
        else
            Glide.with(mContext)
                    .load(imageUrl)
                    //.transform(new GlideCircleTransform(mContext))
                    .error(R.drawable.account_logo)
                    .into(holder.getBinding().ivHeadPortrait);

        SpannableString spannableString = new SpannableString("  " + DateUtils.getAgeBirthDate(bean.getAge()));
        Drawable drawable;
        if ("1".equals(bean.getSex())) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_vector_mans, null);
            holder.getBinding().tvUserSex.setBackgroundResource(R.color.sexMan);
        } else {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_vector_womans, null);
            holder.getBinding().tvUserSex.setBackgroundResource(R.color.sexwoMan);
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.getBinding().tvUserSex.setText(spannableString);

        holder.getBinding().tvInviteJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "join--" + position);
                if (!TextUtils.isEmpty(groupId) && !TextUtils.isEmpty(bean.getHx_id()))
                    inviteToGroup(groupId, bean.getHx_id());
            }
        });
        holder.getBinding().ivHeadPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImages.clear();
                mImages.add(ApiClient.getHxFriendsImage(bean.getAvatar()));
                Intent intent = new Intent(mContext, PhotoShowActivity.class);
                intent.putStringArrayListExtra(PhotoShowActivity.IMAGE_DATA, mImages);
                intent.putExtra(PhotoShowActivity.SELECT_POSITION, 0);
                intent.putExtra(PhotoShowActivity.SHOW_PHOTO_TYPE, PhotoShowActivity.PREVIEW_RANDOM_TYPE);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactListBean.size();
    }

    public AddContactBean.InfoBean.ContactListBean getItem(int position) {
        return contactListBean.get(position);
    }

    /**
     * 邀请更多好友
     *
     * @param groupId
     * @param hxId
     */
    private void inviteToGroup(final String groupId, final String hxId) {
        final String st6 = mContext.getResources().getString(R.string.Add_group_members_fail);
        new Thread(new Runnable() {

            public void run() {
                Message message = new Message();
                try {
                    // 创建者调用add方法
                    EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
                    String[] members = {hxId};
                    if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())) {
                        EMClient.getInstance().groupManager().addUsersToGroup(groupId, members);
                    } else {
                        // 一般成员调用invite方法
                        EMClient.getInstance().groupManager().inviteUser(groupId, members, null);
                    }
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (final Exception e) {
                    message.what = 2;
                    message.obj = e.getMessage();
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "已发出群邀请", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
