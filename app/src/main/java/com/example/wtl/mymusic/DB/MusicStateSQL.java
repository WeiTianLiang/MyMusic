package com.example.wtl.mymusic.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 当前音乐栏数据状态
 * Created by WTL on 2018/6/28.
 */

public class MusicStateSQL extends SQLiteOpenHelper{

    public static String SQL_NAME = "MSSQL";
    public static String ID = "_id";
    public static String SINGER_NAME = "name";
    public static String SONG_IMAGE = "image";
    public static String SONG_NAME = "songname";
    public static String MUSIC_STATE = "state";
    public static String MUSIC_ID = "musicId";

    public static String LM_TABLE = "LOMI";
    public static String LM_NAME = "lm_name";
    public static String LM_BRIEF = "lm_brief";
    public static String LM_ID = "lm_id";
    public static String LM_ADDRESS = "lm_address";

    public MusicStateSQL(Context context) {
        super(context, "MSSQL", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + SQL_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SINGER_NAME + " TEXT,"
            + MUSIC_STATE + " TEXT,"
            + SONG_NAME + " TEXT,"
            + MUSIC_ID + " TEXT,"
            + SONG_IMAGE + " TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + LM_TABLE + "("
                + LM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LM_NAME + " TEXT,"
                + LM_BRIEF + " TEXT,"
                + LM_ADDRESS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
