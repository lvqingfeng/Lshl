package com.lshl.ui.me.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.databinding.ActivityBlackListBinding;
import com.lshl.databinding.ItemLayoutBlackListBinding;
import com.lshl.db.bean.HxUserBean;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.DividerGridItemDecoration;
import com.lshl.utils.ListUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 黑名单列表
 */
public class BlackListActivity extends BaseActivity<ActivityBlackListBinding> {

    private List<String> mBlackList;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, BlackListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupTitle() {
        setTextTitleView("黑名单列表", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);

    }

    @Override
    protected void initViews() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(final RecyclerView.ViewHolder vh) {
                DialogUtils.alertDialog(mContext, "温馨提示", "您是否要将目标移除黑名单？", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        EMClient.getInstance().contactManager().aysncRemoveUserFromBlackList(mBlackList.get(vh.getLayoutPosition()), new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, "已将该用户移除黑名单成功", Toast.LENGTH_SHORT).show();
                                        ((SimpleBindingAdapter) mDataBinding.recyclerView.getAdapter()).removItem(vh.getLayoutPosition());
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, String s) {
                                showMessage("错误信息：" + s);
                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                    }
                });
            }
        });
        EMClient.getInstance().contactManager().aysncGetBlackListFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                mBlackList = strings;
                StringBuilder sb = new StringBuilder();
                ListUtils.appendIdList(strings, sb);
                TaskBase.getUserDetailList(sb.toString(), new TaskBase.UpdateCallBack<HxUserBean>() {
                    @Override
                    public void onSuccess(List<HxUserBean> userBeanList) {
                        mDataBinding.recyclerView.setAdapter(new SimpleBindingAdapter<ItemLayoutBlackListBinding, HxUserBean>(userBeanList, R.layout.item_layout_black_list) {

                            @Override
                            public void onBindViewHolder(ItemLayoutBlackListBinding binding, int position) {
                                binding.setUserBean(getItem(position));
                            }
                        });
                    }

                    @Override
                    public void onError(String err) {
                        showMessage("列表获取失败:" + err);
                    }
                });

            }

            @Override
            public void onError(final int i, final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String hint = s;
                        if (i == 201) {
                            hint = "您的环信聊天暂未登陆，请重新登陆";
                        }
                        showMessage("服务器同步失败：" + hint);
                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_black_list;
    }
}
