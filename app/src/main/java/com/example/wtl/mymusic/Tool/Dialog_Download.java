package com.example.wtl.mymusic.Tool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wtl.mymusic.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载弹窗
 * Created by WTL on 2018/7/29.
 */

public class Dialog_Download extends Dialog {

    private ProgressBar progresss;
    private Button pause;
    private Button cancel;
    private int lastprogress;
    private TextView mname;

    private OnOperateMusic music;
    private String urls;
    private String name;
    private String brief;

    private boolean isPause = false;
    private boolean isCancel = false;

    private final int BeforeSuccess = 2;
    private final int Success = 1;
    private final int Pause = 0;
    private final int Cancel = -1;
    private final int Fails = -2;

    private Context context;

    public Dialog_Download(Context context, String urls, String name, String brief) {
        super(context, R.style.dialog);
        this.urls = urls;
        this.name = name;
        this.context = context;
        this.brief = brief;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog);
        pause = findViewById(R.id.dialog_pause);
        cancel = findViewById(R.id.dialog_cancel);
        progresss = findViewById(R.id.dialog_progress);
        mname = findViewById(R.id.dialog_name);
        mname.setText(name);

        DownloadM download = new DownloadM();
        download.execute(urls);
        Log.e("asdasdasdas", urls);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.setCancel();
                isCancel = true;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pause.getText().toString().equals("暂停")) {
                    isPause = true;
                    pause.setText("开始");
                } else {
                    DownloadM download1 = new DownloadM();
                    download1.execute(urls);
                    isPause = false;
                    pause.setText("暂停");

                }
            }
        });
    }

    private class DownloadM extends AsyncTask<String, Integer, Integer> {

        File file;
        RandomAccessFile accessFile;
        InputStream in;
        long isdownloadlength = 0;//已经下载的程度
        long alldownloadlength = 0;//文件总长度

        @Override
        protected Integer doInBackground(String... params) {
            final String url = params[0];
            alldownloadlength = getAllLenght();
            file = new File(context.getFilesDir(), url.substring(url.lastIndexOf("=") + 1));
            if (file.exists()) {
                isdownloadlength = file.length();
            }

            if (alldownloadlength == isdownloadlength) {
                Log.d("asdasdasdad", "文件已经存在");
                return BeforeSuccess;
            } else if (alldownloadlength == 0) {
                Log.d("asdasdasdad", "文件源不存在");
                return Fails;
            } else {
                try {
                    Request request = new Request.Builder()
                            .addHeader("RANGE", "bytes=" + isdownloadlength + "-")
                            .url(url)
                            .build();
                    Response response = new OkHttpClient().newCall(request).execute();
                    if (response.body() != null && response.isSuccessful()) {
                        in = response.body().byteStream();
                        accessFile = new RandomAccessFile(file, "rw");
                        accessFile.seek(isdownloadlength);
                        byte[] bytes = new byte[1024];
                        int len;
                        int total = 0;
                        while ((len = in.read(bytes)) != -1) {
                            if (isPause) {
                                return Pause;
                            } else if (isCancel) {
                                return Cancel;
                            } else {
                                total += len;
                                accessFile.write(bytes, 0, len);
                                int progress = (int) (((total + isdownloadlength) * 100) / alldownloadlength);
                                publishProgress(progress);
                            }
                        }
                        response.body().close();
                        return Success;
                    } else {
                        return Fails;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (accessFile != null) {
                            accessFile.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return Fails;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            if (progress > lastprogress) {
                progresss.setProgress(progress);
                lastprogress = progress;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            switch (integer) {
                case Success:
                    Intent intent = new Intent("com.example.wtl.mymusic.Tool.dialog");
                    intent.putExtra("dialog_name", name);
                    intent.putExtra("dialog_brief", brief);
                    intent.putExtra("dialog_address", file.getPath());
                    context.sendBroadcast(intent);
                    Toast.makeText(getContext(), "下载成功", Toast.LENGTH_SHORT).show();
                    music.setCancel();
                    break;
                case Pause:
                    Toast.makeText(getContext(), "下载暂停", Toast.LENGTH_SHORT).show();
                    break;
                case Cancel:
                    file.delete();
                    Toast.makeText(getContext(), "下载取消", Toast.LENGTH_SHORT).show();
                    break;
                case Fails:
                    Toast.makeText(getContext(), "下载失败", Toast.LENGTH_SHORT).show();
                    music.setCancel();
                    break;
                case BeforeSuccess:
                    Toast.makeText(getContext(), "文件已存在", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        private Long getAllLenght() {
            long length = 0;
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urls).build();
                Response response = client.newCall(request).execute();
                if (response.body() != null) {
                    length = response.body().contentLength();
                    response.body().close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return length;
        }
    }

    public interface OnOperateMusic {
        void setCancel();
    }

    public void setOnOperateMusic(OnOperateMusic music) {
        this.music = music;
    }

}
