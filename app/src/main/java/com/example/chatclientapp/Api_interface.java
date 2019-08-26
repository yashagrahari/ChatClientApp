package com.example.chatclientapp;



import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api_interface {

   @POST("chat1/login/")
   Observable<List<UserAdminmessagesandactivity3>> postInit(@Body AuthBody authBody);

   @GET("chat1/login/")
    Observable<List<UserAdminmessagesandactivity3>> postInit2(@Query("user") String username);

    @POST("chat1/msg/")
    Observable<List<Success>> postInit(@Body Authbody2 authBody);

    @POST("chat1/msg/")
    Observable<List<Success>> postInit(@Body Authbody3 authBody);

//    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "chat1/delete/", hasBody = true)
    Observable<List<UserAdminmessagesandactivity3>> analysis_delete_api10(@Body Authbody4 authbody4);

    @GET("chat1/refresh/")
    Observable<List<UserAdminmessagesandactivity3>> postInit3(@Query("user1") String username);


}
