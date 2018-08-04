package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Model;

import java.util.List;

/**
 * mv数据
 * Created by WTL on 2018/7/20.
 */

public class SingerAlbumModel {

    private int code;

    private List<hotAlbums> hotAlbums;

    public class hotAlbums {
        private String name;
        private String picUrl;
        private long id;
        private int size;

        public int getSize() {
            return size;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPicUrl() {
            return picUrl;
        }
    }

    public int getCode() {
        return code;
    }

    public List<SingerAlbumModel.hotAlbums> getHotAlbums() {
        return hotAlbums;
    }
}
