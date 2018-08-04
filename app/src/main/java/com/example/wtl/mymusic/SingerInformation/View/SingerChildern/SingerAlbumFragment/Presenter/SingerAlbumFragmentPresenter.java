package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wtl.mymusic.HttpUtil.HttpRequest;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerAlbumFragment.Model.SingerAlbumModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * mv逻辑
 * Created by WTL on 2018/7/20.
 */

public class SingerAlbumFragmentPresenter implements ISingerAlbumPresenter {

    private Context context;
    private String url = "/artist/album?id=";
    private Handler handler;

    public SingerAlbumFragmentPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void setAlbumRecycler(final RecyclerView recycler, String id, final TextView wrong, final AVLoadingIndicatorView wait) {
        url += id;
        final LinearLayoutManager manager = new LinearLayoutManager(context);
        recycler.setLayoutManager(manager);

        wait.setVisibility(View.VISIBLE);
        wrong.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);

        final CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                wait.setVisibility(View.GONE);
                wrong.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            }

        }.start();

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
                        Type type = new TypeToken<SingerAlbumModel>() {
                        }.getType();
                        SingerAlbumModel model = gson.fromJson(json, type);
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
                    final SingerAlbumModel model = (SingerAlbumModel) msg.obj;
                    timer.cancel();
                    wait.setVisibility(View.GONE);
                    wrong.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                    AlbumAdapter adapter = new AlbumAdapter(context,model.getHotAlbums());
                    recycler.setAdapter(adapter);
                    adapter.setOnTouchTheAlbum(new AlbumAdapter.OnTouchTheAlbum() {
                        @Override
                        public void toTouchTheAlbum(int position) {
                            Intent intent = new Intent("com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter");
                            intent.putExtra("viewlocal", "2");
                            intent.putExtra("SingerName", model.getHotAlbums().get(position).getName());
                            intent.putExtra("SingerPic", model.getHotAlbums().get(position).getPicUrl());
                            intent.putExtra("SingerID", String.valueOf(model.getHotAlbums().get(position).getId()));
                            context.sendBroadcast(intent);
                        }
                    });
                } else if (msg.what == 2) {
                    wait.setVisibility(View.GONE);
                    wrong.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
            }
        };
    }
}
