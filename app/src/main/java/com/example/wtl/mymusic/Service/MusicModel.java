package com.example.wtl.mymusic.Service;

import java.util.List;

/**
 * 音乐详情信息
 * Created by WTL on 2018/6/26.
 */

public class MusicModel {

    private List<data> data;

    private int code;

    public class data {

        private String url;

        public String getUrl() {
            return url;
        }
    }

    public List<MusicModel.data> getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
