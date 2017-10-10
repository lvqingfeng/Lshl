package com.lshl.task;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lshl.api.ApiClient;
import com.lshl.api.ApiService;
import com.lshl.api.RetrofitManager;
import com.lshl.db.HxGroupDao;
import com.lshl.db.bean.HxGroupBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.lshl.task.TaskBase.DB_KEY;
import static com.lshl.task.TaskBase.NET_KEY;

/**
 * 作者：吕振鹏
 * 创建时间：10月17日
 * 时间：22:35
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class CheckupHxGroupInfoTask {

    private HxGroupDao mGroupDao;
    private Context mContext;
    private TaskBase.UpdateCallBack<HxGroupBean> mCallBack;

    public CheckupHxGroupInfoTask(Context content) {
        mGroupDao = new HxGroupDao();
        mContext = content;
    }

    public void setUpdateCallBack(TaskBase.UpdateCallBack<HxGroupBean> callBack) {
        mCallBack = callBack;
    }

    public void checkGroupList(Map<String, List<String>> groupIdMap, final boolean isAdd) {

        if (groupIdMap.containsKey(DB_KEY) && groupIdMap.containsKey(NET_KEY)) {

            List<String> mDbGroupIdList = groupIdMap.get(DB_KEY);
            List<String> mNetGroupIdList = groupIdMap.get(NET_KEY);

            String groupIds = TaskBase.getDifferent(mDbGroupIdList, mNetGroupIdList);
            if (isAdd)
                RetrofitManager.toSubscribe(
                        ApiClient.getApiService(ApiService.class,
                                RetrofitManager.RetrofitType.String)
                                .getGroupInfo(groupIds),
                        new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(mContext, "错误信息：" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        JSONArray groupArray = object.getJSONArray("message");
                                        List<HxGroupBean> hxGroupList = JSON.parseArray(groupArray.toJSONString(), HxGroupBean.class);
                                        for (HxGroupBean hxGroupBean : hxGroupList) {
                                            mGroupDao.saveGroupInfo(hxGroupBean);
                                        }

                                        if (mCallBack != null)
                                            mCallBack.onSuccess(mGroupDao.getGroupBeanList());
                                    }

                                }

                            }
                        }
                );
            else {
                if (!TextUtils.isEmpty(groupIds)) {
                    String[] groupId = groupIds.split(",");
                    for (String hxId : groupId) {
                        mGroupDao.deleteGroupInfo(mGroupDao.getGroupDbId(hxId));
                    }
                    if (mCallBack != null)
                        mCallBack.onSuccess(mGroupDao.getGroupBeanList());
                }

            }


        } else
            Log.e("CheckupHxGroupInfoTask", "数据传入异常---- checkGroupId() 未传入想应的key");

    }

    public void updateGroupList(String groupIds) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService(ApiService.class,
                        RetrofitManager.RetrofitType.String)
                        .getGroupInfo(groupIds),
                new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, "错误信息：" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                JSONArray groupArray = object.getJSONArray("message");
                                List<HxGroupBean> hxGroupList = JSON.parseArray(groupArray.toJSONString(), HxGroupBean.class);
                                for (HxGroupBean hxGroupBean : hxGroupList) {
                                    int dbId = mGroupDao.getGroupDbId(hxGroupBean.getGroup_id());
                                    if (dbId == -1){
                                        mGroupDao.saveGroupInfo(hxGroupBean);
                                    }
                                    mGroupDao.updateGroupInfo(dbId, hxGroupBean);
                                }

                                if (mCallBack != null)
                                    mCallBack.onSuccess(mGroupDao.getGroupBeanList());
                            }

                        }

                    }
                }
        );
    }


}
