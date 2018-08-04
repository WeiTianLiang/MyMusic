package com.example.wtl.mymusic.Service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.wtl.mymusic.DB.MusicStateSQL;
import com.example.wtl.mymusic.ShowListereningMusic.Presenter.IUpdateProcess;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 音乐播放service
 * Created by WTL on 2018/6/23.
 */

public class PlayingMusicService extends Service implements MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;
    /**
     * 音频地址
     */
    private String url = "http://music.163.com/song/media/outer/url?id=";

    private String musicurl;

    private MusicStateSQL sql;
    private SQLiteDatabase database;

    private String songId;
    private String Gurl;
    private String GsongId;
    private int stat = 0;
    private TimerTask task;

    /**
     * 更新进度接口
     */
    private IUpdateProcess updateProcess1;
    private Timer timer;

    private MyBinder binder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        public PlayingMusicService getService() {
            return PlayingMusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (timer == null) {
            timer = new Timer();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String FLAG_STATE = intent.getStringExtra("flag_state");
        songId = intent.getStringExtra("now_songId");
        String address = intent.getStringExtra("My_Address");
        if(address != null) {
            musicurl = address;
            play(musicurl);
        }
        if(songId != null) {
            GsongId = songId;
        }
        if (songId != null) {
            musicurl = url + songId + ".mp3";
            if (FLAG_STATE == null) {
                play(musicurl);
            }
        }
        if (FLAG_STATE != null) {
            switch (FLAG_STATE) {
                case "play":
                    start();
                    break;
                case "pause":
                    pause();
                    break;
                default:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //播放
    private void play(String url) {
        if (Gurl == null) {
            Gurl = url;
        } else {
            if (Gurl.equals(url)) {
                stat = 1;
            } else {
                stat = 0;
            }
        }
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnErrorListener(this);
        try {
            mediaPlayer.reset();
            mediaPlayer.setLooping(true);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            timer.schedule(getTimerTask(), 0, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //暂停
    private void pause() {
        if (mediaPlayer.isPlaying() && mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    //启动
    private void start() {
        if (GsongId != null) {
            mediaPlayer.start();
        } else {
            if (sql == null) {
                sql = new MusicStateSQL(this);
            }
            if (database == null) {
                database = sql.getWritableDatabase();
            }
            Cursor cursor = database.query(MusicStateSQL.SQL_NAME, null, null, null, null, null, null);
            if (cursor.moveToLast()) {
                String id = cursor.getString(cursor.getColumnIndex("musicId"));
                play(url + id + ".mp3");
                songId = id;
                GsongId = id;
            }
        }

    }

    @Override
    public void onDestroy() {
        if (sql == null) {
            sql = new MusicStateSQL(this);
        }
        if (database == null) {
            database = sql.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(MusicStateSQL.MUSIC_STATE, "stop");
        database.update(MusicStateSQL.SQL_NAME, cv, "_id=?", new String[]{"1"});
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        Log.e("加载进度：", i + "%");
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.e("搓搓五五", "歌曲播放错误！ ");
        return false;
    }

    private TimerTask getTimerTask() {
        if(task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    if (mediaPlayer == null)
                        return;
                    if (mediaPlayer.isPlaying()) {
                        handler.sendEmptyMessage(0);
                    }
                }
            };
        } else {
            if(stat == 1) {
                return task;
            } else {
                task.cancel();
                task = new TimerTask() {
                    @Override
                    public void run() {
                        if (mediaPlayer == null)
                            return;
                        if (mediaPlayer.isPlaying()) {
                            handler.sendEmptyMessage(0);
                        }
                    }
                };
            }
        }
        return task;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int duration = mediaPlayer.getDuration();

            if (duration > 0) {
                if (updateProcess1 != null) {
                    updateProcess1.updateProcess(mediaPlayer.getCurrentPosition(), duration);
                    updateProcess1.setLocal(mediaPlayer);
                }
            }
        }
    };

    public void setUpdateProcess(IUpdateProcess updateProcess) {
        this.updateProcess1 = updateProcess;
    }

}
