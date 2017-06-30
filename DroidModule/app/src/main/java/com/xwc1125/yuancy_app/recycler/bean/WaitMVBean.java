package com.xwc1125.yuancy_app.recycler.bean;

/**
 * Created by xwc1125 on 2017/4/27.
 */

public class WaitMVBean {
    public static class DataBean {
        public static class ComingBean {
            private String nm;
            private String showInfo;
            private String scm;
            private String img;
            private String comingTitle;

            public String getNm() {
                return nm;
            }

            public String getShowInfo() {
                return showInfo;
            }

            public String getScm() {
                return scm;
            }

            public String getImg() {
                return img;
            }

            public String getComingTitle() {
                return comingTitle;
            }

            public void setNm(String nm) {
                this.nm = nm;
            }

            public void setShowInfo(String showInfo) {
                this.showInfo = showInfo;
            }

            public void setScm(String scm) {
                this.scm = scm;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setComingTitle(String comingTitle) {
                this.comingTitle = comingTitle;
            }

            @Override
            public String toString() {
                return "ComingBean{" +
                        "nm='" + nm + '\'' +
                        ", showInfo='" + showInfo + '\'' +
                        ", scm='" + scm + '\'' +
                        ", img='" + img + '\'' +
                        ", comingTitle='" + comingTitle + '\'' +
                        '}';
            }
        }
    }
}
