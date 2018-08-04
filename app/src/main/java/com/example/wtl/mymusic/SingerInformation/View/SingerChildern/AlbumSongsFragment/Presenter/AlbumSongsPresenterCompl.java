package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wtl.mymusic.HttpUtil.HttpRequest;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.Model.AlbumSongModel;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Model.SingerAlbumModel;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Presenter.AlbumAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 实现接口逻辑
 * Created by WTL on 2018/7/20.
 */

public class AlbumSongsPresenterCompl implements IAlbumSongsPresenter {

    private Context context;
    private String url = "/album?id=";
    private Handler handler;

    public AlbumSongsPresenterCompl(Context context) {
        this.context = context;
    }

    @Override
    public void setAlbumSongsRecycler(final RecyclerView recycler, String Id, final String name) {
        url += Id;
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recycler.setLayoutManager(manager);
        HttpRequest.GetHttpRequest(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求错误：", "请求失败！！！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.code() == 200) {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        Type type = new TypeToken<AlbumSongModel>() {
                        }.getType();
                        AlbumSongModel model = gson.fromJson(json, type);
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = model;
                        handler.sendMessage(message);
                    } else {
                        Log.e("请求错误：", "数据为空！！！");
                    }
                } else {
                    Log.e("请求错误：", "请求失败！！！");
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    final AlbumSongModel model = (AlbumSongModel) msg.obj;
                    if (model != null) {
                        AlbumSongAdapter adapter = new AlbumSongAdapter(context, model.getSongs());
                        recycler.setAdapter(adapter);
                        adapter.setOnTouchTheAlbum(new AlbumSongAdapter.OnTouchAlbumSongs() {
                            @Override
                            public void toTouchAlbumSongs(int position) {
                                Intent intent = new Intent("com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presente");
                                intent.putExtra("singerName", name);
                                intent.putExtra("songName", model.getSongs().get(position).getName());
                                intent.putExtra("songPic", model.getSongs().get(position).getAl().getPicUrl());
                                intent.putExtra("songID", String.valueOf(model.getSongs().get(position).getId()));
                                context.sendBroadcast(intent);
                            }
                        });
                    }
                }
            }
        };
    }
}
