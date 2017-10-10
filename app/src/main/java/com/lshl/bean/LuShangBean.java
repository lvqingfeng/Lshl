package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class LuShangBean {

    /**
     * status : 1
     * info : {"pages":1,"end":1,"list":[{"id":"485","title":"鲁商互联：有奖建议收集","top":"1","img":"/Data/Uploads/article/1206858c61a42375c9.jpeg","add_time":"1489377858"},{"id":"384","title":"内蒙古山东商会会长：郭建军","top":"0","img":"/Data/Uploads/article/695558be8f7260e13.jpeg","add_time":"1488883570"},{"id":"378","title":"拼搏前行,永不止步 \u2014\u2014记甘肃省山东商会会长李国颜","top":"0","img":"/Data/Uploads/article/1078758bf733ad722a.jpeg","add_time":"1488878893"},{"id":"321","title":"甘肃山东商会会长：李国颜","top":"0","img":"/Data/Uploads/article/406058bd3e13b89e2.jpeg","add_time":"1488797203"},{"id":"225","title":"鲁商互联兰州服务站站长：周银朝","top":"0","img":"/Data/Uploads/article/2869158bbc943f0f36.jpeg","add_time":"1488776707"},{"id":"144","title":"鲁商大爱：\u201c关爱环卫工人\u201d提供免费早餐","top":"0","img":"/Data/Uploads/article/191158ba5ea2af195.jpeg","add_time":"1488697631"},{"id":"136","title":" 辽宁省山东商会会长：李大利","top":"0","img":"/Data/Uploads/article/1605458ba1c26b2e9e.jpeg","add_time":"1488627315"},{"id":"134","title":"广东省山东商会会长：江南","top":"0","img":"/Data/Uploads/article/1090258ba1717a7783.jpeg","add_time":"1488702990"}]}
     */

    private int status;
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
        /**
         * pages : 1
         * end : 1
         * list : [{"id":"485","title":"鲁商互联：有奖建议收集","top":"1","img":"/Data/Uploads/article/1206858c61a42375c9.jpeg","add_time":"1489377858"},{"id":"384","title":"内蒙古山东商会会长：郭建军","top":"0","img":"/Data/Uploads/article/695558be8f7260e13.jpeg","add_time":"1488883570"},{"id":"378","title":"拼搏前行,永不止步 \u2014\u2014记甘肃省山东商会会长李国颜","top":"0","img":"/Data/Uploads/article/1078758bf733ad722a.jpeg","add_time":"1488878893"},{"id":"321","title":"甘肃山东商会会长：李国颜","top":"0","img":"/Data/Uploads/article/406058bd3e13b89e2.jpeg","add_time":"1488797203"},{"id":"225","title":"鲁商互联兰州服务站站长：周银朝","top":"0","img":"/Data/Uploads/article/2869158bbc943f0f36.jpeg","add_time":"1488776707"},{"id":"144","title":"鲁商大爱：\u201c关爱环卫工人\u201d提供免费早餐","top":"0","img":"/Data/Uploads/article/191158ba5ea2af195.jpeg","add_time":"1488697631"},{"id":"136","title":" 辽宁省山东商会会长：李大利","top":"0","img":"/Data/Uploads/article/1605458ba1c26b2e9e.jpeg","add_time":"1488627315"},{"id":"134","title":"广东省山东商会会长：江南","top":"0","img":"/Data/Uploads/article/1090258ba1717a7783.jpeg","add_time":"1488702990"}]
         */

        private int pages;
        private int end;
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
            /**
             * id : 485
             * title : 鲁商互联：有奖建议收集
             * top : 1
             * img : /Data/Uploads/article/1206858c61a42375c9.jpeg
             * add_time : 1489377858
             */

            private String id;
            private String title;
            private String top;
            private String img;
            private String add_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }
}
