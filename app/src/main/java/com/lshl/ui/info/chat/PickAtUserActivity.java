package com.lshl.ui.info.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityPickAtUserBinding;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PickAtUserActivity extends BaseActivity<ActivityPickAtUserBinding> {

    private ListView listView;
    private String groupId;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("选择要回复的人", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        groupId = getIntent().getStringExtra("groupId");
    }

    @Override
    protected void initViews() {


        EaseSidebar sidebar = (EaseSidebar) findViewById(com.hyphenate.easeui.R.id.sidebar);
        listView = (ListView) findViewById(R.id.list);
        sidebar.setListView(listView);

        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(final List<String> strings) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFriendsList(strings);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Log.e("error", "i = " + i + "s = " + s);
            }
        });

    }

    private void initFriendsList(List<String> finalFriends) {
        final EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
        final List<String> members = group.getMembers();
        final List<EaseUser> userList = new ArrayList<EaseUser>();
        DialogUtils.showProgressDialog(mContext, "数据加载中...", "", "");
        List<String> tempUserList = new ArrayList<>();
        for (String username : members) {
            EaseUser user = null;
            if (finalFriends.contains(username)) {
                user = EaseUserUtils.getUserInfo(username);
                userList.add(user);
            } else {
                tempUserList.add(username);
            }
        }
        StringBuilder sb = new StringBuilder();
        ListUtils.appendIdList(tempUserList, sb);
        EaseUserUtils.getTempUserList(sb.toString(), new EaseUserUtils.EMUserListCallback() {
            @Override
            public void onUserListCallback(List<EaseUser> list) {
                userList.addAll(list);
                if (userList.size() == members.size()) {
                    DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
                    initView(userList, group);
                }
            }
        });
    }


    private void initView(List<EaseUser> userList, EMGroup group) {
        Collections.sort(userList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {

                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });
        final boolean isOwner = EMClient.getInstance().getCurrentUser().equals(group.getOwner());
        if (isOwner) {
            addHeadView();
        }
        listView.setAdapter(new PickUserAdapter(this, 0, userList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isOwner) {
                    if (position != 0) {
                        EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                        if (EMClient.getInstance().getCurrentUser().equals(user.getUsername()))
                            return;
                        setResult(RESULT_OK, new Intent().putExtra("username", user.getUsername()));
                    } else {
                        setResult(RESULT_OK, new Intent().putExtra("username", getString(R.string.all_members)));
                    }
                } else {
                    EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                    if (EMClient.getInstance().getCurrentUser().equals(user.getUsername())) {
                        Toast.makeText(mContext, "不可@自己", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    setResult(RESULT_OK, new Intent().putExtra("username", user.getNick()));
                }

                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pick_at_user;
    }

    private void addHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.ease_row_contact, listView, false);
        ImageView avatarView = (ImageView) view.findViewById(R.id.avatar);
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(getString(R.string.all_members));
        avatarView.setImageResource(R.drawable.ease_groups_icon);
        listView.addHeaderView(view);
    }

    public void back(View view) {
        finish();
    }

    private class PickUserAdapter extends EaseContactAdapter {

        public PickUserAdapter(Context context, int resource, List<EaseUser> objects) {
            super(context, resource, objects);
        }
    }
}
