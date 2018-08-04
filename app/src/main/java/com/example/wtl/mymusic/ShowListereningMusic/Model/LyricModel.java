package com.example.wtl.mymusic.ShowListereningMusic.Model;

/**
 * 歌词类
 * Created by WTL on 2018/7/1.
 */

public class LyricModel {

    String node;
    long start;
    long end;

    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }

    public String getNode() {
        return node;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setStart(long start) {
        this.start = start;
    }

}
