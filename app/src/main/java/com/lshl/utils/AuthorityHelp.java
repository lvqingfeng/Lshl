package com.lshl.utils;

import com.lshl.base.LshlApplication;
import com.lshl.bean.AuthorityCheckBean;

/**
 * 作者：吕振鹏
 * 创建时间：12月12日
 * 时间：22:36
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class AuthorityHelp {

    public static void setRealnameStatus(int status) {
        AuthorityCheckBean bean = LshlApplication.getApplication().getAuthorityBean();
        if (bean != null) {
            bean.setRealname(status);
            LshlApplication.getApplication().setAuthorityBean(bean);
        }
    }

}
