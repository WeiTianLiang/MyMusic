package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wtl.mymusic.HttpUtil.HttpRequest;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Model.SingerSongModel;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presenter.Adapter.SongListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 音乐加载逻辑实现
 * Created by WTL on 2018/6/22.
 */

public class SingerSongCompl implements ISingerSong {

    private Context context;
    private String url = "/artists?id=";
    private Handler handler;

    public SingerSongCompl(Context context) {
        this.context = context;
    }

    @Override
    public void setSongListView(final ListView listView, String id, final AVLoadingIndicatorView wait, final TextView wrong) {
        url += id;
        wait.setVisibility(View.VISIBLE);
        wrong.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);

        /*
        * 等待时间，长时间没有结果，结束当前
        * */
        final CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                wait.setVisibility(View.GONE);
                wrong.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }.start();

        HttpRequest.GetHttpRequest(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("网络请求错误：", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String json = response.body().string();
                        Gson gson = new Gson();
                        //解析格式
                        Type type = new TypeToken<SingerSongModel>() {
                        }.getType();
                        SingerSongModel model = gson.fromJson(json, type);
                        Message message = Message.obtain();
                        if (model.getCode() == 200) {
                            message.obj = model;
                            message.what = 1;
                            handler.sendMessage(message);
                        } else {
                            message.arg1 = 1;
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    } else {
                        Log.e("请求错误：", "请求数据为空");
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
                    timer.cancel();
                    wait.setVisibility(View.GONE);
                    wrong.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    final SingerSongModel model = (SingerSongModel) msg.obj;
                    SongListAdapter adapter = new SongListAdapter(model.getHotSongs(), context);
                    listView.setAdapter(adapter);
                    adapter.setOnToSongPlaying(new SongListAdapter.OnToSongPlaying() {
                        @Override
                        public void toSongPlaying(int position) {
                            Intent intent = new Intent("com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presente");
                            intent.putExtra("singerName", model.getArtist().getName());
                            intent.putExtra("songName", model.getHotSongs().get(position).getName());
                            intent.putExtra("songPic", model.getHotSongs().get(position).getAl().getPicUrl());
                            intent.putExtra("songID", String.valueOf(model.getHotSongs().get(position).getId()));
                            context.sendBroadcast(intent);
                        }
                    });
                }
                if (msg.what == 2) {
                    wait.setVisibility(View.GONE);
                    wrong.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }
        };
    }
}
