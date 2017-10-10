package com.lshl.ui.info.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lshl.Constant;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityNewContactNotifyBinding;
import com.lshl.db.HxNewContactNotifyDao;
import com.lshl.db.HxUserDao;
import com.lshl.db.bean.HxNewContactNotifyBean;
import com.lshl.db.bean.HxUserBean;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.info.adapter.NewContactNotifyAdapter;

import java.util.List;

/**
 * 新朋友通知的页面
 */
public class NewContactNotifyActivity extends BaseActivity<ActivityNewContactNotifyBinding> {

    private boolean isFriend =true;
    private NewContactNotifyAdapter mAdapter;

    private HxNewContactNotifyDao mNewContactNotifyDao;

    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;
    private List<HxNewContactNotifyBean> mListData;
    private HxUserDao hxUserDao;

    public static void actionStart(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), NewContactNotifyActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNewContactNotifyDao.setUnreadStatus(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("新朋友", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
    }

    @Override
    protected void initViews() {
        hxUserDao = new HxUserDao();
        mNewContactNotifyDao = new HxNewContactNotifyDao();
        mListData = mNewContactNotifyDao.getContactNotifyList();

        mDataBinding.recyclerNewContactNotify.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new NewContactNotifyAdapter(mListData,NewContactNotifyActivity.this);
        mDataBinding.recyclerNewContactNotify.setAdapter(mAdapter);
        mDataBinding.recyclerNewContactNotify.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerNewContactNotify) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String hxId = mListData.get(vh.getLayoutPosition()).getHxId();
                checkIsFriend(hxId);
                HxMemberDetailsActivity.actionStart(NewContactNotifyActivity.this,hxId,"",isFriend);
            }
        });
        registerReceiver();
    }
    private void checkIsFriend(String hxId) {
        List<HxUserBean> userList = hxUserDao.getUserDetailsList();
        for (HxUserBean bean : userList) {
            //将本地存储的环信id与传递过来的环信id进行匹配，判断是否是好友
            if (bean.getHx_id().equals(hxId)) {
                isFriend = false;
                return;
            }
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_contact_notify;
    }

    private void registerReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {

                    HxNewContactNotifyBean bean = (HxNewContactNotifyBean) intent.getSerializableExtra(Constant.NEW_CONTACT_NOTIFY_EXTRA);

                    for (int i = 0; i < mListData.size(); i++) {
                        HxNewContactNotifyBean notifyBean = mListData.get(i);
                        if (notifyBean.getHxId().equals(bean.getHxId())) {
                            mListData.remove(notifyBean);
                            mAdapter.notifyItemRemoved(i);
                        }
                    }

                    mListData.add(0, bean);
                    mAdapter.notifyItemInserted(0);
                    mDataBinding.recyclerNewContactNotify.scrollToPosition(0);
                }
            }
        };
        mBroadcastManager.registerReceiver(mBroadcastReceiver, new IntentFilter(Constant.ACTION_NEW_CONTACT_NOTIFY));
    }

    private void unRegisterReceiver() {
        mBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

}
