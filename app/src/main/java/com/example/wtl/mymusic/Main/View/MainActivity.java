package com.example.wtl.mymusic.Main.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wtl.mymusic.Main.Model.RecordDataModel;
import com.example.wtl.mymusic.Main.Presenter.IMainPresenter;
import com.example.wtl.mymusic.Main.Presenter.MainPresenterCompl;
import com.example.wtl.mymusic.R;
import com.example.wtl.mymusic.Service.PlayingMusicService;
import com.example.wtl.mymusic.ShowListereningMusic.View.ShowPlayingActivity;
import com.example.wtl.mymusic.SingerInformation.View.SingerChildern.AlbumSongsFragment.View.AlbumSongsFragment;
import com.example.wtl.mymusic.SingerInformation.View.SingerInformationFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout musicplaying;//正在播放栏
    private CircleImageView music_image;//歌曲图
    private TextView music_name;//歌曲名
    private TextView music_actor_name;//歌手名
    private ImageView playinglist;//播放列表
    private ImageView playingstop;//暂停开始

    private IntentFilter intentFilter;
    private IntentFilter intentFilter1;
    private IntentFilter intentFilter2;

    private MainAllFragment mainAllFragment;//主fragment
    private SingerInformationFragment singerInformationFragment;//歌手fragment
    private AlbumSongsFragment albumSongsFragment;//专辑fragment

    private String SingerPic;
    private String SingerName;
    private String SingerID;
    private Bundle bundle;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Integer> integerList = new ArrayList<>();
    private List<RecordDataModel> modelList = new ArrayList<>();

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Montior();
        if (presenter == null) {
            presenter = new MainPresenterCompl(this);
        }
        presenter.setSqlData(music_name, music_actor_name, playingstop, music_image);
        changeFragment(0);
        intentFilter = new IntentFilter("com.example.wtl.mymusic.SingerInformation.View.SingerChildern.SingerSongFragment.Presente");
        registerReceiver(broadcastReceiver, intentFilter);
        intentFilter1 = new IntentFilter("com.example.wtl.mymusic.OnLineMusic.ChildernControl.Singer.Presenter");
        registerReceiver(broadcastReceiverFragment, intentFilter1);
        intentFilter2 = new IntentFilter("com.example.wtl.mymusic.ShowListereningMusic.Presenter");
        registerReceiver(broadcastReceiver2, intentFilter2);
    }

    private void Montior() {
        musicplaying = (LinearLayout) findViewById(R.id.musicplaying);
        music_image = (CircleImageView) findViewById(R.id.music_image);
        music_name = (TextView) findViewById(R.id.music_name);
        music_actor_name = (TextView) findViewById(R.id.music_actor_name);
        playinglist = (ImageView) findViewById(R.id.playinglist);
        playingstop = (ImageView) findViewById(R.id.playingstop);

        playingstop.setOnClickListener(this);
        playinglist.setOnClickListener(this);
        musicplaying.setOnClickListener(this);
    }

    /*
    * 接收广播暂停/播放歌曲
    * */
    BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String statee = intent.getStringExtra("state");
            if (statee != null) {
                if (statee.equals("stop")) {
                    playingstop.setImageResource(R.mipmap.musicbegin);
                } else if (statee.equals("play")) {
                    playingstop.setImageResource(R.mipmap.musicstop);
                }
            }
        }
    };

    /*
    * 接收广播播放歌曲
    * */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int st = 0;
            String singerName = intent.getStringExtra("singerName");
            String songName = intent.getStringExtra("songName");
            String songPic = intent.getStringExtra("songPic");
            String songId = intent.getStringExtra("songID");
            if (singerName != null && songName != null &&
                    !music_name.equals(music_name.getText().toString()) && songPic != null) {
                music_actor_name.setText(singerName);
                music_name.setText(songName);
                Glide.with(MainActivity.this).load(songPic).into(music_image);
                st = 1;
            }
            if (st == 1) {
                Intent intent1 = new Intent(MainActivity.this, PlayingMusicService.class);
                intent1.putExtra("now_songId", songId);
                startService(intent1);
                playingstop.setImageResource(R.mipmap.musicstop);
                presenter.addSqlData(songId, songName, singerName, "play", songPic);
            }
        }
    };

    /*
    * 接收广播切换fragment
    * */
    BroadcastReceiver broadcastReceiverFragment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String flag = intent.getStringExtra("viewlocal");
            SingerPic = intent.getStringExtra("SingerPic");
            SingerName = intent.getStringExtra("SingerName");
            SingerID = intent.getStringExtra("SingerID");
            //判断当退出歌手歌单时，清空当前保存数据的list，重新存储
            if(integerList.size() == 1) {
                modelList.removeAll(modelList);
            }
            modelList.add(new RecordDataModel(SingerPic, SingerName, SingerID));
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("SingerPic", SingerPic);
            bundle.putString("SingerName", SingerName);
            bundle.putString("SingerID", SingerID);
            changeFragment(Integer.parseInt(flag));
        }
    };

    /*
    * 切换fragment
    * */
    private void changeFragment(int flag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch (flag) {
            case 0:
                if (mainAllFragment == null) {
                    mainAllFragment = new MainAllFragment();
                    transaction.add(R.id.allmianfragment, mainAllFragment);
                    mainAllFragment.setArguments(bundle);
                } else {
                    mainAllFragment.setArguments(bundle);
                    transaction.show(mainAllFragment);
                }
                fragmentList.add(0, mainAllFragment);
                integerList.add(0, 0);
                break;
            case 1:
                singerInformationFragment = new SingerInformationFragment();
                transaction.add(R.id.allmianfragment, singerInformationFragment);
                transaction.show(singerInformationFragment);
                fragmentList.add(0, singerInformationFragment);
                integerList.add(0, 1);
                break;
            case 2:
                albumSongsFragment = new AlbumSongsFragment();
                transaction.add(R.id.allmianfragment, albumSongsFragment);
                transaction.show(albumSongsFragment);
                fragmentList.add(0, albumSongsFragment);
                integerList.add(0, 2);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /*
    * 隐藏fragment
    * */
    private void hideFragment(FragmentTransaction transaction) {
        if (mainAllFragment != null) {
            transaction.hide(mainAllFragment);
        }
        if (singerInformationFragment != null) {
            transaction.hide(singerInformationFragment);
        }
        if (albumSongsFragment != null) {
            transaction.hide(albumSongsFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fragmentList.get(0) == mainAllFragment) {
                finish();
            } else {
                changeFragment(integerList.get(1));
                SingerID = modelList.get(0).getSingerID();
                SingerName = modelList.get(0).getSingerName();
                SingerPic = modelList.get(0).getSingerPic();
                integerList.remove(0);
                fragmentList.remove(0);
                integerList.remove(0);
                fragmentList.remove(0);
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverFragment);
        unregisterReceiver(broadcastReceiver2);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent intent1 = new Intent(MainActivity.this, PlayingMusicService.class);
        switch (view.getId()) {
            case R.id.playinglist:
                break;
            case R.id.playingstop:
                if (playingstop.getDrawable().getCurrent().getConstantState().
                        equals(this.getResources().getDrawable(R.mipmap.musicstop).getConstantState())) {
                    intent1.putExtra("flag_state", "pause");
                    startService(intent1);
                    playingstop.setImageResource(R.mipmap.musicbegin);
                    presenter.addSqlData(null, null, null, "stop", null);
                } else if (playingstop.getDrawable().getCurrent().getConstantState().
                        equals(this.getResources().getDrawable(R.mipmap.musicbegin).getConstantState())) {
                    intent1.putExtra("flag_state", "play");
                    startService(intent1);
                    playingstop.setImageResource(R.mipmap.musicstop);
                    presenter.addSqlData(null, null, null, "play", null);
                }
                break;
            case R.id.musicplaying:
                Intent intent = new Intent(MainActivity.this, ShowPlayingActivity.class);
                startActivity(intent);
                break;
        }
    }

    public String getSingerName() {
        return SingerName;
    }

    public String getSingerPic() {
        return SingerPic;
    }

    public String getSingId() {
        return SingerID;
    }

}
