package com.lshl.bean.request;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ChooseAddressBean{
    private List<InfoBean> mList;

    public List<InfoBean> getmList() {
        return mList;
    }

    public void setmList(List<InfoBean> mList) {
        this.mList = mList;
    }

    public static class InfoBean{
        private String describe;
        private String address;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
