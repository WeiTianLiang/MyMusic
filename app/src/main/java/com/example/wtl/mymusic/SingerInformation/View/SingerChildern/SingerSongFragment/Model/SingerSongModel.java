package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Model;

import java.util.List;

/**
 * 歌手音乐加载
 * Created by WTL on 2018/6/22.
 */

public class SingerSongModel {

    private int code;

    private List<hotSongs> hotSongs;

    private artist artist;

    public class hotSongs {
        private String name;
        private long id;
        private int t;
        private al al;

        public String getName() {
            return name;
        }

        public int getT() {
            return t;
        }

        public SingerSongModel.hotSongs.al getAl() {
            return al;
        }

        public long getId() {
            return id;
        }

        public class al {
            private String name;
            private String picUrl;

            public String getName() {
                return name;
            }

            public String getPicUrl() {
                return picUrl;
            }
        }
    }

    public class artist {
        private String name;

        public String getName() {
            return name;
        }
    }

    public int getCode() {
        return code;
    }

    public List<SingerSongModel.hotSongs> getHotSongs() {
        return hotSongs;
    }

    public SingerSongModel.artist getArtist() {
        return artist;
    }
}
