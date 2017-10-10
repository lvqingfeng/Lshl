package com.lshl.ui.info.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;

import com.lshl.Constant;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityGroupNotifyManagerBinding;
import com.lshl.db.bean.InviteMessage;
import com.lshl.db.InviteMessageDao;
import com.lshl.ui.info.adapter.GroupNotifyManageAdapter;
import com.lshl.utils.DiffCallBack;

import java.util.List;

public class GroupNotifyManagerActivity extends BaseActivity<ActivityGroupNotifyManagerBinding> {

    private InviteMessageDao mInviteMessageDao;
    private LocalBroadcastManager mLocalManger;
    private BroadcastReceiver mGroupNotifyBroadcast;

    private List<InviteMessage> mListData;
    private GroupNotifyManageAdapter mAdapter;


    public static void actionStart(Fragment fragment) {
        Intent intent = new Intent(fragment.getContext(), GroupNotifyManagerActivity.class);
        fragment.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    protected void initFieldBeforeMethods() {

        mLocalManger = LocalBroadcastManager.getInstance(mContext);

        if (mInviteMessageDao == null) {
            mInviteMessageDao = new InviteMessageDao();
        }
        //Log.d("群通知", "------- 群通知的数量----" + mInviteMessageDao.getMessagesList().size());
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("群通知", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView( true);
    }

    @Override
    protected void initViews() {

        registerReceiver();

        setupView();

        mListData = mInviteMessageDao.getMessagesList();
        mAdapter = new GroupNotifyManageAdapter(mContext, this, mListData, mInviteMessageDao);
        mDataBinding.recyclerNotifyList.setAdapter(mAdapter);

    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(Constant.ACTION_GROUP_NOTIFY);

        mGroupNotifyBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("---- 接受到的数据 ----");
                List<InviteMessage> newData = mInviteMessageDao.getMessagesList();
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mListData, newData));
                diffResult.dispatchUpdatesTo(mAdapter);
                mListData = newData;
                mAdapter.setDatas(mListData);
                mDataBinding.recyclerNotifyList.scrollToPosition(0);
            }
        };
        mLocalManger.registerReceiver(mGroupNotifyBroadcast, filter);
    }

    private void unregisterReceiver() {
        mLocalManger.unregisterReceiver(mGroupNotifyBroadcast);
    }

    private void setupView() {
        mDataBinding.recyclerNotifyList.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    protected void onPause() {
        super.onPause();
        mInviteMessageDao.setUnReadStatus(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_notify_manager;
    }


}
