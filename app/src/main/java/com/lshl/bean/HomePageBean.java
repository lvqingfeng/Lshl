package com.lshl.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class HomePageBean {

    /**
     * status : 1
     * info : {"banner":[{"bn_id":"32","bn_mid":"25","bn_img":"/Data/Uploads/banner/3173658c7625ef2482.jpeg","bn_url":"","bn_urlid":"0","bn_sort":"0"}],"notice":[{"id":"597","title":"测试","onlylabel":"notice","img":"/Data/Uploads/article/640758d0943490ecc.jpeg","add_time":"1490064436"}],"member":[{"uid":"393","realname":"欢迎【刁怀坤】加入鲁商互联大家庭","avatar":"/Data/Uploads/avatar/58d095675b84e20040.jpg","add_time":"1490064675"},{"uid":"392","realname":"欢迎【刘敏】加入鲁商互联大家庭","avatar":"/Data/Uploads/avatar/58d088633153436093.jpg","add_time":"1490061336"},{"uid":"391","realname":"欢迎【马江涛】加入鲁商互联大家庭","avatar":"/Data/Uploads/avatar/58d04eae5376060521.jpg","add_time":"1490046524"},{"uid":"390","realname":"欢迎【古海鹰】加入鲁商互联大家庭","avatar":"/Data/Uploads/avatar/58d0038c69e3c70017.jpg","add_time":"1490027294"},{"uid":"388","realname":"欢迎【汪彬】加入鲁商互联大家庭","avatar":"/Data/Uploads/avatar/58cfe8a5a95c363212.jpg","add_time":"1490019384"}],"goods":[{"realname":"李淑娜","uid":"247","id":"304","title":"佰肤草三部曲","add_time":"1490056004","img":"/Data/Uploads/goods/58d073444fa1a74038.png"},{"realname":"李淑娜","uid":"247","id":"303","title":"初雅套盒","add_time":"1490055915","img":"/Data/Uploads/goods/58d072ebdcc6713633.png"},{"realname":"李淑娜","uid":"247","id":"302","title":"佰肤草蚕丝面膜","add_time":"1490055698","img":"/Data/Uploads/goods/58d072123c8ed99054.png"},{"realname":"古海鹰","uid":"390","id":"301","title":"上海旗袍","add_time":"1490028029","img":"/Data/Uploads/goods/58d005fd1842993696.png"},{"realname":"程志慧","uid":"370","id":"300","title":"心生爱目洗眼液【山东福瑞达】","add_time":"1490022121","img":"/Data/Uploads/goods/58cff0198c8e363772.png"}],"lushang":[{"id":"485","title":"鲁商互联：有奖建议收集","onlylabel":"lushang","img":"/Data/Uploads/article/1206858c61a42375c9.jpeg","add_time":"1489377858"},{"id":"579","title":"山东省委政法委机关传达学习全国\u201c两会\u201d精神","onlylabel":"lushang","img":"/Data/Uploads/article/2823958cfa961547ce.jpeg","add_time":"1490002903"},{"id":"540","title":"鲁商精英：欧阳俊升","onlylabel":"lushang","img":"/Data/Uploads/article/2572558cbb6662449c.jpeg","add_time":"1489745510"},{"id":"529","title":"鲁商精英：吴浩","onlylabel":"lushang","img":"/Data/Uploads/article/460058ca64e6e305e.jpeg","add_time":"1489656649"},{"id":"501","title":"山东人的酒文化","onlylabel":"lushang","img":"/Data/Uploads/article/333458c90b96a22c5.jpeg","add_time":"1489570710"}],"findhelper":[{"realname":"刘春波","uid":"371","id":"30","title":"孩子明年应该上小学一年级，","img":"/Data/Uploads/FindHelper/","add_time":"1490021020"},{"realname":"王飞","uid":"189","id":"27","title":"平安金融服务平台","img":"/Data/Uploads/FindHelper/58cbb2f98b48f64704.png","add_time":"1489744633"},{"realname":"赵书敏","uid":"134","id":"26","title":"足宝堂七天去除灰指甲","img":"/Data/Uploads/FindHelper/58ca92e76523538562.png","add_time":"1489670887"},{"realname":"于强","uid":"341","id":"25","title":"箴岩珠宝","img":"/Data/Uploads/FindHelper/58c9529d2fbb756843.png","add_time":"1489588893"},{"realname":"张祥泉","uid":"333","id":"24","title":"以茶会友","img":"/Data/Uploads/FindHelper/58c807dd5d82324600.png","add_time":"1489504221"}],"scandalous":[{"realname":"宋军洁","uid":"377","id":"36","title":"开心的一天","img":"/Data/Uploads/scandalous/58cfbcedb888266125.png","type":"1","add_time":"1490009325"},{"realname":"盖恒振","uid":"86","id":"33","title":"矿山机械","img":"/Data/Uploads/scandalous/58cb544068f3e89194.png","type":"1","add_time":"1489720384"},{"realname":"孙利刚","uid":"327","id":"32","title":"急需资金的老乡们，放款倒计时还剩七天了！","img":"/Data/Uploads/scandalous/58caa93fc0b0d78522.png","type":"1","add_time":"1489676607"},{"realname":"鲁商互联","uid":"184","id":"30","title":"红榜说明","img":"/Data/Uploads/scandalous/58bcbcdc10c5629431.png","type":"1","add_time":"1488764124"},{"realname":"梁兴凡","uid":"28","id":"25","title":"油漆没有，乳胶漆","img":"","type":"1","add_time":"1488462962"}],"lagua":[{"id":"595","title":"测试拉呱","onlylabel":"lagua","img":"/Data/Uploads/article/2421758d07f495ee3e.jpeg","add_time":"1490059081"}]}
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
        private List<BannerBean> banner;
        private List<NoticeBean> notice;
        private List<MemberBean> member;
        private List<GoodsBean> goods;
        private List<LushangBean> lushang;
        private List<FindhelperBean> findhelper;
        private List<ScandalousBean> scandalous;
        private List<LaguaBean> lagua;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<MemberBean> getMember() {
            return member;
        }

        public void setMember(List<MemberBean> member) {
            this.member = member;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<LushangBean> getLushang() {
            return lushang;
        }

        public void setLushang(List<LushangBean> lushang) {
            this.lushang = lushang;
        }

        public List<FindhelperBean> getFindhelper() {
            return findhelper;
        }

        public void setFindhelper(List<FindhelperBean> findhelper) {
            this.findhelper = findhelper;
        }

        public List<ScandalousBean> getScandalous() {
            return scandalous;
        }

        public void setScandalous(List<ScandalousBean> scandalous) {
            this.scandalous = scandalous;
        }

        public List<LaguaBean> getLagua() {
            return lagua;
        }

        public void setLagua(List<LaguaBean> lagua) {
            this.lagua = lagua;
        }

        public static class BannerBean {
            /**
             * bn_id : 32
             * bn_mid : 25
             * bn_img : /Data/Uploads/banner/3173658c7625ef2482.jpeg
             * bn_url :
             * bn_urlid : 0
             * bn_sort : 0
             */

            private String bn_id;
            private String bn_mid;
            private String bn_img;
            private String bn_url;
            private String bn_urlid;
            private String bn_sort;

            public String getBn_id() {
                return bn_id;
            }

            public void setBn_id(String bn_id) {
                this.bn_id = bn_id;
            }

            public String getBn_mid() {
                return bn_mid;
            }

            public void setBn_mid(String bn_mid) {
                this.bn_mid = bn_mid;
            }

            public String getBn_img() {
                return bn_img;
            }

            public void setBn_img(String bn_img) {
                this.bn_img = bn_img;
            }

            public String getBn_url() {
                return bn_url;
            }

            public void setBn_url(String bn_url) {
                this.bn_url = bn_url;
            }

            public String getBn_urlid() {
                return bn_urlid;
            }

            public void setBn_urlid(String bn_urlid) {
                this.bn_urlid = bn_urlid;
            }

            public String getBn_sort() {
                return bn_sort;
            }

            public void setBn_sort(String bn_sort) {
                this.bn_sort = bn_sort;
            }
        }

        public static class NoticeBean {
            /**
             * id : 597
             * title : 测试
             * onlylabel : notice
             * img : /Data/Uploads/article/640758d0943490ecc.jpeg
             * add_time : 1490064436
             */

            private String id;
            private String title;
            private String onlylabel;
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

            public String getOnlylabel() {
                return onlylabel;
            }

            public void setOnlylabel(String onlylabel) {
                this.onlylabel = onlylabel;
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

        public static class MemberBean {
            /**
             * uid : 393
             * realname : 欢迎【刁怀坤】加入鲁商互联大家庭
             * avatar : /Data/Uploads/avatar/58d095675b84e20040.jpg
             * add_time : 1490064675
             */

            private String uid;
            private String realname;
            private String avatar;
            private String add_time;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }

        public static class GoodsBean {
            /**
             * realname : 李淑娜
             * uid : 247
             * id : 304
             * title : 佰肤草三部曲
             * add_time : 1490056004
             * img : /Data/Uploads/goods/58d073444fa1a74038.png
             */

            private String realname;
            private String uid;
            private String id;
            private String title;
            private String add_time;
            private String img;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

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

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class LushangBean {
            /**
             * id : 485
             * title : 鲁商互联：有奖建议收集
             * onlylabel : lushang
             * img : /Data/Uploads/article/1206858c61a42375c9.jpeg
             * add_time : 1489377858
             */

            private String id;
            private String title;
            private String onlylabel;
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

            public String getOnlylabel() {
                return onlylabel;
            }

            public void setOnlylabel(String onlylabel) {
                this.onlylabel = onlylabel;
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

        public static class FindhelperBean {
            /**
             * realname : 刘春波
             * uid : 371
             * id : 30
             * title : 孩子明年应该上小学一年级，
             * img : /Data/Uploads/FindHelper/
             * add_time : 1490021020
             */

            private String realname;
            private String uid;
            private String id;
            private String title;
            private String img;
            private String add_time;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

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
        }

        public static class ScandalousBean {
            /**
             * realname : 宋军洁
             * uid : 377
             * id : 36
             * title : 开心的一天
             * img : /Data/Uploads/scandalous/58cfbcedb888266125.png
             * type : 1
             * add_time : 1490009325
             */

            private String realname;
            private String uid;
            private String id;
            private String title;
            private String img;
            private String type;
            private String add_time;

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }

        public static class LaguaBean {
            /**
             * id : 595
             * title : 测试拉呱
             * onlylabel : lagua
             * img : /Data/Uploads/article/2421758d07f495ee3e.jpeg
             * add_time : 1490059081
             */

            private String id;
            private String title;
            private String onlylabel;
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

            public String getOnlylabel() {
                return onlylabel;
            }

            public void setOnlylabel(String onlylabel) {
                this.onlylabel = onlylabel;
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
