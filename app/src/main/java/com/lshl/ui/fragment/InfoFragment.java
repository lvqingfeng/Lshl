package com.lshl.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.base.BaseFragment;
import com.lshl.databinding.FragmentInfoBinding;
import com.lshl.ui.MainActivity;
import com.lshl.ui.info.adapter.InfoFragmentPageAdapter;
import com.lshl.ui.info.fragment.InfoContactListFragment;
import com.lshl.ui.info.fragment.InfoConversationListFragment;
import com.lshl.ui.info.fragment.InfoGroupListFragment;
import com.lshl.ui.me.fragment.DynamicsListFragment;
import com.yunzhanghu.redpacketui.RedPacketConstant;

/**
 * 消息Fragment
 */
public class InfoFragment extends BaseFragment<FragmentInfoBinding> implements TabLayout.OnTabSelectedListener {

    private TabLayout mTitleTabLayout;

    private int mCurrentTabIndex = 0;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    private InfoContactListFragment contactListFragment;
    private InfoConversationListFragment conversationListFragment;

    public InfoFragment() {
        // Required empty public constructor
    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mTitleTabLayout = ((MainActivity) getActivity()).mInfoTabLayout;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }
    public int getUnreadMessageTotal(){
        int unReadMsgToatl=0;
        int chatRoomMSg=0;
        unReadMsgToatl = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation:EMClient.getInstance().chatManager().getAllConversations().values()){
            if (conversation.getType()== EMConversation.EMConversationType.ChatRoom){
                chatRoomMSg=chatRoomMSg+conversation.getUnreadMsgCount();
            }
        }
        return unReadMsgToatl-chatRoomMSg;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void initViews() {
        //注册消息的监听
        registerBroadcastReceiver();

        conversationListFragment = new InfoConversationListFragment();
        contactListFragment = new InfoContactListFragment();

        InfoFragmentPageAdapter adapter = new InfoFragmentPageAdapter(getChildFragmentManager());
        adapter.addFragmentData(conversationListFragment, "消息");
        adapter.addFragmentData(contactListFragment, "好友");
        adapter.addFragmentData(InfoGroupListFragment.newInstance(), "群组");
        adapter.addFragmentData(DynamicsListFragment.newInstance(), "动态");

        mDataBinding.infoViewPager.setAdapter(adapter);
        mDataBinding.infoViewPager.setOffscreenPageLimit(3);
        mTitleTabLayout.setupWithViewPager(mDataBinding.infoViewPager);

        mTitleTabLayout.addOnTabSelectedListener(this);
        mDataBinding.infoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              /*  if (position == 2 && positionOffsetPixels >= 150) {
                    PersonDynamicActivity.actionStart(getActivity());
                }
                //Toast.makeText(mContext, "position = " + position + "positionOffset = " + positionOffset + "positionOffsetPixels = " + positionOffsetPixels, Toast.LENGTH_SHORT).show();
                Log.d("onPageScrolled", "position = " + position + "-----positionOffset = " + positionOffset + "------positionOffsetPixels = " + positionOffsetPixels);
   */
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 注册广播进行监听消息的改变
     */
    private void registerBroadcastReceiver() {

        broadcastManager = LocalBroadcastManager.getInstance(mContext);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_NOTIFY);
        intentFilter.addAction(Constant.ACTION_CONTACT_REFRESU);
        intentFilter.addAction(Constant.ACTION_NEW_CONTACT_NOTIFY);
        intentFilter.addAction(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION);

        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
              /*  updateUnreadLabel();
                updateUnreadAddressLable();*/
                if (conversationListFragment != null) {
                    String action = intent.getAction();
                    if (action.equals(Constant.ACTION_GROUP_NOTIFY)) {
                        int dbId = intent.getIntExtra(Constant.GROUP_NOTIFY_EXTRA, -1);
                        //requestMemberBasic(message);
                        conversationListFragment.updateGroupNotifyUnreadCount(dbId);
                 /*   if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }*/
                    }
                    conversationListFragment.refresh();
                }
                if (mCurrentTabIndex == 1) {
                    if (contactListFragment != null) {
                        contactListFragment.onRefresh();
                    }
                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_CONTACT_REFRESU)) {
                    if (contactListFragment != null) {
                        contactListFragment.onRefresh();
                    }
                }
                //red packet code : 处理红包回执透传消息
                if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
                //end of red packet code
            }
        };

        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 取消注册消息的广播
     */
    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mCurrentTabIndex = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}
