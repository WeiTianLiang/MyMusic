package com.example.wtl.mymusic.HttpUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 基础请求模式
 * Created by WTL on 2018/6/22.
 */

public class HttpRequest {

    public static Call GetHttpRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://lijiawei.com.cn:3300"+url)
                .build();
        Call call = client.newCall(request);
        return call;
    }

}
