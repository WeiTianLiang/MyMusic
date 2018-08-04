package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Model;

import java.util.List;

/**
 * MV数据获取
 * Created by WTL on 2018/7/21.
 */

public class MVModel {

    private int code;
    private List<mvs> mvs;

    public class mvs {
        private long id;//视频id
        private String name;//名称
        private String imgurl;//图片

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }

        public String getImgurl() {
            return imgurl;
        }
    }

    public int getCode() {
        return code;
    }

    public List<MVModel.mvs> getMvs() {
        return mvs;
    }
}
