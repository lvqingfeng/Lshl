package com.lshl.bean;

import java.io.Serializable;

/**
 * 作者：吕振鹏
 * 创建时间：11月04日
 * 时间：16:52
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class UpdateGoodsBean implements Serializable {

    private String token;
    private String gid;
    private String type;//	int	Y		类型
    private String goodname;    //string	Y		产品名称
    private String original_price;//	string	Y		原价
    private String special_offer;    //string	Y		老乡价
    private String phone; //string	Y		电话
    private String info;//	联系人
    private String cityno;//城市编号
    private String cityname;//城市名称
    private String old_img;//剩余图片名称
    private String del_img;//删除图片
    private String one_class;
    private String two_class;
    private String enid;

    public String getEnid() {
        return enid;
    }

    public void setEnid(String enid) {
        this.enid = enid;
    }

    public String getOne_class() {
        return one_class;
    }

    public void setOne_class(String one_class) {
        this.one_class = one_class;
    }

    public String getTwo_class() {
        return two_class;
    }

    public void setTwo_class(String two_class) {
        this.two_class = two_class;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOld_img() {
        return old_img;
    }

    public void setOld_img(String old_img) {
        this.old_img = old_img;
    }

    public String getDel_img() {
        return del_img;
    }

    public void setDel_img(String del_img) {
        this.del_img = del_img;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getSpecial_offer() {
        return special_offer;
    }

    public void setSpecial_offer(String special_offer) {
        this.special_offer = special_offer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCityno() {
        return cityno;
    }

    public void setCityno(String cityno) {
        this.cityno = cityno;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    /*    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UpdateGoodsBean) {
            if (((UpdateGoodsBean) obj).getGoodname().equals(this.goodname) && ((UpdateGoodsBean) obj).getType().equals(this.type) && ((UpdateGoodsBean) obj).getSelling().equals(this.selling)
                    && ((UpdateGoodsBean) obj).getOriginal_price().equals(this.original_price) && ((UpdateGoodsBean) obj).getSpecial_offer().equals(this.special_offer) && ((UpdateGoodsBean) obj).getPhone1().equals(this.phone1)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }*/
}
