package com.lshl.ui.info.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.easeui.EaseConstant;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BindingViewHolder;
import com.lshl.databinding.ItemLayoutGroupNotifyBinding;
import com.lshl.db.InviteMessageDao;
import com.lshl.db.bean.InviteMessage;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.view.GlideRoundTransform;

import java.util.List;

import static com.lshl.db.bean.InviteMessage.InviteMesageStatus.BEAPPLYED;
import static com.lshl.db.bean.InviteMessage.InviteMesageStatus.BEINVITEED;


/**
 * 作者：吕振鹏
 * 创建时间：10月14日
 * 时间：12:27
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class GroupNotifyManageAdapter extends RecyclerView.Adapter<BindingViewHolder<ItemLayoutGroupNotifyBinding>> {

    private LayoutInflater mInflater;
    private List<InviteMessage> mListData;
    private InviteMessageDao mInviteMessageDao;
    private EMGroupManager mGroupManager;

    private Activity mActivity;
    private LocalBroadcastManager mBroadcastManager;

    public GroupNotifyManageAdapter(Context context, Activity activity, List<InviteMessage> listData, InviteMessageDao inviteMessageDao) {
        mInflater = LayoutInflater.from(context);
        mListData = listData;
        mActivity = activity;
        mInviteMessageDao = inviteMessageDao;
        mGroupManager = EMClient.getInstance().groupManager();
        mBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    public void setDatas(List<InviteMessage> data) {
        mListData = data;
    }


    @Override
    public BindingViewHolder<ItemLayoutGroupNotifyBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_layout_group_notify, parent, false);
        return new BindingViewHolder<>(ItemLayoutGroupNotifyBinding.bind(rootView));
    }

    @Override
    public void onBindViewHolder(final BindingViewHolder<ItemLayoutGroupNotifyBinding> holder, int position) {
        final int fPosition = position;
        final Context context = holder.getBinding().getRoot().getContext();
        final InviteMessage message = mListData.get(fPosition);
        holder.getBinding().setInfoBean(message);

        TextView tvReason = holder.getBinding().tvNotifyContent;
        TextView tvStatus = holder.getBinding().tvGroupStatus;
        final InviteMessage.InviteMesageStatus status = message.getStatus();
        if (status != null) {
            switch (status) {
                //邀请入群（群）
                case BEINVITEED:
                    tvReason.setVisibility(View.VISIBLE);
                    tvReason.setText(context.getString(R.string.group_inviter) + message.getRealname());
                    tvStatus.setText(message.getStatus().getValue());
                    break;
                //邀请入群，拒绝和同意（个人）
                case GROUPINVITATION_DECLINED:
                case GROUPINVITATION_ACCEPTED:
                    tvStatus.setText(status.getValue() + " " + message.getGroupName());
                    if (!TextUtils.isEmpty(message.getReason())) {
                        tvReason.setVisibility(View.VISIBLE);
                        tvReason.setText(message.getReason());
                    } else {
                        tvReason.setVisibility(View.GONE);
                    }

                    break;
                //申请入群（个人）
                case BEAPPLYED:
                    tvReason.setVisibility(View.VISIBLE);
                    tvReason.setText(message.getReason());
                    tvStatus.setText(message.getStatus().getValue() + " " + message.getGroupName());
                    break;
                //申请入群，拒绝和同意（群）
                case BEAGREED:
                case BEREFUSED:
                    tvReason.setVisibility(View.VISIBLE);
                    tvReason.setText(context.getString(R.string.group_handler) + message.getRealname());
                    tvStatus.setText(message.getStatus().getValue());
                    break;
                case GROUP_DISSOLVED:
                case GROUP_REMOVE:
                    tvReason.setVisibility(View.GONE);
                    tvStatus.setText(message.getRealname() + " " + message.getStatus().getValue() + " " + message.getGroupName());
                    break;

            }

        }


        holder.getBinding().btnAgree.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        message.setUnreadStatus(false);
                        mInviteMessageDao.setUnReadStatus(false);
                        onClickRequest(holder.getBinding().getRoot(), fPosition, "已同意");
                        if (status == BEAPPLYED) {
                            /**
                             * username - 申请人username
                             * groupId - 要申请加入的群id
                             */
                            mGroupManager.asyncAcceptApplication(message.getFrom(), message.getGroupId(), new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    showToast("操作成功");
                                }

                                @Override
                                public void onError(int i, String s) {
                                    System.out.println("---- 操作错误信息 ---- i = " + i + " -- s =" + s);
                                    showToast("操作错误：" + s);
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        } else if (status == BEINVITEED) {
                            mGroupManager.asyncAcceptInvitation(message.getGroupId(), message.getUserId(), new EMValueCallBack<EMGroup>() {
                                @Override
                                public void onSuccess(EMGroup emGroup) {
                                    showToast("加入群成功");
                                    mBroadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
                                }

                                @Override
                                public void onError(int i, String s) {
                                    showToast("操作错误：" + s);
                                }
                            });
                        }
                    }

                }

        );

        holder.getBinding().btnRefuse.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {

                        message.setUnreadStatus(false);
                        mInviteMessageDao.setUnReadStatus(false);

                        onClickRequest(holder.getBinding().getRoot(), fPosition, "已拒绝");
                        if (status == BEAPPLYED) {
                            /**
                             * username - 被拒绝的用户的username
                             * groupId - 群id
                             * reason - 拒绝理由
                             */
                            mGroupManager.asyncDeclineApplication(message.getFrom(), message.getGroupId(), "", new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    showToast("操作成功");
                                }

                                @Override
                                public void onError(int i, String s) {
                                    showToast("操作错误:" + s);
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        } else if (status == BEINVITEED) {
                            mGroupManager.asyncDeclineInvitation(message.getGroupId(), message.getUserId(), "不想加入", new EMCallBack() {
                                @Override
                                public void onSuccess() {
                                    showToast("操作成功");
                                }

                                @Override
                                public void onError(int i, String s) {
                                    showToast("操作错误:" + s);
                                }

                                @Override
                                public void onProgress(int i, String s) {

                                }
                            });
                        }

                    }
                }

        );
        //开聊天
        holder.getBinding().btnStartChat.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        holder.getBinding().getRoot().setBackgroundColor(Color.WHITE);
                        ChatActivity.actionStart(mActivity, null, mListData.get(fPosition).getGroupId(), EaseConstant.CHATTYPE_GROUP, 0);
                    }
                }

        );


        if (message.isUnread())
            holder.getBinding().getRoot().setBackgroundColor(Color.WHITE);
//        else
//            holder.getBinding().getRoot().setBackgroundColor(ContextCompat.getColor(context, R.color.text_yellow));
        if (message.isGroup()) {
            if (TextUtils.isEmpty(message.getGroupImage()))
                Glide.with(context).load(R.drawable.em_group_icon).transform(new GlideRoundTransform(context)).into(holder.getBinding().ivGroupImage);
            else
                Glide.with(context).load(ApiClient.getHxGroupImage(message.getGroupImage())).transform(new GlideRoundTransform(context)).into(holder.getBinding().ivGroupImage);
        } else {
            if (TextUtils.isEmpty(message.getAvatar()))
                Glide.with(context).load(R.drawable.ease_default_avatar).transform(new GlideRoundTransform(context)).into(holder.getBinding().ivGroupImage);
            else
                Glide.with(context).load(ApiClient.getHxFriendsImage(message.getAvatar())).transform(new GlideRoundTransform(context)).into(holder.getBinding().ivGroupImage);
        }

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
        mInviteMessageDao.updateMessage(mListData.get(position).getId(), values);
        InviteMessage message = mListData.get(position);
        message.setDispose(true);
        message.setDisposeRequest(disposeRequest);
        notifyItemChanged(position);
    }

    private void showToast(final String mgs) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, mgs, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
