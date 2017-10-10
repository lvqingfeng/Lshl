package com.lshl.bean;

/**
 * 作者：吕振鹏
 * 创建时间：10月22日
 * 时间：10:15
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class MenuItemBean {

    private String menuName;
    private int menuRes;
    private int menuBg;

    public int getMenuBg() {
        return menuBg;
    }

    public void setMenuBg(int menuBg) {
        this.menuBg = menuBg;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuRes() {
        return menuRes;
    }

    public void setMenuRes(int menuRes) {
        this.menuRes = menuRes;
    }
}
