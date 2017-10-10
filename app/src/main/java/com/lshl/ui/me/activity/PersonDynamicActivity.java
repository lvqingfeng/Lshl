package com.lshl.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CheckBox;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityPersonDynamicBinding;
import com.lshl.ui.me.fragment.DynamicsListFragment;

/**
 * 我的动态
 */
public class PersonDynamicActivity extends BaseActivity<ActivityPersonDynamicBinding> {

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, PersonDynamicActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public  CheckBox getRightViews(){
        return getRightView();
    }
    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("动态",DEFAULT_TITLE_TEXT_COLOR);
        getRightView().setButtonDrawable(R.drawable.ic_vector_add);

    }

    @Override
    protected void initViews() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.ll_dynamic,new DynamicsListFragment());
        transaction.commit();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_dynamic;
    }

}
