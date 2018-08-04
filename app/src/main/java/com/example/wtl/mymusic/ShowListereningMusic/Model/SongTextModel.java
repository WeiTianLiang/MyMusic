package com.example.wtl.mymusic.ShowListereningMusic.Model;

/**
 * 歌词类
 * Created by WTL on 2018/6/29.
 */

public class SongTextModel {

    private int code;

    private lrc lrc;

    public class lrc {
        String lyric;

        public String getLyric() {
            return lyric;
        }
    }

    public int getCode() {
        return code;
    }

    public SongTextModel.lrc getLrc() {
        return lrc;
    }
}
