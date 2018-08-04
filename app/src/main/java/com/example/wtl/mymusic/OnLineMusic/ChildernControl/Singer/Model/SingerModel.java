package com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Model;

import java.util.List;

/**
 * 歌手数据
 * Created by WTL on 2018/6/21.
 */

public class SingerModel {

    private List<artists> artists;

    public class artists {
        private String img1v1Url;//歌手照片
        private String name;//歌手名称
        private int id;//歌手ID

        public int getId() {
            return id;
        }

        public String getImg1v1Url() {
            return img1v1Url;
        }

        public String getName() {
            return name;
        }
    }

    public List<SingerModel.artists> getArtists() {
        return artists;
    }
}
