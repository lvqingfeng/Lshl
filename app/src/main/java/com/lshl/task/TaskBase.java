package com.lshl.task;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lshl.Constant;
import com.lshl.LoginHelper;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.base.HttpResult;
import com.lshl.base.LshlApplication;
import com.lshl.bean.AuthorityCheckBean;
import com.lshl.bean.MemberBasicBean;
import com.lshl.bean.MemberBasicInfoBean;
import com.lshl.db.bean.HxGroupBean;
import com.lshl.db.bean.HxUserBean;
import com.lshl.ui.me.activity.CreateCardsActivity;
import com.lshl.utils.DialogUtils;
import com.lshl.utils.ListUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import rx.Subscriber;


/**
 * 作者：吕振鹏
 * 创建时间：10月18日
 * 时间：9:46
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class TaskBase {
    public static final String DB_KEY = "db";
    public static final String NET_KEY = "net";

    public static String getDifferent(List<String> dbIdList, List<String> netIdList) {
        StringBuilder sb = new StringBuilder();

        List<String> deiffList = ListUtils.checkDifferent(dbIdList, netIdList);

        if (deiffList.size() > 0) {
            ListUtils.appendIdList(deiffList, sb);
        }

        Log.d("TaskBase", sb.toString());

        return sb.toString();
    }


    public static void getGroupInfo(String groupId, final OnGetDataCallBack<HxGroupBean> callBack) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.String)
                        .getGroupInfo(groupId),
                new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callBack != null)
                            callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody groupJson) {
                        String requestStr = null;
                        try {
                            requestStr = groupJson.string();

                            if (!TextUtils.isEmpty(requestStr)) {
                                JSONObject object = JSON.parseObject(requestStr);
                                String status = object.getString("status");
                                if (status.equals(ApiService.STATUS_SUC)) {
                                    JSONArray groupArray = object.getJSONArray("message");
                                    List<HxGroupBean> hxGroupList = JSON.parseArray(groupArray.toJSONString(), HxGroupBean.class);
                                    if (callBack != null)
                                        callBack.onResult(hxGroupList.get(0));
                                } else {
                                    if (callBack != null)
                                        callBack.onError(object.getString("message"));

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (callBack != null)
                                callBack.onError(e.getMessage());
                        }
                    }
                }
        );
    }

    public static void getUserDetailList(String userIds, final UpdateCallBack<HxUserBean> callBack) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).getMemberRealname(LoginHelper.getInstance().getUserToken(), userIds)
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            MemberBasicBean memBerBasicBean = null;

                            if (status.equals(ApiService.STATUS_SUC)) {
                                memBerBasicBean = JSON.parseObject(object.toJSONString(), MemberBasicBean.class);
                                if (callBack != null)
                                    callBack.onSuccess(memBerBasicBean.getInfo());
                            } else {
                                callBack.onError("" + object.getString("info"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onError(e.getMessage());
                        }
                    }
                }
        );

    }

    public static void getUserDetails(String hxId, final OnGetDataCallBack<HxUserBean> callBack) {
        getUserDetails(LoginHelper.getInstance().getUserToken(), hxId, callBack);
    }

    public static void getUserDetails(String token, String hxId, final OnGetDataCallBack<HxUserBean> callBack) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).getMemberRealname(token, hxId)
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callBack != null)
                            callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            MemberBasicBean memBerBasicBean = null;

                            if (status.equals(ApiService.STATUS_SUC)) {
                                memBerBasicBean = JSON.parseObject(object.toJSONString(), MemberBasicBean.class);
                                callBack.onResult(memBerBasicBean.getInfo().get(0));
                            } else {
                                callBack.onError(object.getString("info"));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onError(e.getMessage());
                        }
                    }
                });

    }

    public static void getMemberBasicInfo(String uid, final OnGetDataCallBack<MemberBasicInfoBean> callBack) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.String)
                        .getMemberBasicInfo(LoginHelper.getInstance().getUserToken(), uid)
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String resultStr = responseBody.string();
                            JSONObject object = JSON.parseObject(resultStr);
                            String status = object.getString("status");
                            if (status.equals(ApiService.STATUS_SUC)) {
                                MemberBasicInfoBean basicInfoBean = JSON.parseObject(object.toJSONString(), MemberBasicInfoBean.class);
                                callBack.onResult(basicInfoBean);
                            } else if (status.equals(ApiService.TOKEN_EX)) {
                                callBack.onError("登陆信息异常，请重试");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    /**
     * 检测用户是否实名认证了
     *
     * @param context
     * @param callBack
     */
    public static void checkUserIsRealname(Context context, CheckUserAuthortyCallBack callBack) {
        checkUserAuthorty(context, Constant.CheckType.REALNAME, callBack);
    }

    /**
     * 检测用户是否捐款
     *
     * @param context
     * @param callBack
     */
    public static void checkUserIsDonation(Context context, CheckUserAuthortyCallBack callBack) {
        checkUserAuthorty(context, Constant.CheckType.DONATION, callBack);
    }

    /**
     * 检测用户是否缴纳会员费用
     *
     * @param context
     * @param callBack
     */
    public static void checkUserIsDues(Context context, CheckUserAuthortyCallBack callBack) {
        checkUserAuthorty(context, Constant.CheckType.DUES, callBack);
    }

    /**
     * 检测用户的权限
     */
    public static void checkUserAuthorty(final Context context, final Constant.CheckType checkType, final CheckUserAuthortyCallBack callBack) {
        AuthorityCheckBean bean = LshlApplication.getApplication().getAuthorityBean();
        if (bean != null) {
            switch (checkType) {
                case REALNAME:
                    checkRealname(context, callBack, bean);
                    break;
                case DONATION:
                    break;
                case DUES:
                    checkDues(context, callBack, bean);
                    break;
            }
        } else {
            DialogUtils.showProgressDialog(context, "数据加载中...", "", "");
            RetrofitManager.toSubscribe(
                    ApiClient.getApiService(
                            ApiService.class, RetrofitManager.RetrofitType.Object
                    ).authortyCheck(LoginHelper.getInstance().getUserToken()), new Subscriber<HttpResult<AuthorityCheckBean>>() {
                        @Override
                        public void onCompleted() {
                            DialogUtils.hideDialog(DialogUtils.LoadCompleteType.Success);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(HttpResult<AuthorityCheckBean> authorityCheckBeanHttpResult) {
                            LshlApplication.getApplication().setAuthorityBean(authorityCheckBeanHttpResult.getInfo());
                            switch (checkType) {
                                case REALNAME:
                                    checkRealname(context, callBack, authorityCheckBeanHttpResult.getInfo());
                                    break;
                                case DONATION:
                                    break;
                                case DUES:
                                    checkDues(context, callBack, authorityCheckBeanHttpResult.getInfo());
                                    break;
                            }

                        }
                    }
            );
        }

    }

    /**
     * 检测是否是vip会员
     */
    private static void checkDues(Context context, CheckUserAuthortyCallBack callBack, AuthorityCheckBean bean) {
        if (callBack.checkIsDues(bean)) {
            callBack.onSuccess(bean);
        } else {
            Toast.makeText(context, "检测到您尚不具备发布资格", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检测是否实名认证
     *
     * @param callBack
     * @param bean
     */
    private static void checkRealname(final Context context, final CheckUserAuthortyCallBack callBack, AuthorityCheckBean bean) {
        if (callBack.checkIsRealname(bean) && callBack.checkIsBasic(bean)) {
            callBack.onSuccess(bean);
        } else {
            //判断信息提交的状态
            switch (bean.getBasic()) {
                case 0://0未提交
                    DialogUtils.alertDialog(context, "温馨提示", "检测到您尚未完善基本资料，是否前往完善基本资料",
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }, new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    CreateCardsActivity.actionStart((Activity) context);
                                }
                            });
                    break;
              /*  case 1://1 基本资料提交
                    DialogUtils.alertDialog(context, "温馨提示", "检测到您尚未进行实名认证，您是否要要进行实名认证",
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }, new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    AuthenticationActivity.actionStart((Activity) context, AuthenticationActivity.Authentication);
                                }
                            });
                    break;
                case 2://2待审核
                    Toast.makeText(context, "您的实名认证已提交，请耐心等待", Toast.LENGTH_SHORT).show();
                    break;*/
            }
        }
    }

    public static void uploadDeviceToken(String deviceToken) {
        if (TextUtils.isEmpty(deviceToken) && LoginHelper.getInstance().isOnline())
            return;
        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.Object
                ).deviceTokenUpload(LoginHelper.getInstance().getUserBean().getUid(), deviceToken)
                , new Subscriber<HttpResult<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {

                    }
                }
        );
    }

    public interface OnGetDataCallBack<T> {

        void onResult(T t);

        void onError(String err);
    }

    public interface UpdateCallBack<T> {
        void onSuccess(List<T> userBeanList);

        void onError(String err);
    }

    public static abstract class CheckUserAuthortyCallBack {

        public abstract void onSuccess(AuthorityCheckBean bean);

        public boolean checkIsRealname(AuthorityCheckBean bean) {
            return bean.getRealname() == 1;
        }

        public boolean checkIsBasic(AuthorityCheckBean bean) {
            return bean.getBasic() == 1;
        }

        public boolean checkIsDonation(AuthorityCheckBean bean) {
            return bean.getDonation() == 1;
        }

        public boolean checkIsDues(AuthorityCheckBean bean) {
            return bean.getDues() == 1;
        }
    }

}
