package com.dragon.wtudragondesign.retrofit;

import com.dragon.wtudragondesign.bean.ResultEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by dragon on 2017/12/16.
 *
 */

public interface ApiService {
    //用户登陆
    @POST("login")
    Call<ResultEntity> login(@QueryMap Map<String, String> params);

}
