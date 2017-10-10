package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class PresidentListBean {

    /**
     * status : 1
     * info : {"pages":1,"end":0,"list":[{"id":"38","title":"小游戏","img":"5847b4719b57e.png,5847b4719b94e.png","add_time":"1481094257","imgs":"5847b4719b57e.png"},{"id":"37","title":"小心翼翼","img":"5847b3078f524.png,5847b3078f8f5.png","add_time":"1481093895","imgs":"5847b3078f524.png"},{"id":"36","title":"小心翼翼","img":"5847b28cac6bd.png,5847b28cace5e.png","add_time":"1481093772","imgs":"5847b28cac6bd.png"},{"id":"34","title":"小游戏","img":"5847afcfb4932.png","add_time":"1481093071","imgs":"5847afcfb4932.png"},{"id":"31","title":"吐吐吐吐","img":"584797561f23f.png,584797561f60f.png,584797561f9e0.png","add_time":"1481086806","imgs":"584797561f23f.png"},{"id":"29","title":"兔兔","img":"58479110d425c.png,58479110d462c.png,58479110d49fd.png,58479110d4dcd.png,58479110d519e.png,58479110d556f.png","add_time":"1481085200","imgs":"58479110d425c.png"},{"id":"28","title":"自由职业者","img":"5847900a15925.png,5847900a15cf6.png,5847900a160c7.png","add_time":"1481084938","imgs":"5847900a15925.png"},{"id":"27","title":"呵呵呵","img":"58478d667c110.png,58478d667c8b1.png,58478d667cc82.png","add_time":"1481084262","imgs":"58478d667c110.png"},{"id":"26","title":"兔兔","img":"58478968ec1a5.png,58478968ec575.png,58478968ecd16.png","add_time":"1481083240","imgs":"58478968ec1a5.png"}]}
     */

    private int status;
    /**
     * pages : 1
     * end : 0
     * list : [{"id":"38","title":"小游戏","img":"5847b4719b57e.png,5847b4719b94e.png","add_time":"1481094257","imgs":"5847b4719b57e.png"},{"id":"37","title":"小心翼翼","img":"5847b3078f524.png,5847b3078f8f5.png","add_time":"1481093895","imgs":"5847b3078f524.png"},{"id":"36","title":"小心翼翼","img":"5847b28cac6bd.png,5847b28cace5e.png","add_time":"1481093772","imgs":"5847b28cac6bd.png"},{"id":"34","title":"小游戏","img":"5847afcfb4932.png","add_time":"1481093071","imgs":"5847afcfb4932.png"},{"id":"31","title":"吐吐吐吐","img":"584797561f23f.png,584797561f60f.png,584797561f9e0.png","add_time":"1481086806","imgs":"584797561f23f.png"},{"id":"29","title":"兔兔","img":"58479110d425c.png,58479110d462c.png,58479110d49fd.png,58479110d4dcd.png,58479110d519e.png,58479110d556f.png","add_time":"1481085200","imgs":"58479110d425c.png"},{"id":"28","title":"自由职业者","img":"5847900a15925.png,5847900a15cf6.png,5847900a160c7.png","add_time":"1481084938","imgs":"5847900a15925.png"},{"id":"27","title":"呵呵呵","img":"58478d667c110.png,58478d667c8b1.png,58478d667cc82.png","add_time":"1481084262","imgs":"58478d667c110.png"},{"id":"26","title":"兔兔","img":"58478968ec1a5.png,58478968ec575.png,58478968ecd16.png","add_time":"1481083240","imgs":"58478968ec1a5.png"}]
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
         * id : 38
         * title : 小游戏
         * img : 5847b4719b57e.png,5847b4719b94e.png
         * add_time : 1481094257
         * imgs : 5847b4719b57e.png
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
            private String id;
            private String title;
            private String img;
            private String add_time;
            private String imgs;

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

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }
        }
    }
}
