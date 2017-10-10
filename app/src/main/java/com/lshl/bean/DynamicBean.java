package com.lshl.bean;

import android.text.TextUtils;

import com.lshl.api.ApiClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 圈子动态实体类
 */

public class DynamicBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"background_map":"/Static/Image/dbp2.jpg","list":[{"phone":"13200001237","realname":"魁先生","avatar":"589c4d02a144a98561.png","pay_starttime":null,"pay_endtime":null,"authenticate":"0","md_id":"18","md_uid":"8","mb_format":"2","mb_dynamic_info":"哈哈哈","mb_dynamic_img":"589c366be5eec36995.png","mb_dynamic_voide":null,"mb_thing_status":null,"mb_cityname":"兰州市","mb_jingwei":"36.100107,103.746595","mb_addtime":"1486632555","mb_thing_nums":"1","mb_comment_nums":"0","mb_status":"1","mb_thing":"1","mb_thingstatus":0,"grade":5}]}
     */

    private String status;
    private InfoBean info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * pages : 1
         * end : 1
         * background_map : /Static/Image/dbp2.jpg
         * list : [{"phone":"13200001237","realname":"魁先生","avatar":"589c4d02a144a98561.png","pay_starttime":null,"pay_endtime":null,"authenticate":"0","md_id":"18","md_uid":"8","mb_format":"2","mb_dynamic_info":"哈哈哈","mb_dynamic_img":"589c366be5eec36995.png","mb_dynamic_voide":null,"mb_thing_status":null,"mb_cityname":"兰州市","mb_jingwei":"36.100107,103.746595","mb_addtime":"1486632555","mb_thing_nums":"1","mb_comment_nums":"0","mb_status":"1","mb_thing":"1","mb_thingstatus":0,"grade":5}]
         */

        private int pages;
        private int end;
        private String background_map;
        private List<ListBean> list;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getBackground_map() {
            return background_map;
        }

        public void setBackground_map(String background_map) {
            this.background_map = background_map;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * phone : 13200001237
             * realname : 魁先生
             * avatar : 589c4d02a144a98561.png
             * pay_starttime : null
             * pay_endtime : null
             * authenticate : 0
             * md_id : 18
             * md_uid : 8
             * mb_format : 2
             * mb_dynamic_info : 哈哈哈
             * mb_dynamic_img : 589c366be5eec36995.png
             * mb_dynamic_voide : null
             * mb_thing_status : null
             * mb_cityname : 兰州市
             * mb_jingwei : 36.100107,103.746595
             * mb_addtime : 1486632555
             * mb_thing_nums : 1
             * mb_comment_nums : 0
             * mb_status : 1
             * mb_thing : 1
             * mb_thingstatus : 0
             * grade : 5
             */

            private String phone;
            private String realname;
            private String avatar;
            private String pay_starttime;
            private String pay_endtime;
            private String authenticate;
            private String md_id;
            private String md_uid;
            private String mb_format;
            private String mb_dynamic_info;
            private String mb_dynamic_img;
            private String mb_dynamic_voide;
            private String mb_thing_status;
            private String mb_cityname;
            private String mb_jingwei;
            private String mb_addtime;
            private String mb_thing_nums;
            private String mb_comment_nums;
            private String mb_status;
            private String mb_thing;
            private String mb_thingstatus;
            private int grade;
            private List<String> imageUrl;
            public List<String> getImageUrl() {
                imageUrl = new ArrayList<>();
                if (TextUtils.isEmpty(mb_dynamic_img))
                    return imageUrl;
                String[] split = mb_dynamic_img.split(",");
                for (int i = 0; i < split.length; i++) {
                    split[i] = ApiClient.getDynamicImage(split[i]);
                }
                if (split.length > 0) {
                    int a = split.length < 9 ? split.length : 9;
                    imageUrl.addAll(Arrays.asList(split).subList(0, a));
                }
                return imageUrl;
            }
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

            public String getPay_starttime() {
                return pay_starttime;
            }

            public void setPay_starttime(String pay_starttime) {
                this.pay_starttime = pay_starttime;
            }

            public String getPay_endtime() {
                return pay_endtime;
            }

            public void setPay_endtime(String pay_endtime) {
                this.pay_endtime = pay_endtime;
            }

            public String getAuthenticate() {
                return authenticate;
            }

            public void setAuthenticate(String authenticate) {
                this.authenticate = authenticate;
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

            public String getMb_dynamic_voide() {
                return mb_dynamic_voide;
            }

            public void setMb_dynamic_voide(String mb_dynamic_voide) {
                this.mb_dynamic_voide = mb_dynamic_voide;
            }

            public String getMb_thing_status() {
                return mb_thing_status;
            }

            public void setMb_thing_status(String mb_thing_status) {
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

            public String getMb_thing() {
                return mb_thing;
            }

            public void setMb_thing(String mb_thing) {
                this.mb_thing = mb_thing;
            }

            public String getMb_thingstatus() {
                return mb_thingstatus;
            }

            public void setMb_thingstatus(String mb_thingstatus) {
                this.mb_thingstatus = mb_thingstatus;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }
        }
    }
}
