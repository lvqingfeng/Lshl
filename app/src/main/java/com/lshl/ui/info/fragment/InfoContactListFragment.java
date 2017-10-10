package com.lshl.ui.info.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.ChatHelper;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.databinding.HeadNewContactLayoutBinding;
import com.lshl.db.HxNewContactNotifyDao;
import com.lshl.db.HxUserDao;
import com.lshl.db.bean.HxUserBean;
import com.lshl.task.CheckupHxUserDetailsTask;
import com.lshl.task.TaskBase;
import com.lshl.ui.info.activity.AddContactActivity;
import com.lshl.ui.info.activity.HxMemberDetailsActivity;
import com.lshl.ui.info.activity.MailListActivity;
import com.lshl.ui.info.adapter.ContactSearchAdapter;
import com.lshl.ui.info.message.NewContactNotifyActivity;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 信息 - 好友列表Fragment
 */
public class InfoContactListFragment extends EaseContactListFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int REQUEST_CODE_NEW_CONTACT_NOTIFY = 0x000131;

    private HxUserDao mHxUserDao;
    private List<String> mListHxUserName;
    private List<HxUserBean> mListDbUserData;

    private LocalBroadcastManager mBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;

    private HxNewContactNotifyDao mNewContactNotifyDao;

    private HeadNewContactLayoutBinding mHeadNewContactLayoutBinding;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean isUpdata = true;
    private ContactSearchAdapter contactSearchAdapter;
    private List<HxUserBean> hxUserBeenList;

    @Override
    public void onResume() {
        super.onResume();
        updateUnreadNotifyCount();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterReceiver();
    }

    @Override
    protected void initView() {
        super.initView();

        if (mHxUserDao == null)
            mHxUserDao = new HxUserDao();

        mBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        //添加的新朋友ListView的头
        View addFriends = getActivity().getLayoutInflater().inflate(R.layout.item_head_add_friends_layout, null);
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskBase.checkUserIsRealname(getContext(), new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        startActivity(new Intent(getContext(), AddContactActivity.class));
                    }
                });
            }
        });
        //添加的新朋友通知ListView的头
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.item_head_new_friends_layout, null);
        //添加通讯录好友的ListView的头
        View addressList = getActivity().getLayoutInflater().inflate(R.layout.item_head_add_address_list_layout, null);
        mHeadNewContactLayoutBinding = HeadNewContactLayoutBinding.bind(rootView);
        listView.addHeaderView(addFriends);
        listView.addHeaderView(rootView);
        listView.addHeaderView(addressList);
        updateUnreadNotifyCount();

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskBase.checkUserIsRealname(getContext(), new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        NewContactNotifyActivity.actionStart(InfoContactListFragment.this, REQUEST_CODE_NEW_CONTACT_NOTIFY);
                    }
                });
            }
        });

        addressList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskBase.checkUserIsRealname(getContext(), new TaskBase.CheckUserAuthortyCallBack() {
                    @Override
                    public void onSuccess(AuthorityCheckBean bean) {
                        MailListActivity.actionStart(getActivity());
                    }
                });
            }
        });

        titleBar.setVisibility(View.GONE);
        LinearLayout parentLayout = (LinearLayout) getActivity().findViewById(R.id.parent);
        //隐藏暂时不需要的控件
        final EditText search_bar_view = (EditText) parentLayout.findViewById(R.id.search_bar_view).findViewById(R.id.query);
        search_bar_view.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        search_bar_view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    mListDbUserData = mHxUserDao.getUserDetailsList();
                    hxUserBeenList = new ArrayList<>();
                    if (mListDbUserData != null && mListDbUserData.size() > 0) {
                        for (int i = 0; i < mListDbUserData.size(); i++) {
                            if (mListDbUserData.get(i).getRealname().contains(search_bar_view.getText().toString().trim())) {
                                hxUserBeenList.add(mListDbUserData.get(i));
                            }
                        }
                    }
                    contactSearchAdapter = new ContactSearchAdapter(getActivity(), hxUserBeenList);
                    listViewSearch.setAdapter(contactSearchAdapter);
                    return true;
                }
                return false;
            }
        });
        search_bar_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    listView.setVisibility(View.GONE);
                    listViewSearch.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.VISIBLE);
                    listViewSearch.setVisibility(View.GONE);
                    if (hxUserBeenList != null && hxUserBeenList.size() > 0) {
                        hxUserBeenList.clear();
                        contactSearchAdapter.notifyDataSetChanged();
                        listViewSearch.setAdapter(contactSearchAdapter);
                    }
                }
            }
        });

        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HxMemberDetailsActivity.actionStart(getActivity(), hxUserBeenList.get(position).getHx_id(), "", false);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) parentLayout.findViewById(R.id.contact_swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                mSwipeRefreshLayout.setEnabled(enable);
            }
        });

        mListDbUserData = mHxUserDao.getUserDetailsList();
    }

    private void updateContactList() {


        Log.d("好友通讯录", "目前数据库中存留的好友个数 = " + mListDbUserData.size());
        new Thread() {
            @Override
            public void run() {
                try {
                    mListHxUserName = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    mListHxUserName = new ArrayList<>();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "好友列表同步失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (getActivity() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //checkupUserData();
                        uploadUserData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }.start();/**/
    }

    private void uploadUserData() {
        StringBuilder sb = new StringBuilder();
        ListUtils.appendIdList(mListHxUserName, sb);
        TaskBase.getUserDetailList(sb.toString(), new TaskBase.UpdateCallBack<HxUserBean>() {
            @Override
            public void onSuccess(List<HxUserBean> userBeanList) {
                mHxUserDao.cleanUser();
                setupData(userBeanList);
                for (HxUserBean bean : userBeanList) {
                    mHxUserDao.saveUserDetails(bean);
                }
            }

            @Override
            public void onError(String err) {

            }
        });
    }


    @Override
    protected void setUpView() {

   /*     contactListLayout.setVisibility(View.GONE);
        View searchView = LayoutInflater.from(getContext()).inflate(R.layout.layout_info_group_search_bar, null);
        listView.addHeaderView(searchView);*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                    if (user != null) {
                        String username = user.getUsername();
                        HxMemberDetailsActivity.actionStart(getActivity(), username, "", false);
                    }
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    String username = user.getUsername();
//                    ChatSettingActivity.actionStart(getActivity(), username, ChatActivity.REQUEST_CODE_CONTACT_SETTING);
                    String[] alertItems = {"删除联系人", "加入黑名单"};
                    customListDialog(getActivity(), "", alertItems, username);
                }
                return true;
            }
        });

        super.setUpView();

        registerReceiver();
        if (mListDbUserData.size() > 0)
            setupData(mListDbUserData);
        updateContactList();
    }

    /**
     * 自定义对话框
     *
     * @param context
     * @param title
     * @param items
     */
    public void customListDialog(final Context context, String title, final String[] items, final String userName) {
        ListView alertListView = new ListView(getActivity());
        alertListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.array_item, R.id.tv_item, items));
        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setIcon(R.drawable.ic_launcher)
                .setView(alertListView);
        final AlertDialog alertDialog = builder.show();
        alertListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog.dismiss();
                if (position == 0) {
                    DialogUtils.alertDialog(getActivity(), "温馨提示", "确认要删除该好友", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismissWithAnimation();

                            DialogUtils.showProgressDialog(getActivity(), "正在删除..", null, null);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        EMClient.getInstance().contactManager().deleteContact(userName);
//                                        getActivity().setResult(RESULT_OK, new Intent().putExtra("result", ChatSettingActivity.ResultType.DELETE_USER));
                                    } catch (final HyphenateException e) {
                                        e.printStackTrace();
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogUtils.hideDialog(null);
                                        }
                                    });
                                }
                            }.start();

                        }
                    });
                } else if (position == 1) {
                    DialogUtils.alertDialog(getActivity(), "温馨提示", "您是否要将好友加入黑名单", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            //第二个参数如果为true，则把用户加入到黑名单后双方发消息时对方都收不到；false，则我能给黑名单的中用户发消息，但是对方发给我时我是收不到的
                            new Thread() {
                                @Override
                                public void run() {
                                    EMClient.getInstance().contactManager().aysncAddUserToBlackList(userName, true, new EMCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    uploadUserData();
                                                    Toast.makeText(getActivity(), "加入黑名单成功", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(int i, final String s) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(), "加入黑名单异常：" + s, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onProgress(int i, String s) {

                                        }
                                    });
                                }
                            }.start();
                        }
                    });
                }
            }
        });
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_NEW_CONTACT_NOTIFY);
        filter.addAction(Constant.ACTION_PAGE_REFRESH);
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case Constant.ACTION_NEW_CONTACT_NOTIFY:
                        updateUnreadNotifyCount();
                        break;
                    case Constant.ACTION_PAGE_REFRESH:
                        updateContactList();
                        break;
                }
            }
        };
        mBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
    }

    private void unRegisterReceiver() {
        mBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    private void checkupUserData() {

        List<String> hxIdList = new ArrayList<>();
        for (HxUserBean bean : mListDbUserData) {
            hxIdList.add(bean.getHx_id());
        }
        CheckupHxUserDetailsTask task = new CheckupHxUserDetailsTask(getContext());
        task.setUpdateCallBack(new TaskBase.UpdateCallBack<HxUserBean>() {
            @Override
            public void onSuccess(List<HxUserBean> userBeanList) {
                setupData(userBeanList);
            }

            @Override
            public void onError(String err) {

            }
        });
        Map<String, List<String>> map = new HashMap<>();
        map.put(TaskBase.DB_KEY, hxIdList);
        map.put(TaskBase.NET_KEY, mListHxUserName);

        if (hxIdList.size() != mListHxUserName.size()) {

            if (mListHxUserName.size() < hxIdList.size()) {
                task.checkUserList(map, false);
            } else if (mListHxUserName.size() > hxIdList.size()) {
                task.checkUserList(map, true);
            }

        } else {
            if (isUpdata) {
                StringBuilder sb = new StringBuilder();
                ListUtils.appendIdList(mListHxUserName, sb);
                Log.d("onRefresh", " ----- 最后获取的id号为 ----" + sb.toString());
                task.updateUserList(sb.toString());
                isUpdata = false;
            } else {
                setupData(mListDbUserData);
            }
        }


    }

    private void setupData(List<HxUserBean> userBeanList) {
        Log.d("好友通讯录", "从Task中返回的列表集合个数  = " + userBeanList.size());
        Map<String, EaseUser> m = new HashMap<>();
        m.putAll(ChatHelper.getInstance().getContactList(userBeanList));
        Log.d("好友通讯录", "从本地列表中中返回的列表集合个数  = " + m.size());
        setContactsMap(m);
        refresh();

    }

    private void updateUnreadNotifyCount() {
        if (mNewContactNotifyDao == null)
            mNewContactNotifyDao = new HxNewContactNotifyDao();
        int unreadCount = mNewContactNotifyDao.getUnreadNotifyCount();
        if (unreadCount > 0) {
            mHeadNewContactLayoutBinding.tvGroupUnreadCount.setVisibility(View.VISIBLE);
            mHeadNewContactLayoutBinding.tvGroupUnreadCount.setText(String.valueOf(unreadCount));
        } else
            mHeadNewContactLayoutBinding.tvGroupUnreadCount.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        isUpdata = true;
        updateContactList();
    }
}
