package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class FriendsDynamicBean {

    /**
     * status : 1
     * info : {"list":[{"phone":"17010207171","realname":"尽可能","avatar":"589c1dd0606b159009.jpg","uid":"3","md_id":"123","md_uid":"3","mb_format":"2","mb_dynamic_info":"国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥区号称是浪漫主义乌龙在天之灵芝华士兵突击队国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥区号称是浪漫主义乌龙在天之灵芝华士兵突击队国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥区号称是浪漫主义乌龙在天之灵芝华士兵突击队国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥","mb_dynamic_img":"58b3ed6aae25a14010.png,58b3ed6aae25a4670.png,58b3ed6aae25a67563.png,58b3ed6aae25a74241.png","mb_dynamic_voide":null,"mb_thing_status":null,"mb_cityname":"甘肃省兰州市","mb_jingwei":"","mb_addtime":"1488186730","mb_thing_nums":"1","mb_comment_nums":"5","mb_status":"1"}]}
     */

    private int status;
    private InfoBean info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * phone : 17010207171
             * realname : 尽可能
             * avatar : 589c1dd0606b159009.jpg
             * uid : 3
             * md_id : 123
             * md_uid : 3
             * mb_format : 2
             * mb_dynamic_info : 国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥区号称是浪漫主义乌龙在天之灵芝华士兵突击队国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥区号称是浪漫主义乌龙在天之灵芝华士兵突击队国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥区号称是浪漫主义乌龙在天之灵芝华士兵突击队国美女小游戏就默默因为格罗姆之血就默默因为默默默因为默默默因为默默默因为后来慢慢来看不清不楚天桥
             * mb_dynamic_img : 58b3ed6aae25a14010.png,58b3ed6aae25a4670.png,58b3ed6aae25a67563.png,58b3ed6aae25a74241.png
             * mb_dynamic_voide : null
             * mb_thing_status : null
             * mb_cityname : 甘肃省兰州市
             * mb_jingwei :
             * mb_addtime : 1488186730
             * mb_thing_nums : 1
             * mb_comment_nums : 5
             * mb_status : 1
             */

            private String phone;
            private String realname;
            private String avatar;
            private String uid;
            private String md_id;
            private String md_uid;
            private String mb_format;
            private String mb_dynamic_info;
            private String mb_dynamic_img;
            private Object mb_dynamic_voide;
            private Object mb_thing_status;
            private String mb_cityname;
            private String mb_jingwei;
            private String mb_addtime;
            private String mb_thing_nums;
            private String mb_comment_nums;
            private String mb_status;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getMd_id() {
                return md_id;
            }

            public void setMd_id(String md_id) {
                this.md_id = md_id;
            }

            public String getMd_uid() {
                return md_uid;
            }

            public void setMd_uid(String md_uid) {
                this.md_uid = md_uid;
            }

            public String getMb_format() {
                return mb_format;
            }

            public void setMb_format(String mb_format) {
                this.mb_format = mb_format;
            }

            public String getMb_dynamic_info() {
                return mb_dynamic_info;
            }

            public void setMb_dynamic_info(String mb_dynamic_info) {
                this.mb_dynamic_info = mb_dynamic_info;
            }

            public String getMb_dynamic_img() {
                return mb_dynamic_img;
            }

            public void setMb_dynamic_img(String mb_dynamic_img) {
                this.mb_dynamic_img = mb_dynamic_img;
            }

            public Object getMb_dynamic_voide() {
                return mb_dynamic_voide;
            }

            public void setMb_dynamic_voide(Object mb_dynamic_voide) {
                this.mb_dynamic_voide = mb_dynamic_voide;
            }

            public Object getMb_thing_status() {
                return mb_thing_status;
            }

            public void setMb_thing_status(Object mb_thing_status) {
                this.mb_thing_status = mb_thing_status;
            }

            public String getMb_cityname() {
                return mb_cityname;
            }

            public void setMb_cityname(String mb_cityname) {
                this.mb_cityname = mb_cityname;
            }

            public String getMb_jingwei() {
                return mb_jingwei;
            }

            public void setMb_jingwei(String mb_jingwei) {
                this.mb_jingwei = mb_jingwei;
            }

            public String getMb_addtime() {
                return mb_addtime;
            }

            public void setMb_addtime(String mb_addtime) {
                this.mb_addtime = mb_addtime;
            }

            public String getMb_thing_nums() {
                return mb_thing_nums;
            }

            public void setMb_thing_nums(String mb_thing_nums) {
                this.mb_thing_nums = mb_thing_nums;
            }

            public String getMb_comment_nums() {
                return mb_comment_nums;
            }

            public void setMb_comment_nums(String mb_comment_nums) {
                this.mb_comment_nums = mb_comment_nums;
            }

            public String getMb_status() {
                return mb_status;
            }

            public void setMb_status(String mb_status) {
                this.mb_status = mb_status;
            }
        }
    }
}
