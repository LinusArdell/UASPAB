package com.twelve.mp3.player.API;

import com.twelve.mp3.player.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("judul") String judul,
            @Field("artis") String artis,
            @Field("album") String album,
            @Field("genre") String genre,
            @Field("lirik") String lirik,
            @Field("link") String link
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("judul") String judul,
            @Field("artis") String artis,
            @Field("album") String album,
            @Field("genre") String genre,
            @Field("lirik") String lirik,
            @Field("link") String link
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ModelResponse> ardLogin(
            @Field("username") String username,
            @Field("password") String password
    );
}
