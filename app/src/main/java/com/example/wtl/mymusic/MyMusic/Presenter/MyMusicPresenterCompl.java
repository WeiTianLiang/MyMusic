package com.example.wtl.mymusic.MyMusic.Presenter;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wtl.mymusic.DB.MusicStateSQL;
import com.example.wtl.mymusic.MyMusic.Model.MyMusicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的音乐主界面逻辑实现类
 * Created by WTL on 2018/6/20.
 */

public class MyMusicPresenterCompl implements IMyMusicPresenter {

    private Context context;
    private IntentFilter filter;
    private List<MyMusicModel> myMusicModel = new ArrayList<>();
    private My_Music_Adapter adapter;
    private RecyclerView recyclerView;
    private MusicStateSQL sql;
    private SQLiteDatabase database;

    public MyMusicPresenterCompl(Context context) {
        this.context = context;
        sql = new MusicStateSQL(context);
        database = sql.getWritableDatabase();
        readSQL();
    }

    @Override
    public void setMyMusicAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        filter = new IntentFilter("com.example.wtl.mymusic.Tool.dialog");
        context.registerReceiver(receiver, filter);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

        adapter = new My_Music_Adapter(context, myMusicModel);
        recyclerView.setAdapter(adapter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra("dialog_name");
            String brief = intent.getStringExtra("dialog_brief");
            String address = intent.getStringExtra("dialog_address");

            if (name != null && brief != null && address != null) {
                MyMusicModel model = new MyMusicModel(name, brief, address);
                adapter.add(model);

                ContentValues cv = new ContentValues();
                cv.put(MusicStateSQL.LM_NAME, name);
                cv.put(MusicStateSQL.LM_BRIEF, brief);
                cv.put(MusicStateSQL.LM_ADDRESS, address);
                database.insert(MusicStateSQL.LM_TABLE,null,cv);
            }
        }
    };

    private void readSQL() {
        Cursor cursor = database.query(MusicStateSQL.LM_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("lm_name"));
                String brief = cursor.getString(cursor.getColumnIndex("lm_brief"));
                String address = cursor.getString(cursor.getColumnIndex("lm_address"));
                MyMusicModel model = new MyMusicModel(name, brief, address);
                myMusicModel.add(model);
            } while (cursor.moveToNext());
        }
    }

}
