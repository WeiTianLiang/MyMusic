package com.example.wtl.mymusic.MyMusic.Model;

/**
 * 我的音乐数据
 * Created by WTL on 2018/7/29.
 */

public class MyMusicModel {

    private String name;
    private String brief;
    private String address;

    public MyMusicModel(String name,String brief,String address) {
        this.name = name;
        this.brief = brief;
        this.address = address;
    }

    public String getBrief() {
        return brief;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
