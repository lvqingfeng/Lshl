package com.lshl.bean;

import com.lshl.ui.info.group.CreateNewGroupActivity;

/**
 * 作者：吕振鹏
 * 创建时间：10月11日
 * 时间：20:44
 * 版本：v1.0.0
 * 类描述：群类别选择的实体类
 * 修改时间：
 */

public class GroupTypeResBean {

    private String groupName;
    private boolean isChecked;
    private CreateNewGroupActivity.GroupType mGroupType;

    public CreateNewGroupActivity.GroupType getGroupType() {
        return mGroupType;
    }

    public void setGroupType(CreateNewGroupActivity.GroupType mGroupType) {
        this.mGroupType = mGroupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
