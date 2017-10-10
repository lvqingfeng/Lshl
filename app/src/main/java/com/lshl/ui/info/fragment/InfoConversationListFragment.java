package com.lshl.ui.info.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.databinding.HeadMessageLayoutBinding;
import com.lshl.db.InviteMessageDao;
import com.lshl.db.bean.InviteMessage;
import com.lshl.ui.info.activity.RadarListActivity;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.ui.info.message.GroupNotifyManagerActivity;

import java.util.List;

/**
 * 信息 - 会话列表的Fragment
 */
public class InfoConversationListFragment extends EaseConversationListFragment {


    private HeadMessageLayoutBinding mHeadViewBinding;
    private InviteMessageDao mMessageDao;

    private boolean isAddHeadSuc;
    private View mFriendRadarHeadView;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMessageDao = new InviteMessageDao();
    }

    @Override
    public void onResume() {
        super.onResume();
        addHeadView();
    }

    /**
     * 更新群通知到未读个数
     *
     * @param dbId db的id
     */
    public void updateGroupNotifyUnreadCount(int dbId) {
        if (mMessageDao != null) {
            int unreadCount = mMessageDao.getUnreadMessagesCount();
            InviteMessage message = mMessageDao.getMessage(dbId);
            if (!isAddHeadSuc)
                addHeadView();

            if (mHeadViewBinding != null) {
                mHeadViewBinding.setInviterMessage(message);
                mHeadViewBinding.setUnreadCount(unreadCount);
            }

        }

    }

    @Override
    protected void initView() {
        super.initView();
        titleBar.setVisibility(View.GONE);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        mSearchBarView.setVisibility(View.GONE);
        //群通知的布局文件

        addHeadView();
        addFriendRadarHeadView();
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isAddHeadSuc) {
                    if (position == 0) {
                        if (view == mFriendRadarHeadView) {
                            RadarListActivity.actionStart(getActivity());
                        } else {
                            GroupNotifyManagerActivity.actionStart(InfoConversationListFragment.this);
                        }
                    } else if (position == 1) {
                        if (view == mFriendRadarHeadView) {
                            RadarListActivity.actionStart(getActivity());
                        } else {
                            GroupNotifyManagerActivity.actionStart(InfoConversationListFragment.this);
                        }
                    } else if (position > 0) {
                        position = position - 2;
                        onClickItem(position);
                    }
                } else {
                    if (position == 0) {
                        RadarListActivity.actionStart(getActivity());
                    } else {
                        position = position - 1;
                        onClickItem(position);
                    }
                }

            }
        });
    }

    private void addFriendRadarHeadView() {
        mFriendRadarHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_head_friend_radar_message_layout, null);
        conversationListView.addHeaderView(mFriendRadarHeadView);
    }

    private void addHeadView() {
        if (mMessageDao != null) {
            List<InviteMessage> inviteMessageList = mMessageDao.getMessagesList();
            if (inviteMessageList != null && inviteMessageList.size() > 0) {
                int size = inviteMessageList.size();
                InviteMessage message = inviteMessageList.get(0);
                int unreadCount = mMessageDao.getUnreadMessagesCount();
                View groupNotifyHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_head_message_layout, null);
                if (mHeadViewBinding == null)
                    mHeadViewBinding = HeadMessageLayoutBinding.bind(groupNotifyHeadView);
                if (size > 0) {
                    mHeadViewBinding.setUnreadCount(unreadCount);
                    mHeadViewBinding.setInviterMessage(message);
                    //防止添加成功后还会添加头，在这里对ListView的头布局进行判断处理
                    if (conversationListView.getHeaderViewsCount() <= 1) {
                        conversationListView.addHeaderView(groupNotifyHeadView);
                    }
                }
                isAddHeadSuc = true;
            }
        }
    }

    private void onClickItem(int position) {
        EMConversation conversation = conversationListView.getItem(position);
        String username = conversation.getUserName();
        int chatType;
        if (conversation.isGroup()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                // it's group chat
                chatType = Constant.CHATTYPE_CHATROOM;
            } else {
                chatType = Constant.CHATTYPE_GROUP;
            }

        } else
            chatType = EaseConstant.CHATTYPE_SINGLE;
        ChatActivity.actionStart(null, InfoConversationListFragment.this, username, chatType, 0);
    }
}
