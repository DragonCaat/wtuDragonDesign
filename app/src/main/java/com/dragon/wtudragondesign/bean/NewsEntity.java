package com.dragon.wtudragondesign.bean;

import java.util.List;

/**
 * Created by dragon on 2018/5/9.
 * 新闻实体
 */

public class NewsEntity {

    /**
     * status : 1
     * message : ok
     * data : {"total":20,"page":2,"list":[{"id":"27","cateid":"12345","title":"12345","read_count":"0","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/be639201805092218433476.jpeg"},{"id":"26","cateid":"8989z11","title":"8989z11","read_count":"4","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/5116f201805061648477788.png"},{"id":"25","cateid":"8989z","title":"8989z","read_count":"2","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/0f06f201805061618371840.png"},{"id":"24","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"7","image":""},{"id":"23","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"16","image":""},{"id":"22","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"7","image":""},{"id":"21","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"2","image":""},{"id":"20","cateid":"1","title":"kkkkkkkk","read_count":"3","image":""},{"id":"19","cateid":"1","title":"ppppppppppppppp","read_count":"2","image":""},{"id":"18","cateid":"1","title":"ppppppppppppppp","read_count":"3","image":""},{"id":"17","cateid":"1","title":"asdf","read_count":"1","image":""},{"id":"16","cateid":"4","title":"韩国人太热","read_count":"8","image":""},{"id":"15","cateid":"1","title":"规范化股份","read_count":"32","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/095f8201805020743102024.png"},{"id":"14","cateid":"1","title":"a啊热狗","read_count":"8","image":""},{"id":"12","cateid":"2","title":"风格化","read_count":"6","image":""}]}
     */

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 20
         * page : 2
         * list : [{"id":"27","cateid":"12345","title":"12345","read_count":"0","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/be639201805092218433476.jpeg"},{"id":"26","cateid":"8989z11","title":"8989z11","read_count":"4","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/5116f201805061648477788.png"},{"id":"25","cateid":"8989z","title":"8989z","read_count":"2","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/0f06f201805061618371840.png"},{"id":"24","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"7","image":""},{"id":"23","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"16","image":""},{"id":"22","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"7","image":""},{"id":"21","cateid":"4rertrre","title":"wxvgfreere2345rd","read_count":"2","image":""},{"id":"20","cateid":"1","title":"kkkkkkkk","read_count":"3","image":""},{"id":"19","cateid":"1","title":"ppppppppppppppp","read_count":"2","image":""},{"id":"18","cateid":"1","title":"ppppppppppppppp","read_count":"3","image":""},{"id":"17","cateid":"1","title":"asdf","read_count":"1","image":""},{"id":"16","cateid":"4","title":"韩国人太热","read_count":"8","image":""},{"id":"15","cateid":"1","title":"规范化股份","read_count":"32","image":"http://p7zbhax7c.bkt.clouddn.com/2018/05/095f8201805020743102024.png"},{"id":"14","cateid":"1","title":"a啊热狗","read_count":"8","image":""},{"id":"12","cateid":"2","title":"风格化","read_count":"6","image":""}]
         */

        private int total;
        private int page;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 27
             * cateid : 12345
             * title : 12345
             * read_count : 0
             * image : http://p7zbhax7c.bkt.clouddn.com/2018/05/be639201805092218433476.jpeg
             */

            private String id;
            private String cateid;
            private String title;
            private String read_count;
            private String image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRead_count() {
                return read_count;
            }

            public void setRead_count(String read_count) {
                this.read_count = read_count;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
