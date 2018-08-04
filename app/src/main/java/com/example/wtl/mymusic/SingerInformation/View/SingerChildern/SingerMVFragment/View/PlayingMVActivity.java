package com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.View;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter.ISingerMVPresenter;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerMVFragment.Presenter.SingerMVPresenterCompl;
import com.example.wtl.mymusic.Tool.HideScreenTop;

public class PlayingMVActivity extends AppCompatActivity implements View.OnClickListener {

    private IVideoView videoView;
    private ImageView pause;
    private SeekBar seekbar;
    private ISingerMVPresenter presenter;
    private LinearLayout asd;
    private CountDownTimer timer;
    private ImageView mv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_mv);
        HideScreenTop.HideScreenTop(getWindow());
        Montior();
        if (presenter == null) {
            presenter = new SingerMVPresenterCompl(this);
        }
        String id = getIntent().getStringExtra("url_id");
        String url = "/mv?mvid=" + id;
        presenter.playingVideo(url, videoView, seekbar);
    }

    private void Montior() {
        videoView = (IVideoView) findViewById(R.id.videoView);
        pause = (ImageView) findViewById(R.id.pause);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        asd = (LinearLayout) findViewById(R.id.asd);
        mv_back = (ImageView) findViewById(R.id.mv_back);

        pause.setOnClickListener(this);
        videoView.setOnClickListener(this);
        mv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoView:
                if(asd.getVisibility() == View.VISIBLE) {
                    asd.setVisibility(View.GONE);
                    mv_back.setVisibility(View.GONE);
                    timer.cancel();
                } else {
                    asd.setVisibility(View.VISIBLE);
                    mv_back.setVisibility(View.VISIBLE);
                    timer = new CountDownTimer(5000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {}

                        @Override
                        public void onFinish() {
                            asd.setVisibility(View.GONE);
                            mv_back.setVisibility(View.GONE);
                        }
                    }.start();
                }
                break;
            case R.id.pause:
                if(pause.getDrawable().getCurrent().getConstantState().
                        equals(getResources().getDrawable(R.mipmap.musicbegin).getConstantState())) {
                    pause.setImageResource(R.mipmap.musicstop);
                    videoView.start();
                } else if(pause.getDrawable().getCurrent().getConstantState().
                        equals(getResources().getDrawable(R.mipmap.musicstop).getConstantState())) {
                    pause.setImageResource(R.mipmap.musicbegin);
                    videoView.pause();
                }
                break;
            case R.id.mv_back:
                finish();
                break;
        }
    }
}
