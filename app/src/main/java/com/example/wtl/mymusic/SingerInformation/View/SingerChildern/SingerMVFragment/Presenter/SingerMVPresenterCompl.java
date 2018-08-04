package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.wtl.mymusic.HttpUtil.HttpRequest;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Model.MVModel;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.View.PlayingMVActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * mv播放逻辑
 * Created by WTL on 2018/7/21.
 */

public class SingerMVPresenterCompl implements ISingerMVPresenter {

    private Context context;
    private String url = "/artist/mv?id=";
    private Handler handler;
    private Handler handler1;
    private Handler handler2;
    private String mv_url;

    public SingerMVPresenterCompl(Context context) {
        this.context = context;
    }

    @Override
    public void setMVRecycler(final RecyclerView recycler, final TextView wrong, final AVLoadingIndicatorView wait, String Id) {
        url += Id;
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        recycler.setLayoutManager(manager);

        wait.setVisibility(View.VISIBLE);
        wrong.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);

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
                recycler.setVisibility(View.GONE);
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
                        Type type = new TypeToken<MVModel>() {
                        }.getType();
                        MVModel model = gson.fromJson(json, type);
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
                    recycler.setVisibility(View.VISIBLE);
                    final MVModel model = (MVModel) msg.obj;
                    MVAdapter adapter = new MVAdapter(context, model.getMvs());
                    recycler.setAdapter(adapter);
                    adapter.setOnTouchSongsMV(new MVAdapter.OnTouchSongsMV() {
                        @Override
                        public void toTouchSongsMV(int position) {
                            Intent intent = new Intent(context, PlayingMVActivity.class);
                            intent.putExtra("url_id", model.getMvs().get(position).getId() + "");
                            context.startActivity(intent);
                        }
                    });
                }
                if (msg.what == 2) {
                    wait.setVisibility(View.GONE);
                    wrong.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    public void playingVideo(String url, final VideoView videoView, final SeekBar seekBar) {
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
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String object = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(object);
                            String brs = jsonObject1.getString("brs");
                            JSONObject jsonObject2 = new JSONObject(brs);
                            mv_url = jsonObject2.getString("720");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Message message = Message.obtain();
                        message.obj = mv_url;
                        message.what = 1;
                        handler1.sendMessage(message);
                    } else {
                        Log.e("请求错误：", "请求数据为空");
                    }
                } else {
                    Log.e("请求错误：", "请求失败！！！");
                }
            }
        });



        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1) {
                    String s = (String) msg.obj;
                    videoView.setVideoURI(Uri.parse(s));
                    videoView.start();
                }
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler2.sendEmptyMessage(2);
            }
        },0,1000);

        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                long duration = videoView.getDuration();
                long current = videoView.getCurrentPosition();
                long pos = seekBar.getMax() * current / duration;
                seekBar.setProgress((int) pos);
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int position;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    this.position = (progress*(videoView.getDuration()) / seekBar.getMax());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(position);
            }
        });

    }
}
