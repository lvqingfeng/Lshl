package com.lshl.ui.info.group;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.exceptions.HyphenateException;
import com.lshl.R;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.ProgressSubscriber;
import com.lshl.api.RetrofitManager;
import com.lshl.api.SubscriberOnNextListener;
import com.lshl.base.BaseActivity;
import com.lshl.bean.GroupTypeResBean;
import com.lshl.camera.SinglePictureSelectHelper;
import com.lshl.databinding.ActivityCreateNewGroupBinding;
import com.lshl.databinding.SelectGroupTypeBinding;
import com.lshl.databinding.SelectPhotoPopBinding;
import com.lshl.recycler_listener.OnRecyclerItemClickListener;
import com.lshl.ui.info.adapter.SelectGroupTypeAdapter;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.EditTextUtils;
import com.lshl.view.AddPopWindow;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.functions.Action1;
import top.zibin.luban.OnCompressListener;


public class CreateNewGroupActivity extends BaseActivity<ActivityCreateNewGroupBinding> {

    private AddPopWindow mSelectPhotoPopWindow;
    private AddPopWindow mSelectGroupTypePopWindow;


    private SinglePictureSelectHelper mSinglePictureSelectHelper;

    private GroupType mGroupType;
    private String mGroupHeadImageUrl;
    public static final int MAX_USERS = 500;

    public enum GroupType {

        PrivateOnlyOwnerInvite("私有群，只有群主可以邀请人"),
        PrivateMemberCanInvite("私有群，群成员也能邀请人进群"),
        PublicJoinNeedApproval("公开群，加入此群除了群主邀请，只能通过申请加入此群"),
        PublicOpenJoin("公开群，任何人都能加入此群");

        private String value;

        GroupType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void actionStart(Activity activity, Fragment fragment, int requestCode) {
        Intent intent = new Intent();
        if (activity != null) {
            intent.setClass(activity, CreateNewGroupActivity.class);
            activity.startActivityForResult(intent, requestCode);
        } else if (fragment != null) {
            intent.setClass(fragment.getContext(), CreateNewGroupActivity.class);
            fragment.startActivityForResult(intent, requestCode);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("创建群聊", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initFieldBeforeMethods() {
        //在调用之前一定要先获取实例，并调用init方法
        mSinglePictureSelectHelper = SinglePictureSelectHelper.getInstance().init(this);
        mDataBinding.setPresenter(new Presenter());

        mSelectPhotoPopWindow = new AddPopWindow(this, R.layout.layout_pop_select_photo);
        mSelectGroupTypePopWindow = new AddPopWindow(this, R.layout.layout_pop_select_group_type);

    }

    @Override
    protected void initViews() {

        initPopSelectPhoto();
        initPopSelectGroupType();

        mDataBinding.editGroupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 0) {
                    mDataBinding.inputLayoutGroupName.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDataBinding.editGroupName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        EditTextUtils.changeCursorVisible((EditText) v);
                        break;
                }
                return false;
            }
        });
        mDataBinding.editGroupSummary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        EditTextUtils.changeCursorVisible((EditText) v);
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGroupHeadImageUrl = mSinglePictureSelectHelper.onActivityResult(requestCode, resultCode, data, mDataBinding.ivAddGroupHead, true);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_new_group;
    }


    private void initPopSelectPhoto() {

        mSelectPhotoPopWindow.setAnimationStyle(R.style.PopWindow_y_anim_style);
        SelectPhotoPopBinding selectPhotoPopBinding = SelectPhotoPopBinding.bind(mSelectPhotoPopWindow.getWindowRootView());

        PopClickListener listener = new PopClickListener();
        selectPhotoPopBinding.btnCancel.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenCamera.setOnClickListener(listener);
        selectPhotoPopBinding.tvOpenAlbum.setOnClickListener(listener);

        mSelectPhotoPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

    }

    private void initPopSelectGroupType() {

        mSelectGroupTypePopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        SelectGroupTypeBinding selectGroupTypeBinding = SelectGroupTypeBinding.bind(mSelectGroupTypePopWindow.getWindowRootView());
        selectGroupTypeBinding.setPresenter(new Presenter());
        selectGroupTypeBinding.recyclerGroupType.setLayoutManager(new LinearLayoutManager(mContext));

        GroupType[] groupTypes = {GroupType.PrivateMemberCanInvite
                , GroupType.PrivateOnlyOwnerInvite
                , GroupType.PublicJoinNeedApproval
                , GroupType.PublicOpenJoin};

        final List<GroupTypeResBean> beanList = new ArrayList<>();

        for (int i = 0; i < groupTypes.length; i++) {

            GroupTypeResBean resBean = new GroupTypeResBean();
            resBean.setGroupType(groupTypes[i]);
            resBean.setGroupName(groupTypes[i].getValue());
            resBean.setChecked(false);
            beanList.add(resBean);

        }
        final SelectGroupTypeAdapter adapter = new SelectGroupTypeAdapter(beanList);
        selectGroupTypeBinding.recyclerGroupType.setAdapter(adapter);
        selectGroupTypeBinding.recyclerGroupType.addOnItemTouchListener(new OnRecyclerItemClickListener(selectGroupTypeBinding.recyclerGroupType) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                for (int i = 0; i < beanList.size(); i++) {
                    GroupTypeResBean resBean = beanList.get(i);
                    if (i == vh.getLayoutPosition()) {
                        resBean.setChecked(true);
                        mDataBinding.tvGroupTypeHint.setText(resBean.getGroupName());
                        mGroupType = resBean.getGroupType();
                    } else
                        resBean.setChecked(false);
                }
                adapter.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeSelectGroupTypePop();
                    }
                }, 100);
            }
        });

        mSelectGroupTypePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }


    /**
     * 关闭SelectPhotoPop页面
     */
    private void closeSelectPhotoPop() {
        mSelectPhotoPopWindow.closePopupWindow();
    }

    /**
     * 关闭SelectGroupTypePop页面
     */
    private void closeSelectGroupTypePop() {
        mSelectGroupTypePopWindow.closePopupWindow();
    }

    /**
     * 使用DataBinding进行的事件点击绑定
     */
    public class Presenter {


        public void addGroupHeadImageClick() {
            //开启图片筛选的PopWindow
            mSelectPhotoPopWindow.showPopupWindow(mDataBinding.rlNewGroupParent);
            lightOff();
        }

        public void parentViewClick() {
            mDataBinding.editGroupName.setCursorVisible(false);
            mDataBinding.editGroupSummary.setCursorVisible(false);
        }

        public void selectGroupTypeClick() {
            mSelectGroupTypePopWindow.showPopupWindow(mDataBinding.rlNewGroupParent);
            lightOff();
        }

        public void popGroupTypeBack() {
            closeSelectGroupTypePop();
        }

        public void submitGroupInfo() {

            String groupName = mDataBinding.editGroupName.getText().toString().trim();
            String groupSummary = mDataBinding.editGroupSummary.getText().toString();

            if (TextUtils.isEmpty(mGroupHeadImageUrl)) {
                showMessage("请选择您的群头像");
                return;
            }
            if (TextUtils.isEmpty(groupName)) {
                mDataBinding.inputLayoutGroupName.setErrorEnabled(true);
                mDataBinding.inputLayoutGroupName.setError("请填写群名称");
                showMessage("请填写群名称");
                return;
            }
            if (groupName.length() < 2) {
                showMessage("您的群名称不符合规则");
                return;
            }
            if (TextUtils.isEmpty(groupSummary)) {
                groupSummary = getString(R.string.group_empty_summary);
            }

//            if (mGroupType == null) {
//                showMessage("请选择群类型");
//                return;
//            }
            createGroup(groupName, groupSummary, mGroupHeadImageUrl, GroupType.PublicJoinNeedApproval);
        }

    }

    /**
     * 创建群的基本信息，并在环信中创建新的群
     *
     * @param groupName
     * @param groupSummary
     * @param groupHeadImageUrl
     * @param mGroupType
     */
    private void createGroup(String groupName, String groupSummary, String groupHeadImageUrl, GroupType mGroupType) {
        /**
         * 创建群组
         * @param groupName 群组名称
         * @param desc 群组简介
         * @param allMembers 群组初始成员，如果只有自己传空数组即可
         * @param reason 邀请成员加入的reason
         * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
         * @return 创建好的group
         * @throws HyphenateException
         */
        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
        createGroupOptions(mGroupType, option);
        EMGroup group = null;
        try {
            group = EMClient.getInstance().groupManager().createGroup(groupName, groupSummary, new String[0], "", option);
        } catch (HyphenateException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (group != null) {
            File file = new File(groupHeadImageUrl);
            final String groupId = group.getGroupId();

            if (file.length() <= 200 * 1024)//小于400k就不压缩图片
                uploadGroupInfo(groupId, groupName, groupSummary, file);
            else//如果大于400k进行图片压缩处理
                pictureCompression(groupId, groupName, groupSummary, file);

        } else
            showMessage("系统建群失败，请重试");
    }

    /**
     * 创建EMGroupOptions 的option里面的参数
     *
     * @param mGroupType
     * @param option
     */
    private void createGroupOptions(GroupType mGroupType, EMGroupManager.EMGroupOptions option) {
        option.maxUsers = MAX_USERS;

        switch (mGroupType) {
            case PrivateMemberCanInvite:
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                break;
            case PrivateOnlyOwnerInvite:
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                break;
            case PublicJoinNeedApproval:
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
                break;
            case PublicOpenJoin:
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                break;
        }
    }

    /**
     * 图片压缩处理后进行上传
     *
     * @param groupId
     * @param groupName
     * @param groupSummary
     * @param file
     */
    private void pictureCompression(final String groupId, final String groupName, final String groupSummary, File file) {

        ApiClient.pictureCompression(mContext, file, new OnCompressListener() {

            @Override
            public void onStart() {
                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                DialogUtils.showProgressDialog(mContext, "图片过大，正在压缩...", null, "压缩失败");
            }

            @Override
            public void onSuccess(File file) {
                // TODO 压缩成功后调用，返回压缩后的图片文件
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                uploadGroupInfo(groupId, groupName, groupSummary, file);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("图片压缩出现问题", e.getMessage());
                DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Error);
            }
        });

    }

    /**
     * 上传同步后台创建群的信息
     *
     * @param groupId
     * @param groupName
     * @param groupSummary
     * @param file
     */
    private void uploadGroupInfo(final String groupId, String groupName, String groupSummary, File file) {


        MultipartBody.Part photo = ApiClient.getFileBody(file);

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).uploadGroupCreate(photo, RequestBody.create(null, groupId), RequestBody.create(null, groupName), RequestBody.create(null, groupSummary))
                , new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody result) {
                        try {
                            String jsonStr = result.string();
                            JSONObject object = JSON.parseObject(jsonStr);
                            String status = object.getString("status");
                            String message = object.getString("message");
                            if (status.equals(ApiService.STATUS_SUC)) {
                                setResult(RESULT_OK);
                                finish();
                                Toast.makeText(mContext, "" + message, Toast.LENGTH_SHORT).show();
                            } else {//如果建群失败，就将环信中创建的群删除
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            EMClient.getInstance().groupManager().destroyGroup(groupId);
                                        } catch (HyphenateException e) {
                                            e.printStackTrace();
                                            Log.e("删除群", e.getMessage());
                                        }
                                    }
                                }.start();
                                showMessage(message);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }


    /**
     * PopWindow按钮的点击事件
     */
    private class PopClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    break;
                case R.id.tv_open_camera:
                    RxPermissions.getInstance(mContext)
                            .request(Manifest.permission.CAMERA)
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean granted) {
                                    if (granted) {
                                        mSinglePictureSelectHelper.openCameraForFile();
                                    } else {
                                        Toast.makeText(mContext, "只有同意权限后才能进行拍照,请您在应用详情中进行权限修改", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    break;
                case R.id.tv_open_album:
                    mSinglePictureSelectHelper.openAlbum();
                    break;
            }
            closeSelectPhotoPop();
        }
    }

}
