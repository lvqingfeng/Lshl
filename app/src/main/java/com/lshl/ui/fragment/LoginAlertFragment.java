package com.lshl.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lshl.R;
import com.lshl.base.BaseFragment;
import com.lshl.databinding.FragmentLoginAlertBinding;
import com.lshl.ui.login.LoginActivity;


/**
 * 底部Fragment页面切换的登陆界面
 */
public class LoginAlertFragment extends BaseFragment<FragmentLoginAlertBinding> {


    public LoginAlertFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginAlertFragment newInstance() {
        LoginAlertFragment fragment = new LoginAlertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public class Presenter {

        public void onLogin() {
            LoginActivity.actionStart(getActivity(), false, "");
        }

        public void onRegister() {

        }

    }

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_alert;
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(new Presenter());
    }

}
