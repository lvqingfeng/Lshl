package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/14.
 */

public class PersonBasedataBean implements Serializable{

    /**
     * status : 1
     * info : {"uid":"3","phone":"17010207171","password":"e64e9d4196b83ed5e869dd0cdd450269","salt":"KE0EzK","isfrozen":"0","loginnum":"37","realname":"哈哈","avatar":"589c1dd0606b159009.jpg","idcard":null,"idcard_img_z":null,"idcard_img_f":"","authenticate":"0","age":"2022年1月20日 ","sex":"1","origin_cityname":"烟台市","origin_county":"后来慢慢","origin_address":"呵呵","live_cityname":"北京","live_county":"你不打","live_address":"可能性","live_cityno":"1001","profile":null,"enterprise":"1","add_time":"1484898247","is_hx":"1","hx_id":"5354f892ae1a47cdaa2af60a2a58d821","president":"1","member_grade":"3","pay_starttime":null,"pay_endtime":null,"busid":"1","industry":[{"in_id":"3","in_name":"旅游及票务"},{"in_id":"5","in_name":"办公文教"},{"in_id":"7","in_name":"游戏软件"}],"hide_account":"2","dynamic_background_picture":"dbp8.jpg","lj_county":"烟台市后来慢慢","xj_county":"北京你不打","all_origin":"烟台市后来慢慢呵呵","all_live":"北京你不打","grade":99,"enterprise_list":[{"en_id":"3","en_name":"猴年马月在","en_addtime":"1484920667"}],"good_list":[{"gd_id":"4","gd_goodname":"浪漫主义","gd_original_price":"250.00","gd_special_offer":"369.00","img":"588216e78df0b10417.png"}],"business_info":[{"business_id":"1","business_name":"甘肃省山东商会","business_position":"会长"}],"dynamic":[],"image_wall":["58a1640eeaa2864269.png"],"readme_allnum":"3","readme_num":0}
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

    public static class InfoBean implements Serializable{
        /**
         * uid : 3
         * phone : 17010207171
         * password : e64e9d4196b83ed5e869dd0cdd450269
         * salt : KE0EzK
         * isfrozen : 0
         * loginnum : 37
         * realname : 哈哈
         * avatar : 589c1dd0606b159009.jpg
         * idcard : null
         * idcard_img_z : null
         * idcard_img_f :
         * authenticate : 0
         * age : 2022年1月20日
         * sex : 1
         * origin_cityname : 烟台市
         * origin_county : 后来慢慢
         * origin_address : 呵呵
         * live_cityname : 北京
         * live_county : 你不打
         * live_address : 可能性
         * live_cityno : 1001
         * profile : null
         * enterprise : 1
         * add_time : 1484898247
         * is_hx : 1
         * hx_id : 5354f892ae1a47cdaa2af60a2a58d821
         * president : 1
         * member_grade : 3
         * pay_starttime : null
         * pay_endtime : null
         * busid : 1
         * industry : [{"in_id":"3","in_name":"旅游及票务"},{"in_id":"5","in_name":"办公文教"},{"in_id":"7","in_name":"游戏软件"}]
         * hide_account : 2
         * dynamic_background_picture : dbp8.jpg
         * lj_county : 烟台市后来慢慢
         * xj_county : 北京你不打
         * all_origin : 烟台市后来慢慢呵呵
         * all_live : 北京你不打
         * grade : 99
         * enterprise_list : [{"en_id":"3","en_name":"猴年马月在","en_addtime":"1484920667"}]
         * good_list : [{"gd_id":"4","gd_goodname":"浪漫主义","gd_original_price":"250.00","gd_special_offer":"369.00","img":"588216e78df0b10417.png"}]
         * business_info : [{"business_id":"1","business_name":"甘肃省山东商会","business_position":"会长"}]
         * dynamic : []
         * image_wall : ["58a1640eeaa2864269.png"]
         * readme_allnum : 3
         * readme_num : 0
         */

        private String uid;
        private String phone;
        private String password;
        private String salt;
        private String isfrozen;
        private String loginnum;
        private String realname;
        private String avatar;
        private String idcard;
        private String idcard_img_z;
        private String idcard_img_f;
        private String authenticate;
        private String age;
        private String sex;
        private String origin_cityname;
        private String origin_county;
        private String origin_address;
        private String live_cityname;
        private String live_county;
        private String live_address;
        private String live_cityno;
        private String profile;
        private String enterprise;
        private String add_time;
        private String is_hx;
        private String hx_id;
        private String president;
        private String member_grade;
        private String pay_starttime;
        private String pay_endtime;
        private String busid;
        private String hide_account;
        private String dynamic_background_picture;
        private String lj_county;
        private String xj_county;
        private String all_origin;
        private String all_live;
        private int grade;
        private int readme_allnum;
        private int readme_num;
        private int dynamic_num;
        private List<IndustryBean> industry;
        private List<EnterpriseListBean> enterprise_list;
        private List<GoodListBean> good_list;
        private List<BusinessInfoBean> business_info;
        private List<String> dynamic;
        private List<String> image_wall;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getIsfrozen() {
            return isfrozen;
        }

        public void setIsfrozen(String isfrozen) {
            this.isfrozen = isfrozen;
        }

        public String getLoginnum() {
            return loginnum;
        }

        public void setLoginnum(String loginnum) {
            this.loginnum = loginnum;
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

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIdcard_img_z() {
            return idcard_img_z;
        }

        public void setIdcard_img_z(String idcard_img_z) {
            this.idcard_img_z = idcard_img_z;
        }

        public String getIdcard_img_f() {
            return idcard_img_f;
        }

        public void setIdcard_img_f(String idcard_img_f) {
            this.idcard_img_f = idcard_img_f;
        }

        public String getAuthenticate() {
            return authenticate;
        }

        public void setAuthenticate(String authenticate) {
            this.authenticate = authenticate;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getOrigin_cityname() {
            return origin_cityname;
        }

        public void setOrigin_cityname(String origin_cityname) {
            this.origin_cityname = origin_cityname;
        }

        public String getOrigin_county() {
            return origin_county;
        }

        public void setOrigin_county(String origin_county) {
            this.origin_county = origin_county;
        }

        public String getOrigin_address() {
            return origin_address;
        }

        public void setOrigin_address(String origin_address) {
            this.origin_address = origin_address;
        }

        public String getLive_cityname() {
            return live_cityname;
        }

        public void setLive_cityname(String live_cityname) {
            this.live_cityname = live_cityname;
        }

        public String getLive_county() {
            return live_county;
        }

        public void setLive_county(String live_county) {
            this.live_county = live_county;
        }

        public String getLive_address() {
            return live_address;
        }

        public void setLive_address(String live_address) {
            this.live_address = live_address;
        }

        public String getLive_cityno() {
            return live_cityno;
        }

        public void setLive_cityno(String live_cityno) {
            this.live_cityno = live_cityno;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getEnterprise() {
            return enterprise;
        }

        public void setEnterprise(String enterprise) {
            this.enterprise = enterprise;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getIs_hx() {
            return is_hx;
        }

        public void setIs_hx(String is_hx) {
            this.is_hx = is_hx;
        }

        public String getHx_id() {
            return hx_id;
        }

        public void setHx_id(String hx_id) {
            this.hx_id = hx_id;
        }

        public String getPresident() {
            return president;
        }

        public void setPresident(String president) {
            this.president = president;
        }

        public String getMember_grade() {
            return member_grade;
        }

        public void setMember_grade(String member_grade) {
            this.member_grade = member_grade;
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

        public String getBusid() {
            return busid;
        }

        public void setBusid(String busid) {
            this.busid = busid;
        }

        public String getHide_account() {
            return hide_account;
        }

        public void setHide_account(String hide_account) {
            this.hide_account = hide_account;
        }

        public String getDynamic_background_picture() {
            return dynamic_background_picture;
        }

        public void setDynamic_background_picture(String dynamic_background_picture) {
            this.dynamic_background_picture = dynamic_background_picture;
        }

        public String getLj_county() {
            return lj_county;
        }

        public void setLj_county(String lj_county) {
            this.lj_county = lj_county;
        }

        public String getXj_county() {
            return xj_county;
        }

        public void setXj_county(String xj_county) {
            this.xj_county = xj_county;
        }

        public String getAll_origin() {
            return all_origin;
        }

        public void setAll_origin(String all_origin) {
            this.all_origin = all_origin;
        }

        public String getAll_live() {
            return all_live;
        }

        public void setAll_live(String all_live) {
            this.all_live = all_live;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getReadme_allnum() {
            return readme_allnum;
        }

        public void setReadme_allnum(int readme_allnum) {
            this.readme_allnum = readme_allnum;
        }

        public int getReadme_num() {
            return readme_num;
        }

        public void setReadme_num(int readme_num) {
            this.readme_num = readme_num;
        }

        public int getDynamic_num() {
            return dynamic_num;
        }

        public void setDynamic_num(int dynamic_num) {
            this.dynamic_num = dynamic_num;
        }

        public List<IndustryBean> getIndustry() {
            return industry;
        }

        public void setIndustry(List<IndustryBean> industry) {
            this.industry = industry;
        }

        public List<EnterpriseListBean> getEnterprise_list() {
            return enterprise_list;
        }

        public void setEnterprise_list(List<EnterpriseListBean> enterprise_list) {
            this.enterprise_list = enterprise_list;
        }

        public List<GoodListBean> getGood_list() {
            return good_list;
        }

        public void setGood_list(List<GoodListBean> good_list) {
            this.good_list = good_list;
        }

        public List<BusinessInfoBean> getBusiness_info() {
            return business_info;
        }

        public void setBusiness_info(List<BusinessInfoBean> business_info) {
            this.business_info = business_info;
        }

        public List<String> getDynamic() {
            return dynamic;
        }

        public void setDynamic(List<String> dynamic) {
            this.dynamic = dynamic;
        }

        public List<String> getImage_wall() {
            return image_wall;
        }

        public void setImage_wall(List<String> image_wall) {
            this.image_wall = image_wall;
        }

        public static class IndustryBean implements Serializable{
            /**
             * in_id : 3
             * in_name : 旅游及票务
             */

            private String in_id;
            private String in_name;

            public String getIn_id() {
                return in_id;
            }

            public void setIn_id(String in_id) {
                this.in_id = in_id;
            }

            public String getIn_name() {
                return in_name;
            }

            public void setIn_name(String in_name) {
                this.in_name = in_name;
            }
        }

        public static class EnterpriseListBean implements Serializable{
            /**
             * en_id : 3
             * en_name : 猴年马月在
             * en_addtime : 1484920667
             */

            private String en_id;
            private String en_name;
            private String en_addtime;

            public String getEn_id() {
                return en_id;
            }

            public void setEn_id(String en_id) {
                this.en_id = en_id;
            }

            public String getEn_name() {
                return en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public String getEn_addtime() {
                return en_addtime;
            }

            public void setEn_addtime(String en_addtime) {
                this.en_addtime = en_addtime;
            }
        }

        public static class GoodListBean implements Serializable{
            /**
             * gd_id : 4
             * gd_goodname : 浪漫主义
             * gd_original_price : 250.00
             * gd_special_offer : 369.00
             * img : 588216e78df0b10417.png
             */

            private String gd_id;
            private String gd_goodname;
            private String gd_original_price;
            private String gd_special_offer;
            private String img;

            public String getGd_id() {
                return gd_id;
            }

            public void setGd_id(String gd_id) {
                this.gd_id = gd_id;
            }

            public String getGd_goodname() {
                return gd_goodname;
            }

            public void setGd_goodname(String gd_goodname) {
                this.gd_goodname = gd_goodname;
            }

            public String getGd_original_price() {
                return gd_original_price;
            }

            public void setGd_original_price(String gd_original_price) {
                this.gd_original_price = gd_original_price;
            }

            public String getGd_special_offer() {
                return gd_special_offer;
            }

            public void setGd_special_offer(String gd_special_offer) {
                this.gd_special_offer = gd_special_offer;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class BusinessInfoBean implements Serializable{
            /**
             * business_id : 1
             * business_name : 甘肃省山东商会
             * business_position : 会长
             */

            private String business_id;
            private String business_name;
            private String business_position;

            public String getBusiness_id() {
                return business_id;
            }

            public void setBusiness_id(String business_id) {
                this.business_id = business_id;
            }

            public String getBusiness_name() {
                return business_name;
            }

            public void setBusiness_name(String business_name) {
                this.business_name = business_name;
            }

            public String getBusiness_position() {
                return business_position;
            }

            public void setBusiness_position(String business_position) {
                this.business_position = business_position;
            }
        }
    }
}
