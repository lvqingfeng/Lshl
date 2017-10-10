/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lshl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.lshl.bean.NewsInfoBean;
import com.lshl.ui.appliance.activity.KouBeiDetailsActivity;
import com.lshl.ui.appliance.activity.ProjectDetailActivity;
import com.lshl.ui.business.activity.SHDynamDetailsicActivity;
import com.lshl.ui.dscs.activity.DscsProjectDetailsActivity;
import com.lshl.ui.me.activity.GoodsDetailsActivity;
import com.lshl.ui.news.activity.NewsCommentActivity;
import com.lshl.utils.IconFontManager;

public class Constant extends EaseConstant {

    public static final String WX_APP_ID = "wxa914d1bc745be9b8";
    public static final String WX_API_KEY = "7aa8a6f6f0f41894cdbbd6c7e4954175";
    public static final String WX_MCH_ID ="1419355702";

    //public static final int REQUEST_CODE_LOGIN = 0x0002016;//登陆的请求码
    public static final int REQUEST_CODE_LOGOUT = 0x0002017;//退出的请求码

    public static final String FRIEND_RADAR_KEY = "friend_radar";

    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";
    public static final String ACTION_GROUP_NOTIFY = "action_group_notify";//群通知的动作
    public static final String ACTION_NEW_CONTACT_NOTIFY = "action_new_contact_notify";//新联系人的通知

    public static final String ACTION_CONTACT_REFRESU = "action_contact_refresh";//好友联系人更新

    public static final String GROUP_NOTIFY_EXTRA = "group_notify_extra";
    public static final String NEW_CONTACT_NOTIFY_EXTRA = "new_contact_notify_extra";
    public static final String ACTION_GROUP_NAME_CHANGER = "group_name_changer";

    public static final String VOTE_FIRST_STATUS = "12";//初审
    public static final String VOTE_SEND_STATUS = "22";//复审
    public static final String VOTE_THIRD_STATUS = "32";//终审

    public static final String ACTION_PAGE_REFRESH = "page_refresh";//通知页面刷新

    /*以下为分享的唯一标识*/
    public static final String SHARE_KEY_PROJECT = "xiangmu";//项目
    public static final String SHARE_KEY_GOOD = "gongyi";//公益
    public static final String SHARE_KEY_HELP = "huzhu";//互助
    public static final String SHARE_KEY_KOUBEI = "koubei";//口碑
    public static final String SHARE_KEY_TRADE = "zimaoqu";//自贸区
    public static final String SHARE_KEY_LOOK_HELP="findhelper";//找帮手


    /**
     * 发布口碑红、黑榜的类型
     */
    public enum ReputationType {
        RED("1"), BLACK("2");

        ReputationType(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }
    }

    /**
     * 招贤纳士，列表类型
     * {@link #COMMON}普通类型
     * {@link #COLLECT}收藏列表
     * {@link #DELIVER}投递列表
     */
    public enum JobListType {
        COMMON(""), COLLECT("1"), DELIVER("2");
        private String type;

        JobListType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 进行权限选择的判断
     */
    public enum CheckType {
        REALNAME, DONATION, DUES
    }

    /**
     * 使用语音通话或者是使用视频通话的类型
     */
    public enum CallType {
        VOICE("voice"),
        VIDEO("video");
        private final String type;

        CallType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum EMError {

        GROUP_INVALID_ID(600, "群id无效"), GROUP_ALREADY_JOINED(601, "您已是该群成员"),
        GROUP_NOT_JOINED(602, "该群组不可加入"), GROUP_PERMISSION_DENIED(603, "该群组拒绝了您的申请"),
        GROUP_MEMBERS_FULL(604, "群成员已满"), GROUP_NOT_EXIST(605, "群组不存在");

        private String value;
        private int code;

        EMError(int code, String value) {
            this.code = code;
            this.value = value;

        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static String getValue(int code) {
            for (int i = 0; i < EMError.values().length; i++) {
                if (EMError.values()[i].code == code)
                    return EMError.values()[i].value;
            }
            return "未知错误";
        }
    }

    public static Typeface getIconTypeface(Context context) {
        return IconFontManager.getTypeface(context, IconFontManager.FONT_NAME);
    }

    public static void getNewsHideView(String key, TextView lookDetails, TextView tvComment) {
        switch (key) {
            case "gongyi":
                lookDetails.setVisibility(View.VISIBLE);
                break;
            case "huzhu":
                lookDetails.setVisibility(View.VISIBLE);
                break;
            case "koubei":
                lookDetails.setVisibility(View.VISIBLE);
                break;
            case "zimaoqu":
                lookDetails.setVisibility(View.VISIBLE);
                break;
            case "shanghui":
                lookDetails.setVisibility(View.VISIBLE);
                break;
            case "xiangmu":
                lookDetails.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public static void getNewsToComment(Activity activity, String key, NewsInfoBean infoBean) {
        switch (key) {
            case "xiangmu"://项目
                ProjectDetailActivity.actionStart(activity, infoBean.getProject_id(), ProjectDetailActivity.FROM_APPLICEN);
                break;
            case "shanghui"://商会
                SHDynamDetailsicActivity.actionStart(activity, infoBean.getProject_id());
                break;
            case "koubei"://口碑
                KouBeiDetailsActivity.actionStart(activity, infoBean.getProject_id(), KouBeiDetailsActivity.FROM_APPLICEN);
                break;
            default:
                NewsCommentActivity.actionStart(activity, infoBean.getId());
                break;
        }
    }

    public static void getNewsToDetails(Activity activity, String key, String id) {
        switch (key) {
            case "xiangmu"://项目
                ProjectDetailActivity.actionStart(activity, id, ProjectDetailActivity.FROM_APPLICEN);
                break;
            case "shanghui"://商会
                SHDynamDetailsicActivity.actionStart(activity, id);
                break;
            case "gongyi"://公益
                DscsProjectDetailsActivity.actionStart(activity, DscsProjectDetailsActivity.FROM_MB, id);
                break;
            case "huzhu"://互助
                DscsProjectDetailsActivity.actionStart(activity, DscsProjectDetailsActivity.FROM_MA, id);
                break;
            case "koubei"://口碑
                KouBeiDetailsActivity.actionStart(activity, id, KouBeiDetailsActivity.FROM_APPLICEN);
                break;
            case "zimaoqu"://自贸区
                GoodsDetailsActivity.actionStart(activity,id,GoodsDetailsActivity.FROM_OTHER);
                break;
            default:
                break;
        }
    }

    public static void getGradeForAll(int grade, ImageView view) {
        switch (grade) {
            case 1:
                view.setImageResource(R.drawable.ic_vector_star_vip);
                break;
            case 2:
                view.setImageResource(R.drawable.ic_vector_svip);
                break;
            case 3:
                view.setImageResource(R.drawable.ic_vector_vip);
                break;
            case 4:
                break;
            case 5:
                view.setImageResource(R.drawable.ic_vector_not_realname);
                break;
            default:
                break;
        }
    }

    public static int getGradeForResource(int grade) {
        switch (grade) {
            case 1:
                return R.drawable.ic_vector_star_vip;
            case 2:
                return R.drawable.ic_vector_svip;
            case 3:
                return R.drawable.ic_vector_vip;
            case 4:
                return R.drawable.ic_vector_white;
            case 5:
                return R.drawable.ic_vector_not_realname;
            default:
                return R.drawable.ic_vector_white;
        }
    }

    public static String getStatusForMutual(String tap) {
        switch (tap) {
            case "0":
                return "失败";
            case "2":
                return "待审";
            case "99":
                return "成功";
            case "10":
                return "初审失败";
            case "11":
                return "初审失败";
            case "12":
                return "初审投票中";
            case "20":
                return "复审失败";
            case "21":
                return "复审失败";
            case "22":
                return "复审投票中";
            case "30":
                return "终审失败";
            case "31":
                return "终审失败";
            case "32":
                return "终审投票中";
            case "41":
                return "打款成功";
            case "42":
                return "等待打款";
            case "52":
                return "执行中";
            case "62":
                return "执行回馈";
        }
        return "";
    }

}
