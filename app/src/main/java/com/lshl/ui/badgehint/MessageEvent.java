package com.lshl.ui.badgehint;

/**
 * 项目名称：鲁商互联
 * 类描述：
 * 创建人：吕清锋
 * 创建时间：2017/3/23 9:58
 * 修改备注：
 */
public class MessageEvent {
    private int messageNum;

    public MessageEvent(int messageNum) {
        this.messageNum = messageNum;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }
}
