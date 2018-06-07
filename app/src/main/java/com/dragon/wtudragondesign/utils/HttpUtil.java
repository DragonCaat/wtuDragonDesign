package com.dragon.wtudragondesign.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dragon on 2018/1/1.
 */

public class HttpUtil {

    public static void sendOkhttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
