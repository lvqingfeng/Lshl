package com.lshl.ui.addressthree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lshl.R;
import com.lshl.base.BaseActivity;
import com.lshl.databinding.ActivityAddressThreeBinding;

/**
 * Created by Administrator on 2017/3/1.
 */

public class AddressThActivity extends BaseActivity<ActivityAddressThreeBinding> {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private PFragment fragment1;
    private CFragment fragment2;
    private XFragment fragment3;
    private int mFragmentIndex = 0;
    public static void actionStart(Activity activity,int requestCode) {
        Intent intent = new Intent(activity, AddressThActivity.class);
        activity.startActivityForResult(intent,requestCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("选择地区",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragment1 = new PFragment();
        fragment2 = new CFragment();
        fragment3 = new XFragment();

        fragmentTransaction.add(R.id.content, fragment1, null);
        fragmentTransaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_three;
    }


    public void enterContentFrament02(String msg) {

        if (fragment2 == null) {
            fragment2 = new CFragment();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFragmentIndex++;

    }
}
