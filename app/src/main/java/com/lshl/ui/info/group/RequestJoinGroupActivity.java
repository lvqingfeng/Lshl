package com.lshl.ui.info.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.base.BaseActivity;
import com.lshl.base.LshlApplication;
import com.lshl.base.SimpleBindingAdapter;
import com.lshl.bean.User;
import com.lshl.databinding.GroupMembersImageItemBinding;
import com.lshl.databinding.JoinGroupBinding;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.db.bean.HxUserBean;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.task.TaskBase;
import com.lshl.utils.BitmapBlurUtils;
import com.lshl.utils.DateUtils;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求加入群的Activity
 */
public class RequestJoinGroupActivity extends BaseActivity<JoinGroupBinding> {

    private static final String GROUP_INFO_BEAN = "group_info";

    private HxGroupBean mGroupInfo;

    private EMGroup mEMGroup;

    private List<HxUserBean> mMembersListData = new ArrayList<>();
    private SimpleBindingAdapter<GroupMembersImageItemBinding, HxUserBean> mMembersImageAdapter;

    private int mImageSize;

    public static void actionStart(Activity activity, HxGroupBean groupInfo) {
        Intent intent = new Intent(activity, RequestJoinGroupActivity.class);
        intent.putExtra(GROUP_INFO_BEAN, groupInfo);
        activity.startActivity(intent);
    }

    public class Presenter {

        public void onClickRequestJoinGroup() {
            if (mEMGroup != null) {

                new Thread() {
                    @Override
                    public void run() {
                        //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                        try {

                            if (mEMGroup.isMembersOnly())
                                EMClient.getInstance().groupManager().applyJoinToGroup(mGroupInfo.getGroup_id(), "求加入");//需异步处理
                            else
                                //如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                                EMClient.getInstance().groupManager().joinGroup(mGroupInfo.getGroup_id());//需异步处理
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mEMGroup.isMembersOnly())
                                        showMessage(getString(R.string.send_the_request_is));
                                    else {
                                        int groupMemberCount = Integer.decode(TextUtils.isEmpty(mDataBinding.getGroupMemberCount()) ? "0" : mDataBinding.getGroupMemberCount());
                                        mDataBinding.setGroupMemberCount(String.valueOf(groupMemberCount + 1));
                                        User user = LoginHelper.getInstance().getUserBean();
                                        HxUserBean bean = new HxUserBean();
                                        bean.setRealname(user.getRealName());
                                        bean.setAvatar(user.getAvatar());
                                        bean.setHx_id(bean.getHx_id());
                                        mMembersListData.add(bean);
                                        mMembersImageAdapter.notifyItemInserted(mMembersImageAdapter.getItemCount());
                                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent().setAction(Constant.ACTION_PAGE_REFRESH));
                                        showMessage(getString(R.string.Join_the_group_chat));
                                    }
                                }
                            });
                        } catch (final HyphenateException e) {
                            e.printStackTrace();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage(getString(R.string.Failed_to_join_the_group_chat) + Constant.EMError.getValue(e.getErrorCode()));
                                }
                            });
                        }


                    }
                }.start();

            }
        }

        public void goGroupMembersList() {
            RequestJoinGroupActivity.this.goGroupMembersList();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mImageSize = LshlApplication.sWindowWidth / 5 - 20;
        mGroupInfo = (HxGroupBean) getIntent().getSerializableExtra(GROUP_INFO_BEAN);
        mDataBinding.setGroupInfo(mGroupInfo);
        mDataBinding.setPresenter(new Presenter());
        initData();
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    mEMGroup = EMClient.getInstance().groupManager().getGroupFromServer(mGroupInfo.getGroup_id());
                    mDataBinding.setGroupMemberCount(String.valueOf(mEMGroup.getAffiliationsCount()));
                    getGroupMembersForService(mEMGroup.getMembers());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    mDataBinding.setGroupMemberCount("0");
                }
            }
        }.start();

    }

    private void getGroupMembersForService(List<String> members) {
        StringBuilder sb = new StringBuilder();
        ListUtils.appendIdList(members, sb);
        TaskBase.getUserDetailList(sb.toString(), new TaskBase.UpdateCallBack<HxUserBean>() {
            @Override
            public void onSuccess(List<HxUserBean> userBeanList) {
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                mMembersListData.addAll(userBeanList);
                mMembersImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String err) {
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });

    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView(mGroupInfo.getGroup_name(), DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        String url = ApiClient.getHxGroupImage(mGroupInfo.getGroup_img());
        Glide.with(mContext).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                //模糊处理
                BitmapBlurUtils.addTask(resource, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.obj != null) {
                            Drawable drawable = (Drawable) msg.obj;
                            mDataBinding.ivBg.setImageDrawable(drawable);
                        }
                    }
                });
            }
        });
        String time = DateUtils.getDateToString(Long.decode(mGroupInfo.getAdd_time()) * 1000);
        mDataBinding.tvGroupCreateTime.setText(String.format(getString(R.string.group_create_time_s), time));
        initMembersList();
    }


    private void initMembersList() {
        mDataBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mDataBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(15, 20, 15, 0);
            }
        });
        mMembersImageAdapter = new SimpleBindingAdapter<GroupMembersImageItemBinding, HxUserBean>(mMembersListData, R.layout.item_layout_group_members_image) {
            @Override
            public void onBindViewHolder(GroupMembersImageItemBinding binding, int position) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) binding.ivAvatar.getLayoutParams();
                lp.width = mImageSize;
                lp.height = mImageSize;
                binding.ivAvatar.setLayoutParams(lp);
                binding.setUserBean(mMembersListData.get(position));
            }

            @Override
            public int getItemCount() {
                return mMembersListData.size() < 10 ? mMembersListData.size() : 10;
            }
        };
        mDataBinding.recyclerView.setAdapter(mMembersImageAdapter);
        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                goGroupMembersList();
            }
        });
    }

    private void goGroupMembersList() {
        GroupMembersListActivity.actionStart(this, (ArrayList<HxUserBean>) mMembersListData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_request_join_group;
    }


}
