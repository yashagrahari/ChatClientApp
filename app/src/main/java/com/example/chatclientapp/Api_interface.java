package com.example.chatclientapp;



import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api_interface {

   @POST("chat/")
   Observable<List<Validation0>> postInit(@Body AuthBody authBody);

   @GET("chat/")
    Observable<List<Validation0>> postInit2(@Query("user") String username);

    @POST("chat/")
    Observable<List<Validation0>> postInit(@Body Authbody2 authBody);

    @POST("chat/")
    Observable<List<Validation0>> postInit(@Body Authbody3 authBody);
}
