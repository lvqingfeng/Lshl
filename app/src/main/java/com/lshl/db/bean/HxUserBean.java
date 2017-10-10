package com.lshl.db.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lshl.utils.DateUtils;

/**
 * 作者：吕振鹏
 * 创建时间：10月15日
 * 时间：15:04
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class HxUserBean implements Parcelable {

    /**
     * avatar : 57fa4a479c6b4.png
     * hx_id : 2fcb13c32827c61a5d040c82e03eab28
     * phone : 18366189918
     * realname : 魁大圣
     * uid : 3
     */
    private int id;
    private String avatar;
    private String hx_id;
    private String phone;
    private String realname;
    private String uid;
    /**
     * age : 1994年8月13日
     * live_cityname : 山东省淄博市
     * sex : 1
     */

    private String age;
    private String live_cityname;
    private String sex;
    /**
     * authenticate : 1
     * grade : 3
     */

    private String authenticate;//是否是会长
    private int grade;//等级

    public String getAgeBirthDate() {
        return DateUtils.getAgeBirthDate(age);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHx_id() {
        return hx_id;
    }

    public void setHx_id(String hx_id) {
        this.hx_id = hx_id;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.avatar);
        dest.writeString(this.hx_id);
        dest.writeString(this.phone);
        dest.writeString(this.realname);
        dest.writeString(this.uid);
    }

    public HxUserBean() {
    }

    protected HxUserBean(Parcel in) {
        this.id = in.readInt();
        this.avatar = in.readString();
        this.hx_id = in.readString();
        this.phone = in.readString();
        this.realname = in.readString();
        this.uid = in.readString();
    }

    public static final Creator<HxUserBean> CREATOR = new Creator<HxUserBean>() {
        @Override
        public HxUserBean createFromParcel(Parcel source) {
            return new HxUserBean(source);
        }

        @Override
        public HxUserBean[] newArray(int size) {
            return new HxUserBean[size];
        }
    };

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLive_cityname() {
        return live_cityname;
    }

    public void setLive_cityname(String live_cityname) {
        this.live_cityname = live_cityname;
    }

    public String getSexStr() {
        return "1".equals(sex) ? "男" : "女";
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(String authenticate) {
        this.authenticate = authenticate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
