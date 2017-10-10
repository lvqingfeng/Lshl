package com.lshl.service;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.lshl.ui.info.activity.RadarListActivity;
import com.lshl.ui.me.activity.NewNoticeActivity;
import com.lshl.utils.AuthorityHelp;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengMessageService;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

/**
 * 作者：吕振鹏
 * 创建时间：12月10日
 * 时间：12:37
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class MyPushIntentService extends UmengMessageService {

    private static final String TAG = MyPushIntentService.class.getName();

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            UmLog.d(TAG, "message=" + message);      //消息体
            UmLog.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            UmLog.d(TAG, "title=" + msg.title);      //通知标题
            UmLog.d(TAG, "text=" + msg.text);        //通知内容
            // code  to handle message here
            Map<String, String> extra = msg.extra;
            String key = extra.get("pi_catid");
            if (key.equals("realname")) {
                AuthorityHelp.setRealnameStatus(1);
            }
            UmengMessageHandler handler = null;
            if (key.equals("radar")) {
                handler = new UmengMessageHandler() {
                    @Override
                    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
                        Intent clickIntent = new Intent();
                        clickIntent.setClass(context, RadarListActivity.class);
                        clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, (int) (System.currentTimeMillis()), clickIntent, 0);
                        return clickPendingIntent;
                    }
                };
            } else {
                // 对完全自定义消息的处理方式，点击或者忽略
                handler = new UmengMessageHandler() {

                    @Override
                    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
                        Intent clickIntent = new Intent();
                        clickIntent.setClass(context, NewNoticeActivity.class);
                        clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, (int) (System.currentTimeMillis()), clickIntent, 0);
                        return clickPendingIntent;
                    }
                };
            }

            handler.dealWithNotificationMessage(this, msg);

            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

            // 使用完全自定义消息来开启应用服务进程的示例代码
            // 首先需要设置完全自定义消息处理方式
            // mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
            // code to handle to start/stop service for app process
            JSONObject json = new JSONObject(msg.custom);
            String topic = json.getString("topic");
            UmLog.d(TAG, "topic=" + topic);
          /*  if (topic != null && topic.equals("appName:startService")) {
                // 在【友盟+】portal上新建自定义消息，自定义消息文本如下
                //{"topic":"appName:startService"}
                if (Helper.isServiceRunning(context, NotificationService.class.getName()))
                    return;
                Intent intent1 = new Intent();
                intent1.setClass(context, NotificationService.class);
                context.startService(intent1);
            } else if (topic != null && topic.equals("appName:stopService")) {
                // 在【友盟+】portal上新建自定义消息，自定义消息文本如下
                //{"topic":"appName:stopService"}
                if (!Helper.isServiceRunning(context,NotificationService.class.getName()))
                    return;
                Intent intent1 = new Intent();
                intent1.setClass(context, NotificationService.class);
                context.stopService(intent1);
            }*/
        } catch (Exception e) {
            UmLog.e(TAG, e.getMessage());
        }
    }
}
