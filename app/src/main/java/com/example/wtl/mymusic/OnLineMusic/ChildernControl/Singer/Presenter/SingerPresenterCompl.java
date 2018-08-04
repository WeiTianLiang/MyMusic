package com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wtl.mymusic.HttpUtil.HttpRequest;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Model.SingerModel;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.Adapter.ListViewAdapter;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.Adapter.SingerCountriesAdapter;
import com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter.Adapter.SingerSexAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 歌手逻辑
 * Created by WTL on 2018/6/21.
 */

public class SingerPresenterCompl implements ISingerPresenter {

    private Context context;

    private List<String> countrieslist;

    private List<String> sexlist;

    private Handler handler;

    private String url = "/artist/list?cat=";
    private String num1 = "10";
    private String num2 = "01";

    public SingerPresenterCompl(Context context) {
        this.context = context;
    }

    @Override
    public void setCountriesAdapter(RecyclerView recyclerView, final ListView listView, final AVLoadingIndicatorView loding, final TextView wrongs) {
        loding.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        wrongs.setVisibility(View.GONE);
        if (countrieslist == null) {
            countrieslist = new ArrayList<>();
            countrieslist.add("华语");
            countrieslist.add("欧美");
            countrieslist.add("日本");
            countrieslist.add("韩国");
            countrieslist.add("其他");
        }
        SingerCountriesAdapter adapter = new SingerCountriesAdapter(context, countrieslist);
        recyclerView.setAdapter(adapter);
        adapter.setOnGetCityLocat(new SingerCountriesAdapter.OnGetCityLocat() {
            @Override
            public void getCityLocat(String p) {
                if (!num1.equals(p)) {
                    num1 = p;
                    setNameAdapter(listView, loding, wrongs);
                }
            }
        });
    }

    @Override
    public void setSexAdapter(RecyclerView recyclerView, final ListView listView, final AVLoadingIndicatorView loding, final TextView wrongs) {
        loding.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        wrongs.setVisibility(View.GONE);
        if (sexlist == null) {
            sexlist = new ArrayList<>();
            sexlist.add("男");
            sexlist.add("女");
            sexlist.add("组合");
        }
        SingerSexAdapter adapter = new SingerSexAdapter(context, sexlist);
        recyclerView.setAdapter(adapter);
        adapter.setOnGetSexLocat(new SingerSexAdapter.OnGetSexLocat() {
            @Override
            public void getSexLocat(String p) {
                if (!num2.equals(p)) {
                    num2 = p;
                    setNameAdapter(listView, loding, wrongs);
                }
            }
        });
    }

    @Override
    public void setNameAdapter(final ListView listView, final AVLoadingIndicatorView loding, final TextView wrongs) {
        loding.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        wrongs.setVisibility(View.GONE);

        final CountDownTimer timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                loding.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                wrongs.setVisibility(View.VISIBLE);
            }
        }.start();

        HttpRequest.GetHttpRequest(url + num1 + num2).enqueue(new Callback() {
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
                        Type type = new TypeToken<SingerModel>() {
                        }.getType();
                        SingerModel model = gson.fromJson(json, type);
                        Message message = Message.obtain();
                        message.obj = model;
                        message.what = 0;
                        handler.sendMessage(message);
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
                if (msg.what == 0) {
                    timer.cancel();
                    loding.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    wrongs.setVisibility(View.GONE);
                    final SingerModel model = (SingerModel) msg.obj;
                    List<SingerModel.artists> artistsList = model.getArtists();
                    ListViewAdapter adapter = new ListViewAdapter(context, artistsList);
                    listView.setAdapter(adapter);
                    adapter.setOnTouchJumpFragment(new ListViewAdapter.OnTouchJumpActivity() {
                        @Override
                        public void touchJumpActivity(int position) {
                            Intent intent = new Intent("com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter");
                            intent.putExtra("viewlocal","1");
                            intent.putExtra("SingerPic", model.getArtists().get(position).getImg1v1Url());
                            intent.putExtra("SingerName", model.getArtists().get(position).getName());
                            intent.putExtra("SingerID", String.valueOf(model.getArtists().get(position).getId()));
                            context.sendBroadcast(intent);
                        }
                    });
                }
            }
        };
    }
}
