package com.lshl.bean;

/**
 * 作者：吕振鹏
 * 创建时间：11月01日
 * 时间：18:19
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class GoodsOrderBean {

    private String code;
    private String name;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
