package com.example.wtl.mymusic.Main.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.DB.MusicStateSQL;
import com.example.wtl.mymusic.R;

/**
 * 逻辑实现
 * Created by WTL on 2018/6/28.
 */

public class MainPresenterCompl implements IMainPresenter {

    private MusicStateSQL sql;
    private SQLiteDatabase database;
    private SharedPreferences preferences;
    private Context context;

    public MainPresenterCompl(Context context) {
        this.context = context;
    }

    @Override
    public void setSqlData(TextView music_name, TextView music_actor_name, ImageView playingstop, ImageView music_image) {
        if (sql == null) {
            sql = new MusicStateSQL(context);
        }
        if (database == null) {
            database = sql.getWritableDatabase();
        }
        Cursor cursor = database.query(MusicStateSQL.SQL_NAME, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            music_name.setText(cursor.getString(cursor.getColumnIndex("songname")));
            music_actor_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            Glide.with(context).load(cursor.getString(cursor.getColumnIndex("image"))).into(music_image);
            if (cursor.getString(cursor.getColumnIndex("state")).equals("stop")) {
                playingstop.setImageResource(R.mipmap.musicbegin);
            } else if (cursor.getString(cursor.getColumnIndex("state")).equals("play")) {
                playingstop.setImageResource(R.mipmap.musicstop);
            }
        }
    }

    @Override
    public void addSqlData(String music_id, String music_name, String music_actor_name, String music_state, String music_image) {
        preferences = context.getSharedPreferences("first_act",0);
        Boolean user_first = preferences.getBoolean("FIRST",true);
        if (sql == null) {
            sql = new MusicStateSQL(context);
        }
        if (database == null) {
            database = sql.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        if(user_first) {
            preferences.edit().putBoolean("FIRST", false).apply();
            cv.put(MusicStateSQL.SINGER_NAME, music_actor_name);
            cv.put(MusicStateSQL.SONG_IMAGE, music_image);
            cv.put(MusicStateSQL.SONG_NAME, music_name);
            cv.put(MusicStateSQL.MUSIC_ID, music_id);
            cv.put(MusicStateSQL.MUSIC_STATE, music_state);
            database.insert(MusicStateSQL.SQL_NAME, null, cv);
            return;
        }
        if(music_id != null) {
            cv.put(MusicStateSQL.MUSIC_ID, music_id);
        }
        if (music_actor_name != null) {
            cv.put(MusicStateSQL.SINGER_NAME, music_actor_name);
        }
        if (music_image != null) {
            cv.put(MusicStateSQL.SONG_IMAGE, music_image);
        }
        if (music_name != null) {
            cv.put(MusicStateSQL.SONG_NAME, music_name);
        }
        cv.put(MusicStateSQL.MUSIC_STATE, music_state);
        database.update(MusicStateSQL.SQL_NAME, cv, "_id=?", new String[]{"1"});
    }
}
