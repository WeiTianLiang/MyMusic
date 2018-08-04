package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Model;

import java.util.List;

/**
 * 专辑歌曲数据
 * Created by WTL on 2018/7/20.
 */

public class AlbumSongModel {

    private int code;
    private List<AlbumSongModel.songs> songs;

    public class songs {
        private String name;
        private long id;
        private List<ar> ar;
        private al al;

        public class ar {
            private List<String> alia;

            public List<String> getAlia() {
                return alia;
            }
        }

        public class al {
            private String picUrl;

            public String getPicUrl() {
                return picUrl;
            }
        }

        public List<songs.ar> getAr() {
            return ar;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public AlbumSongModel.songs.al getAl() {
            return al;
        }
    }

    public int getCode() {
        return code;
    }

    public List<AlbumSongModel.songs> getSongs() {
        return songs;
    }

}
