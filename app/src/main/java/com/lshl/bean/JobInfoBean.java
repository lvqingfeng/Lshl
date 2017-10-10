package com.lshl.bean;

/**
 * 作者：吕振鹏
 * 创建时间：12月23日
 * 时间：14:39
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class JobInfoBean {


    /**
     * age : 1991年11月13日
     * avatar : 58524c6a1408e.jpg
     * cv_addtime : 1482402773
     * cv_education : 其他
     * cv_id : 4
     * cv_major :
     * cv_school :
     * cv_uid : 9
     * hx_id : 3fde1275d0082eae973b2b2feebd05e6
     * live_cityname : 甘肃省兰州市
     * live_county : 安宁区
     * origin_cityname : 聊城市
     * origin_county : 莘县
     * phone : 18393811681
     * profile : 瞬间感觉心情美美哒！
     * realname : 李菲菲
     * sex : 2
     */

    private String age;
    private String avatar;
    private String cv_addtime;
    private String cv_education;
    private String cv_id;
    private String cv_major;
    private String cv_school;
    private String cv_uid;
    private String hx_id;
    private String live_cityname;
    private String live_county;
    private String origin_cityname;
    private String origin_county;
    private String phone;
    private String profile;
    private String realname;
    private String sex;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCv_addtime() {
        return cv_addtime;
    }

    public void setCv_addtime(String cv_addtime) {
        this.cv_addtime = cv_addtime;
    }

    public String getCv_education() {
        return cv_education;
    }

    public void setCv_education(String cv_education) {
        this.cv_education = cv_education;
    }

    public String getCv_id() {
        return cv_id;
    }

    public void setCv_id(String cv_id) {
        this.cv_id = cv_id;
    }

    public String getCv_major() {
        return cv_major;
    }

    public void setCv_major(String cv_major) {
        this.cv_major = cv_major;
    }

    public String getCv_school() {
        return cv_school;
    }

    public void setCv_school(String cv_school) {
        this.cv_school = cv_school;
    }

    public String getCv_uid() {
        return cv_uid;
    }

    public void setCv_uid(String cv_uid) {
        this.cv_uid = cv_uid;
    }

    public String getHx_id() {
        return hx_id;
    }

    public void setHx_id(String hx_id) {
        this.hx_id = hx_id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        if (sex.equals("1")) {
            return "男";
        }
        return "女";
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
