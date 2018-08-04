package com.example.wtl.mymusic.ShowListereningMusic.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wtl.mymusic.DB.MusicStateSQL;
import com.example.wtl.mymusic.HttpUtil.HttpRequest;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.Service.PlayingMusicService;
import com.example.wtl.mymusic.ShowListereningMusic.Model.LyricModel;
import com.example.wtl.mymusic.ShowListereningMusic.Model.SongTextModel;
import com.example.wtl.mymusic.ShowListereningMusic.Presenter.Adapter.LyricAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 实现
 * Created by WTL on 2018/6/29.
 */

public class ShowListerenPresenterCompl implements IShowListerenPresenter {

    private Context context;
    private MusicStateSQL sql;
    private SQLiteDatabase database;
    private List<LyricModel> lyricModels = new ArrayList<>();
    private LyricAdapter adapter;
    private Timer timer = new Timer();
    private int local = 1;
    private long time = 57600000;
    private RecyclerView recyclerView;

    public ShowListerenPresenterCompl(Context context) {
        this.context = context;
    }

    @Override
    public void setSqlData(TextView name, ImageView playstop) {
        if (sql == null) {
            sql = new MusicStateSQL(context);
        }
        if (database == null) {
            database = sql.getWritableDatabase();
        }
        Cursor cursor = database.query(MusicStateSQL.SQL_NAME, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            String musicname = cursor.getString(cursor.getColumnIndex("songname"));
            if (musicname != null) {
                name.setText(musicname);
            }
            String musicstate = cursor.getString(cursor.getColumnIndex("state"));
            if (musicstate != null) {
                if (musicstate.equals("play")) {
                    playstop.setImageResource(R.mipmap.stopsong);
                } else if (musicstate.equals("stop")) {
                    playstop.setImageResource(R.mipmap.playingsong);
                }
            }
        }
    }

    @Override
    public void updateSql(String state) {
        if (sql == null) {
            sql = new MusicStateSQL(context);
        }
        if (database == null) {
            database = sql.getWritableDatabase();
        }
        ContentValues cv = new ContentValues();
        cv.put(MusicStateSQL.MUSIC_STATE, state);
        database.update(MusicStateSQL.SQL_NAME, cv, "_id=?", new String[]{"1"});
    }

    @Override
    public void stopOrPlaying(ImageView listerenplay) {
        Intent i = new Intent("com.example.wtl.mymusic.ShowListereningMusic.Presenter");
        Intent intent1 = new Intent(context, PlayingMusicService.class);
        if (listerenplay.getDrawable().getCurrent().getConstantState().
                equals(context.getResources().getDrawable(R.mipmap.stopsong).getConstantState())) {
            intent1.putExtra("flag_state", "pause");
            context.startService(intent1);
            listerenplay.setImageResource(R.mipmap.playingsong);
            updateSql("stop");
            i.putExtra("state", "stop");
            context.sendBroadcast(i);
            task.cancel();
        } else if (listerenplay.getDrawable().getCurrent().getConstantState().
                equals(context.getResources().getDrawable(R.mipmap.playingsong).getConstantState())) {
            intent1.putExtra("flag_state", "play");
            context.startService(intent1);
            listerenplay.setImageResource(R.mipmap.stopsong);
            updateSql("play");
            i.putExtra("state", "play");
            context.sendBroadcast(i);
            task.run();
        }
    }

    @Override
    public void getSongText(final RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (sql == null) {
            sql = new MusicStateSQL(context);
        }
        if (database == null) {
            database = sql.getWritableDatabase();
        }
        Cursor cursor = database.query(MusicStateSQL.SQL_NAME, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            String id = cursor.getString(cursor.getColumnIndex("musicId"));
            if (id != null) {
                String url = "/lyric?id=" + id;
                HttpRequest.GetHttpRequest(url).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("获取歌词错误", "错错错错错");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                String json = response.body().string();
                                Gson gson = new Gson();
                                //解析格式
                                Type type = new TypeToken<SongTextModel>() {
                                }.getType();
                                SongTextModel model = gson.fromJson(json, type);
                                Message message = new Message();
                                message.what = 0;
                                message.obj = model;
                                hand.sendMessage(message);
                            } else {
                                Log.e("获取错误", "数据为空");
                            }
                        } else {
                            Log.e("获取错误", "notSuccessful");
                        }
                    }
                });
            }
        }
    }

    @Override
    public void downLoadMusic() {
        
    }

    Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0 && msg.obj != null) {
                SongTextModel model = (SongTextModel) msg.obj;
                getList(model.getLrc().getLyric());
                LinearLayoutManager m = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(m);
                adapter = new LyricAdapter(lyricModels, context);
                recyclerView.setAdapter(adapter);
                 timer.schedule(task, 0, 10);
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (time == lyricModels.get(local - 1).getStart()) {
                handler.sendEmptyMessage(0);
                time += 10;
            } else {
                time += 10;
            }
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                local++;
                recyclerView.smoothScrollToPosition(local + 4);
                if(lyricModels.get(local).getNode() != null) {
                    adapter.setColor(local);
                }
            }
        }
    };

    private long getTime(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date date = format.parse(s);
        return date.getTime();
    }

    private void getList(String s) {

        String[] arr = s.split("\\n");
        for (int i = 0; i < arr.length - 1; i++) {
            String[] s1 = arr[i].split("\\]");
            String[] s2 = arr[i + 1].split("\\]");
            LyricModel model = new LyricModel();
            try {
                model.setStart(getTime(remove(s1[0])));
                model.setEnd(getTime(remove(s2[0])));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (s1.length > 1) {
                model.setNode(s1[1]);
            }
            lyricModels.add(model);
        }
    }

    private String remove(String t) {
        String s = "";
        for (int i = 1; i < t.length(); i++) {
            s += t.substring(i, i + 1);
        }
        s = "1970-01-02 00:" + s;
        return s;
    }

}
