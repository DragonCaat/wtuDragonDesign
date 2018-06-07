package com.dragon.wtudragondesign.retrofit;

import com.dragon.wtudragondesign.bean.ResultEntity;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

/**
 * Created by dragon on 2017/12/16.
 *
 */

public interface ApiService {
    //用户登陆
    @POST("user/sign/in")
    Call<ResultEntity> login(@QueryMap Map<String, String> params);

    //用户注册
    @POST("user/sign/up")
    Call<ResultEntity> register(@QueryMap Map<String, String> params);

    //首页普通悬赏
    @GET("publish/list/1")
    Call<ResultEntity> publishNormal(@QueryMap Map<String, Object> params);

    //普通悬赏
    @GET("publish/list/0")
    Call<ResultEntity> publish(@QueryMap Map<String, Object> params);

    //首页VIP悬赏
    @GET("publish/vip/1")
    Call<ResultEntity> publishVip(@QueryMap Map<String, Object> params);

    //VIP悬赏
    @GET("publish/vip/0")
    Call<ResultEntity> publishV(@QueryMap Map<String, Object> params);

    //发布悬赏
    @Multipart
    @POST("publish")
    Call<ResultEntity> publishCourier(@QueryMap Map<String, Object> params, @PartMap Map<String, RequestBody> parts);

    //发布悬赏
    @POST("publish")
    Call<ResultEntity> publishCourier(@QueryMap Map<String, Object> params);


    //我发布的悬赏
    @GET("publish/my/{userId}")
    Call<ResultEntity> Mypublish(@Path("userId") int id);

    //我接受的悬赏
    @GET("publish/recive/{userId}")
    Call<ResultEntity> MyReceive(@Path("userId") int id);

    //悬赏详情
    @GET("publish/{userId}")
    Call<ResultEntity> publishDetail(@Path("userId") int id);

    //会员充值
    @PUT("user/{userId}")
    Call<ResultEntity> rechargeVip(@Path("userId") int id,@QueryMap Map<String, Object> params);

    //更新用户信息
    @PUT("user/{userId}")
    Call<ResultEntity> updateUserData(@Path("userId") int id,@QueryMap Map<String, Object> params);

    //获取用户信息
    @GET("user/{userId}")
    Call<ResultEntity> getUserData(@Path("userId") int id);

    //上传用户头像
    @Multipart
    @POST("user/uploadHeadImg")
    Call<ResultEntity> uploadHeadImg(@QueryMap Map<String, Object> params,@PartMap Map<String, RequestBody> parts);

    //删除悬赏
    @DELETE("publish/{publishId}")
    Call<ResultEntity> deletePublish(@Path("publishId") int id);

    //接受悬赏
    @PUT("publish/{publishId}/{receiveId}")
    Call<ResultEntity> receivePublish(@Path("publishId") int id,@Path("receiveId")int receiveId);
}
