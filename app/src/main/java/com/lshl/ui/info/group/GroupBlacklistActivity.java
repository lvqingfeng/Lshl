package com.lshl.ui.info.group;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityGroupBlacklistBinding;
import com.lshl.db.bean.HxUserBean;
import com.lshl.task.TaskBase;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GroupBlacklistActivity extends BaseActivity<ActivityGroupBlacklistBinding> {
    private BlacklistAdapter adapter;
    private String groupId;
    private List<String> mHxIdList;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);


    }

    @Override
    protected void initFieldBeforeMethods() {
        groupId = getIntent().getStringExtra("groupId");
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("群黑名单", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {


        // register context menu
        registerForContextMenu(mDataBinding.list);
        final String st1 = getResources().getString(R.string.get_failed_please_check);
        new Thread(new Runnable() {

            public void run() {
                try {
                    mHxIdList = EMClient.getInstance().groupManager().getBlockedUsers(groupId);

                    if (mHxIdList != null) {
                        final StringBuilder sb = new StringBuilder();
                        ListUtils.appendIdList(mHxIdList, sb);


                        runOnUiThread(new Runnable() {
                            public void run() {
                                TaskBase.getUserDetailList(sb.toString(), new TaskBase.UpdateCallBack<HxUserBean>() {
                                    @Override
                                    public void onSuccess(List<HxUserBean> userBeanList) {
                                        List<String> realNameList = new ArrayList<String>();
                                        for (HxUserBean bean : userBeanList) {
                                            realNameList.add(bean.getRealname());
                                        }
                                        adapter = new BlacklistAdapter(GroupBlacklistActivity.this, 1, realNameList);
                                        mDataBinding.list.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onError(String err) {

                                    }
                                });

                            }
                        });
                    }
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), st1, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();

        mDataBinding.list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String tobeRemoveUser = mHxIdList.get(position);
                final String realName = adapter.getItem(position);
                DialogUtils.alertDialog(mContext, "温馨提示", "是否将" + realName + "移除黑名单", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        // move out of blacklist
                        removeOutBlacklist(tobeRemoveUser, realName);
                    }
                });

                return true;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_blacklist;
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.em_remove_from_blacklist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove) {

            return true;
        }
        return super.onContextItemSelected(item);
    }*/

    /**
     * move out of blacklist
     *
     * @param tobeRemoveUser
     */
    void removeOutBlacklist(final String tobeRemoveUser, String realName) {
        final String st2 = getResources().getString(R.string.Removed_from_the_failure);
        try {
            EMClient.getInstance().groupManager().unblockUser(groupId, tobeRemoveUser);
            adapter.remove(realName);
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "移除黑名单成功！", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (HyphenateException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), st2, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void back(View view) {
        finish();
    }

    /**
     * adapter
     */
    private class BlacklistAdapter extends ArrayAdapter<String> {

        public BlacklistAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.ease_row_contact, null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(getItem(position));

            return convertView;
        }

    }

}
