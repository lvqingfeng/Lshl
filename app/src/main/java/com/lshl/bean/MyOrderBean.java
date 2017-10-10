package com.lshl.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */

public class MyOrderBean implements Serializable {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"go_id":"1","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686227","go_status":"0","go_reject_info":null,"go_edittime":"1477885710","go_order":"23763b4d57cb39f49f038b99e9df7ab8","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"2","go_goodsid":"4","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":null,"go_addtime":"1476373110","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"994a863975c2aa451c1d08deb1398f1a","go_buy_del":"0","go_sell_del":"0","gd_goodname":"红富士苹果","gd_img1":"1150957fdaf30161e8.jpeg","gd_selling":"无公害自家种植红富士苹果","gd_original_price":"12.80","gd_special_offer":"8.80","k":"1"},{"go_id":"4","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686438","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"402bbf18d9084ee97560261182c813ee","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"5","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686441","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cccc656294c0c1692428ee6f595eae56","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"6","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686441","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cccc656294c0c1692428ee6f595eae56","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"7","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686458","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"c856f237b0e9f13b88c04a3544605695","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"8","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686460","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2046d7ee9ef9a2e05905a3100a57e5b8","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"9","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686470","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"09771624474e0baadc76223f40a09db1","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"23","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476757804","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"8d2ee6b3ca4d3a2f703c9ec1e6337e63","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"24","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476844920","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"21d7833c71f9fd1ad357986a02293111","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"25","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845099","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"850dfe6580eb7945dc9fd64d8c12ff18","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"26","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845279","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2211edc06a66dc4f222dd6ee8ba8f818","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"27","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845279","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2211edc06a66dc4f222dd6ee8ba8f818","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"28","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845279","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2211edc06a66dc4f222dd6ee8ba8f818","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"29","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845606","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cf74c54f3e08e927023b3645dafdd2c5","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"33","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884600","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"40f96997bc06368a7fe053834fe75692","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null},{"go_id":"34","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884602","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"861a8279df582a7d9dae3603194e854a","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null},{"go_id":"35","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884603","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cd872da16135a80d095584f2c1c978cd","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null},{"go_id":"36","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884604","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"afdad2bad3e121e3c2be4033dd2d4dfc","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 1
     * list : [{"go_id":"1","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686227","go_status":"0","go_reject_info":null,"go_edittime":"1477885710","go_order":"23763b4d57cb39f49f038b99e9df7ab8","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"2","go_goodsid":"4","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":null,"go_addtime":"1476373110","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"994a863975c2aa451c1d08deb1398f1a","go_buy_del":"0","go_sell_del":"0","gd_goodname":"红富士苹果","gd_img1":"1150957fdaf30161e8.jpeg","gd_selling":"无公害自家种植红富士苹果","gd_original_price":"12.80","gd_special_offer":"8.80","k":"1"},{"go_id":"4","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686438","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"402bbf18d9084ee97560261182c813ee","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"5","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686441","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cccc656294c0c1692428ee6f595eae56","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"6","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686441","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cccc656294c0c1692428ee6f595eae56","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"7","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686458","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"c856f237b0e9f13b88c04a3544605695","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"8","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686460","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2046d7ee9ef9a2e05905a3100a57e5b8","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"9","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476686470","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"09771624474e0baadc76223f40a09db1","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"23","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476757804","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"8d2ee6b3ca4d3a2f703c9ec1e6337e63","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"24","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476844920","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"21d7833c71f9fd1ad357986a02293111","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"25","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845099","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"850dfe6580eb7945dc9fd64d8c12ff18","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"26","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845279","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2211edc06a66dc4f222dd6ee8ba8f818","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"27","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845279","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2211edc06a66dc4f222dd6ee8ba8f818","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"28","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845279","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"2211edc06a66dc4f222dd6ee8ba8f818","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"29","go_goodsid":"27","go_buy_uid":"3","go_sell_uid":"7","go_num":"1","go_arrivetime":"","go_addtime":"1476845606","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cf74c54f3e08e927023b3645dafdd2c5","go_buy_del":"0","go_sell_del":"0","gd_goodname":"哈哈哈","gd_img1":"12102580200ab42b33.jpeg","gd_selling":"家具啊","gd_original_price":"100.00","gd_special_offer":"100.00","k":null},{"go_id":"33","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884600","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"40f96997bc06368a7fe053834fe75692","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null},{"go_id":"34","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884602","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"861a8279df582a7d9dae3603194e854a","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null},{"go_id":"35","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884603","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"cd872da16135a80d095584f2c1c978cd","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null},{"go_id":"36","go_goodsid":"28","go_buy_uid":"3","go_sell_uid":"9","go_num":"1","go_arrivetime":"","go_addtime":"1477884604","go_status":"1","go_reject_info":null,"go_edittime":null,"go_order":"afdad2bad3e121e3c2be4033dd2d4dfc","go_buy_del":"0","go_sell_del":"0","gd_goodname":"iPhone 10","gd_img1":"29069580607ff8a916.jpeg","gd_selling":"iPhone  10 爆款手机已发售！不要9999、不要5888  只要998，没错，你没看错，也没听错，只要998...","gd_original_price":"9999.00","gd_special_offer":"998.00","k":null}]
     */

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

    public static class InfoBean {
        private int pages;
        private int end;
        /**
         * go_id : 1
         * go_goodsid : 27
         * go_buy_uid : 3
         * go_sell_uid : 7
         * go_num : 1
         * go_arrivetime :
         * go_addtime : 1476686227
         * go_status : 0
         * go_reject_info : null
         * go_edittime : 1477885710
         * go_order : 23763b4d57cb39f49f038b99e9df7ab8
         * go_buy_del : 0
         * go_sell_del : 0
         * gd_goodname : 哈哈哈
         * gd_img1 : 12102580200ab42b33.jpeg
         * gd_selling : 家具啊
         * gd_original_price : 100.00
         * gd_special_offer : 100.00
         * k : null
         */

        private List<ListBean> list;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String go_id;
            private String go_goodsid;
            private String go_buy_uid;
            private String go_sell_uid;
            private String go_num;
            private String go_arrivetime;
            private String go_addtime;
            private String go_status;
            private Object go_reject_info;
            private String go_edittime;
            private String go_order;
            private String go_buy_del;
            private String go_sell_del;
            private String gd_goodname;
            private String gd_img1;
            private String gd_selling;
            private String gd_original_price;
            private String gd_special_offer;
            private String k;

            public String getGo_id() {
                return go_id;
            }

            public void setGo_id(String go_id) {
                this.go_id = go_id;
            }

            public String getGo_goodsid() {
                return go_goodsid;
            }

            public void setGo_goodsid(String go_goodsid) {
                this.go_goodsid = go_goodsid;
            }

            public String getGo_buy_uid() {
                return go_buy_uid;
            }

            public void setGo_buy_uid(String go_buy_uid) {
                this.go_buy_uid = go_buy_uid;
            }

            public String getGo_sell_uid() {
                return go_sell_uid;
            }

            public void setGo_sell_uid(String go_sell_uid) {
                this.go_sell_uid = go_sell_uid;
            }

            public String getGo_num() {
                return go_num;
            }

            public void setGo_num(String go_num) {
                this.go_num = go_num;
            }

            public String getGo_arrivetime() {
                return go_arrivetime;
            }

            public void setGo_arrivetime(String go_arrivetime) {
                this.go_arrivetime = go_arrivetime;
            }

            public String getGo_addtime() {
                return go_addtime;
            }

            public void setGo_addtime(String go_addtime) {
                this.go_addtime = go_addtime;
            }

            public String getGo_status() {
                return go_status;
            }

            public void setGo_status(String go_status) {
                this.go_status = go_status;
            }

            public Object getGo_reject_info() {
                return go_reject_info;
            }

            public void setGo_reject_info(Object go_reject_info) {
                this.go_reject_info = go_reject_info;
            }

            public String getGo_edittime() {
                return go_edittime;
            }

            public void setGo_edittime(String go_edittime) {
                this.go_edittime = go_edittime;
            }

            public String getGo_order() {
                return go_order;
            }

            public void setGo_order(String go_order) {
                this.go_order = go_order;
            }

            public String getGo_buy_del() {
                return go_buy_del;
            }

            public void setGo_buy_del(String go_buy_del) {
                this.go_buy_del = go_buy_del;
            }

            public String getGo_sell_del() {
                return go_sell_del;
            }

            public void setGo_sell_del(String go_sell_del) {
                this.go_sell_del = go_sell_del;
            }

            public String getGd_goodname() {
                return gd_goodname;
            }

            public void setGd_goodname(String gd_goodname) {
                this.gd_goodname = gd_goodname;
            }

            public String getGd_img1() {
                return gd_img1;
            }

            public void setGd_img1(String gd_img1) {
                this.gd_img1 = gd_img1;
            }

            public String getGd_selling() {
                return gd_selling;
            }

            public void setGd_selling(String gd_selling) {
                this.gd_selling = gd_selling;
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

            public String getK() {
                return k;
            }

            public void setK(String k) {
                this.k = k;
            }
        }
    }
}
