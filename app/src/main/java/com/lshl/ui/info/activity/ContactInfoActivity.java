package com.lshl.ui.info.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.bean.FindUserBean;
import com.lshl.databinding.ActivityContactInfoBinding;

/**
 * 添加好友的界面
 */
public class ContactInfoActivity extends BaseActivity<ActivityContactInfoBinding> {

    private FindUserBean mHxUserBean;
    private static final int REQUEST_CODE=0x000123;
    public class Presenter {

        public void onClickAddContact() {
            AddFriendActivity.actionStart(ContactInfoActivity.this,mHxUserBean.getHx_id(),REQUEST_CODE);
        }

    }

    public static void actionStart(Context context, FindUserBean userBean) {
        Intent intent = new Intent(context, ContactInfoActivity.class);
        intent.putExtra("user_data", userBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        mHxUserBean = (FindUserBean) getIntent().getSerializableExtra("user_data");
    }

    @Override
    protected void setupTitle() {
        setTextTitleView("好友详情", DEFAULT_TITLE_TEXT_COLOR);
        openTitleLeftView(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==REQUEST_CODE){
                finish();
            }
        }
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_info;
    }
}
