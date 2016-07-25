package com.lemon95.ymtv.bean;

import java.util.List;

/**
 * Created by wuxiaotie on 16/7/23.
 */
public class RealSource {
    private String quality;
    private List<seg> seg;

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public List<seg> getSeg() {
        return seg;
    }

    public void setSeg(List<seg> seg) {
        this.seg = seg;
    }

    public static class seg {
        public String seq;
        public String size;
        public String seconds;
        public String furl;

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSeconds() {
            return seconds;
        }

        public void setSeconds(String seconds) {
            this.seconds = seconds;
        }

        public String getFurl() {
            return furl;
        }

        public void setFurl(String furl) {
            this.furl = furl;
        }
    }
}
