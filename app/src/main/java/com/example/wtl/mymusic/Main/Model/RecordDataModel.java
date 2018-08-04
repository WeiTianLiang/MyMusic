package com.example.wtl.mymusic.Main.Model;

/**
 * 歌手数据记录
 * Created by WTL on 2018/7/20.
 */

public class RecordDataModel {

    private String SingerPic;
    private String SingerName;
    private String SingerID;

    public RecordDataModel(String SingerPic,String SingerName,String SingerID) {
        this.SingerPic = SingerPic;
        this.SingerName = SingerName;
        this.SingerID = SingerID;
    }

    public String getSingerID() {
        return SingerID;
    }

    public String getSingerName() {
        return SingerName;
    }

    public String getSingerPic() {
        return SingerPic;
    }
}
