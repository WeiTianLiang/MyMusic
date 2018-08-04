package com.example.wtl.mymusic.ShowListereningMusic.View;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.Service.PlayingMusicService;
import com.example.wtl.mymusic.ShowListereningMusic.Presenter.IShowListerenPresenter;
import com.example.wtl.mymusic.ShowListereningMusic.Presenter.IUpdateProcess;
import com.example.wtl.mymusic.ShowListereningMusic.Presenter.ShowListerenPresenterCompl;
import com.example.wtl.mymusic.Tool.HideScreenTop;

import java.util.Date;

public class ShowPlayingActivity extends AppCompatActivity implements View.OnClickListener,IUpdateProcess {

    private TextView listerename;//歌名
    private ImageView listerenlike;//喜欢
    private ImageView listerendown;//下载
    private ImageView listerenwhile;//循环
    private ImageView listerenprivous;//上一首
    private ImageView listerenplay;//播放
    private ImageView listerennext;//下一首
    private ImageView listerenlist;//菜单
    private TextView nowtime;//现在的时间
    private TextView alltime;//歌曲总时间
    private SeekBar seekbar;//进度条
    private RecyclerView lyric;//歌词

    private IShowListerenPresenter presenter;
    private PlayingMusicService musicService;
    private long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_playing);
        HideScreenTop.HideScreenTop(getWindow());
        Montior();
        if (presenter == null) {
            presenter = new ShowListerenPresenterCompl(this);
        }
        presenter.setSqlData(listerename, listerenplay);
        presenter.getSongText(lyric);
        Intent intent = new Intent(ShowPlayingActivity.this,PlayingMusicService.class);
        bindService(intent,serviceConnection,0);
    }

    private void Montior() {
        listerename = (TextView) findViewById(R.id.listerename);
        listerenlike = (ImageView) findViewById(R.id.listerenlike);
        listerendown = (ImageView) findViewById(R.id.listerendown);
        listerenwhile = (ImageView) findViewById(R.id.listerenwhile);
        listerenprivous = (ImageView) findViewById(R.id.listerenprivous);
        listerenplay = (ImageView) findViewById(R.id.listerenplay);
        listerennext = (ImageView) findViewById(R.id.listerennext);
        listerenlist = (ImageView) findViewById(R.id.listerenlist);
        nowtime = (TextView) findViewById(R.id.nowtime);
        alltime = (TextView) findViewById(R.id.alltime);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        lyric = (RecyclerView) findViewById(R.id.lyric);

        listerenplay.setOnClickListener(this);
        listerendown.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listerenplay:
                presenter.stopOrPlaying(listerenplay);
                break;
            case R.id.listerendown:

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayingMusicService.MyBinder myBinder = (PlayingMusicService.MyBinder) iBinder;
            musicService = myBinder.getService();
            musicService.setUpdateProcess(ShowPlayingActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public void updateProcess(long current, long duration) {
        this.duration = duration;
        long pos = seekbar.getMax() * current / duration;
        seekbar.setProgress((int) pos);
        nowtime.setText(getTimer(current));
        alltime.setText(getTimer(duration));
    }

    @Override
    public void setLocal(final MediaPlayer mediaPlayer) {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int position;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {
                    this.position = (int) (i*duration / seekbar.getMax());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(position);
            }
        });
    }

    private String getTimer(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date date = new Date(time);
        String times = format.format(date);
        return times;
    }
}
