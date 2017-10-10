package com.lshl.task;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lshl.LoginHelper;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.bean.MemberBasicBean;
import com.lshl.db.HxUserDao;
import com.lshl.db.bean.HxUserBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 作者：吕振鹏
 * 创建时间：10月15日
 * 时间：16:58
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class CheckupHxUserDetailsTask {

    private HxUserDao mHxUserDao;
    private Context mContext;
    private TaskBase.UpdateCallBack<HxUserBean> mCallBack;

    public CheckupHxUserDetailsTask(Context context) {
        mContext = context;
        mHxUserDao = new HxUserDao();
    }

    public void setUpdateCallBack(TaskBase.UpdateCallBack<HxUserBean> callBack) {
        mCallBack = callBack;
    }


    public void checkUserList(Map<String, List<String>> userIdMap, boolean isAdd) {

        if (userIdMap.containsKey(TaskBase.DB_KEY) && userIdMap.containsKey(TaskBase.NET_KEY)) {
            List<String> hxIdList = userIdMap.get(TaskBase.DB_KEY);
            List<String> usernames = userIdMap.get(TaskBase.NET_KEY);
            final String userIds = TaskBase.getDifferent(hxIdList, usernames);

            if (isAdd) {
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

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String resultStr = responseBody.string();
                                    JSONObject object = JSON.parseObject(resultStr);
                                    String status = object.getString("status");
                                    MemberBasicBean memBerBasicBean = null;
                                    List<HxUserBean> userBeanListTemp = new ArrayList<HxUserBean>();
                                    if (status.equals(ApiService.STATUS_SUC)) {
                                        memBerBasicBean = JSON.parseObject(object.toJSONString(), MemberBasicBean.class);
                                    } else {
                                        if (!TextUtils.isEmpty(userIds)) {
                                            String[] userName = userIds.split(",");
                                            for (String anUserName : userName) {
                                                HxUserBean bean = new HxUserBean();
                                                bean.setHx_id(anUserName);
                                                bean.setRealname(anUserName);
                                                userBeanListTemp.add(bean);
                                            }
                                        }

                                    }
                                    //这里为空判断，只有当返回的状态码为真时，这个才可能不会是空的
                                    if (memBerBasicBean != null) {
                                        for (HxUserBean bean : memBerBasicBean.getInfo()) {
                                            userBeanListTemp.add(bean);
                                        }
                                    }

                                    for (HxUserBean bean : userBeanListTemp)
                                        mHxUserDao.saveUserDetails(bean);

                                    userBeanListTemp.clear();
                                    userBeanListTemp.addAll(mHxUserDao.getUserDetailsList());


                                    if (mCallBack != null)
                                        mCallBack.onSuccess(userBeanListTemp);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {
                if (!TextUtils.isEmpty(userIds)) {
                    String[] groupId = userIds.split(",");
                    for (String hxId : groupId) {
                        mHxUserDao.deleteUserDetails(mHxUserDao.getUserDetailsDbId(hxId));
                    }
                    if (mCallBack != null)
                        mCallBack.onSuccess(mHxUserDao.getUserDetailsList());
                }
            }
        }

    }

    public void updateUserList(String userIds) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(
                        ApiService.class, RetrofitManager.RetrofitType.String
                ).getMemberRealname(LoginHelper.getInstance().getUserToken(), userIds),
                new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onRefresh", " ----- 错误信息Task ----" + e.getMessage());
                        Toast.makeText(mContext, "错误信息Task：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseBody groupJson) {
                        String requestStr = null;
                        try {
                            requestStr = groupJson.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!TextUtils.isEmpty(requestStr)) {
                            JSONObject object = JSON.parseObject(requestStr);
                            String status = object.getString("status");
                            if (status.equals(ApiService.STATUS_SUC)) {
                                JSONArray groupArray = object.getJSONArray("info");
                                List<HxUserBean> hxUserList = JSON.parseArray(groupArray.toJSONString(), HxUserBean.class);
                                for (HxUserBean user : hxUserList) {
                                    int dbId = mHxUserDao.getUserDetailsDbId(user.getHx_id());
                                    mHxUserDao.updateUserDetails(dbId, user);
                                }
                                if (mCallBack != null)
                                    mCallBack.onSuccess(mHxUserDao.getUserDetailsList());
                            }

                        }

                    }
                }
        );
    }

}
