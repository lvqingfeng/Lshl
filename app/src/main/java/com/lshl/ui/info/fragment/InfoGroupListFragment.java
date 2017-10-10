package com.lshl.ui.info.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.Constant;
import com.lshl.R;
import com.lshl.base.LazyFragment;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.databinding.GroupListBinding;
import com.lshl.databinding.LookupSearchBarBinding;
import com.lshl.db.HxGroupDao;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.task.CheckupHxGroupInfoTask;
import com.lshl.task.TaskBase;
import com.lshl.ui.info.adapter.GroupAdapter;
import com.lshl.ui.info.chat.ChatActivity;
import com.lshl.ui.info.group.CreateNewGroupActivity;
import com.lshl.ui.info.group.LookupGroupActivity;
import com.lshl.utils.ListUtils;
import com.lshl.view.AddPopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 信息 - 群列表Fragment
 */
public class InfoGroupListFragment extends LazyFragment<GroupListBinding> implements SwipeRefreshLayout.OnRefreshListener {

    private boolean isFirstShowPop = true;

    private static final int REQUEST_CODE_CREATE_GROUP = 0x00020;
    private static final int REQUEST_CODE_DELETE_GROUP = 0x00025;
    private static final int REQUEST_CODE_LOOKUP_GROUP = 0x00030;

    private String mLookupGroupKey;

    private GroupAdapter groupAdapter;
    private InputMethodManager inputMethodManager;
    private AddPopWindow mLookupGroupPop;

    private LookupSearchBarBinding mLookupBarBinding;

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;

    private List<EMGroup> mListGroup = new ArrayList<>();
    private List<HxGroupBean> mGroupInfoList = new ArrayList<>();
    private HxGroupDao mGroupDao;

    private boolean isSearchPopCancle = true;
    private CheckupHxGroupInfoTask mGetGroupInfoTask;


    public InfoGroupListFragment() {
        // Required empty public constructor
    }

    public static InfoGroupListFragment newInstance() {
        InfoGroupListFragment fragment = new InfoGroupListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            groupAdapter = new GroupAdapter(mContext, 1, mGroupInfoList);
            mDataBinding.list.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
        }
    };

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
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLookupGroupPop.closePopupWindow();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_group_list;
    }

    @Override
    protected void initViews() {

        mGroupDao = new HxGroupDao();

        mGetGroupInfoTask = new CheckupHxGroupInfoTask(getContext());

        mGetGroupInfoTask.setUpdateCallBack(new TaskBase.UpdateCallBack<HxGroupBean>() {
            @Override
            public void onSuccess(List<HxGroupBean> groupBeanList) {
                setupData(groupBeanList);
            }

            @Override
            public void onError(String err) {

            }
        });

        inputMethodManager = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);

        initGroupList();

        initSearchBar();

        registerReceiver();

    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_GROUP_CHANAGED);
        filter.addAction(Constant.ACTION_PAGE_REFRESH);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onRefresh", "监听到群改变的监听");
                String deleteGroupId = intent.getStringExtra("groupId");
                if (!TextUtils.isEmpty(deleteGroupId)) {
//                    mGroupDao.deleteGroupInfo();
                }
                onRefresh();
            }
        };
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
    }

    private void initSearchBar() {

        mLookupGroupPop = new AddPopWindow(getActivity(), R.layout.layout_pop_lookup_group_search_bar);
        mLookupGroupPop.setAnimationStyle(R.style.PopWindow_x_anim_style);
        mLookupGroupPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        mLookupBarBinding = LookupSearchBarBinding.bind(mLookupGroupPop.getWindowRootView());

        mLookupBarBinding.editLookupSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    mLookupBarBinding.tvLookupSearch.setText("取消");
                    mLookupBarBinding.tvLookupSearch.setTextColor(ContextCompat.getColor(mContext, R.color.indicatorColor));
                    isSearchPopCancle = true;
                } else {
                    mLookupBarBinding.tvLookupSearch.setText("搜索");
                    mLookupBarBinding.tvLookupSearch.setTextColor(ContextCompat.getColor(mContext, R.color.textBlackColor));
                    isSearchPopCancle = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLookupBarBinding.editLookupSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onClickInputMethodSearch();
                    return true;
                }
                return false;
            }
        });
        mLookupBarBinding.tvLookupSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchPopCancle)
                    mLookupGroupPop.closePopupWindow();
                else
                    onClickInputMethodSearch();
            }
        });

    }

    private void onClickInputMethodSearch() {
        hideKeyboard(mLookupBarBinding.editLookupSearchText);
        String searchStr = mLookupBarBinding.editLookupSearchText.getText().toString();
        if (!TextUtils.isEmpty(searchStr) || !isFirstShowPop) {
            if (!TextUtils.isEmpty(searchStr))
                mLookupGroupKey = searchStr;
            isFirstShowPop = false;
            LookupGroupActivity.actionStart(InfoGroupListFragment.this, mLookupGroupKey, REQUEST_CODE_LOOKUP_GROUP);
        } else
            Toast.makeText(mContext, "请输入您要查找的关键字", Toast.LENGTH_SHORT).show();

    }


    /**
     * 初始化群列表
     */
    private void initGroupList() {


        mDataBinding.swipeLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);

        //pull down to refresh
        mDataBinding.swipeLayout.setOnRefreshListener(this);

        mDataBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                if (position == 1) {
                    checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                        @Override
                        public void onSuccess(AuthorityCheckBean bean) {
                            // 跳转进入创建一个新的群组
                            CreateNewGroupActivity.actionStart(null, InfoGroupListFragment.this, REQUEST_CODE_CREATE_GROUP);
                        }
                    });
                } else if (position == 2) {
                    checkUserIsRealname(new TaskBase.CheckUserAuthortyCallBack() {
                        @Override
                        public void onSuccess(AuthorityCheckBean bean) {
                            // 加入公共的群
                            // 先开启一个PopWindow
                            if (!isFirstShowPop) {//如果不是第一次打开打开
                                mLookupBarBinding.editLookupSearchText.setHint(mLookupGroupKey);
                                mLookupBarBinding.editLookupSearchText.setText("");
                            }
                            mLookupGroupPop.showPopupWindow(mDataBinding.getRoot());
                            showKeyboard(view);
                        }
                    });
                } else {
                    ChatActivity.actionStart(
                            null,
                            InfoGroupListFragment.this,
                            groupAdapter.getItem(position - 3).getGroup_id(),
                            EaseConstant.CHATTYPE_GROUP,
                            REQUEST_CODE_DELETE_GROUP);
                }
            }

        });

        mDataBinding.list.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getActivity().getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

    }

    //隐藏虚拟键盘
    private void hideKeyboard(View v) {
       /* if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }*/
    }

    //显示虚拟键盘
    private void showKeyboard(View v) {
        inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }


    @Override
    protected void loadData() {
        loadGroupList();
    }

    private void loadGroupList() {

        mGroupInfoList = mGroupDao.getGroupBeanList();

        new Thread() {
            @Override
            public void run() {
                try {

                    mListGroup = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                    Log.d("onRefresh", " ---- 从网络中获取到的条码数量  ---" + mListGroup.size());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkupUserData(false);
                        mDataBinding.swipeLayout.setRefreshing(false);
                    }
                });
            }
        }.start();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CREATE_GROUP:
                    break;
                case REQUEST_CODE_DELETE_GROUP:
                    break;
                case REQUEST_CODE_LOOKUP_GROUP:
                    break;
            }
            loadGroupList();
        }

    }


    @Override
    public void onRefresh() {
        mGroupInfoList = mGroupDao.getGroupBeanList();

        new Thread() {
            @Override
            public void run() {
                try {
                    mListGroup = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    Log.d("onRefresh", " ---- 从网络中获取到的条码数量  ---" + mListGroup.size());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        checkupUserData(true);

                        mDataBinding.swipeLayout.setRefreshing(false);
                    }
                });
            }
        }.start();
    }


    private void checkupUserData(boolean isUpdate) {

        List<String> hxNetIdList = new ArrayList<>();
        for (EMGroup emGroup : mListGroup) {
            hxNetIdList.add(emGroup.getGroupId());
        }

        List<String> hxIdList = new ArrayList<>();
        for (HxGroupBean bean : mGroupInfoList) {
            hxIdList.add(bean.getGroup_id());
        }

        if (mGroupInfoList.size() != mListGroup.size()) {

            Map<String, List<String>> map = new HashMap<>();
            map.put(TaskBase.DB_KEY, hxIdList);
            map.put(TaskBase.NET_KEY, hxNetIdList);
            if (mGroupInfoList.size() < mListGroup.size()) {
                mGetGroupInfoTask.checkGroupList(map, true);
            } else if (mGroupInfoList.size() > mListGroup.size()) {
                mGetGroupInfoTask.checkGroupList(map, false);
            }
        } else {
            if (isUpdate) {
                StringBuilder sb = new StringBuilder();
                ListUtils.appendIdList(hxNetIdList, sb);
                Log.d("onRefresh", " ----- 最后获取的id号为 ----" + sb.toString());
                mGetGroupInfoTask.updateGroupList(sb.toString());
            } else {
                System.out.println("----- 获取到的数据长度 --- " + mGroupInfoList.size());
                handler.sendEmptyMessage(0);
            }
        }

    }

    private void setupData(List<HxGroupBean> groupBeanList) {
        for (HxGroupBean bean : groupBeanList) {
            Log.d("InfoGroupListFragment", bean.getGroup_name());
        }
        mGroupInfoList.clear();
        mGroupInfoList.addAll(groupBeanList);

        handler.sendEmptyMessage(0);
    }
}
