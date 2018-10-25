package com.ngo.ducquang.appspa.base.api;

import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.getAddress.ResponseGetAddress;
import com.ngo.ducquang.appspa.base.reponseMessage.ResponseMessage;
import com.ngo.ducquang.appspa.modelImageSlide.ResponseGetImage;
import com.ngo.ducquang.appspa.modelStore.ResponseGetStore;
import com.ngo.ducquang.appspa.modelStore.ResponseGetStoreToOrder;
import com.ngo.ducquang.appspa.notification.model.ResponseNotification;
import com.ngo.ducquang.appspa.oder.model.ResponseDetailOrder;
import com.ngo.ducquang.appspa.oder.model.ResponseListOder;
import com.ngo.ducquang.appspa.login.modelLogin.ResponseLogin;
import com.ngo.ducquang.appspa.login.modelRegister.ResponseRegister;
import com.ngo.ducquang.appspa.oder.model.ResponseOrder;
import com.ngo.ducquang.appspa.report.model.ResponseReportByAddress;
import com.ngo.ducquang.appspa.report.model.ResponseReportByStore;
import com.ngo.ducquang.appspa.service.model.ResponseCreateService;
import com.ngo.ducquang.appspa.service.model.ResponseServiceAdmin;
import com.ngo.ducquang.appspa.slideMenu.logout.ResponseLogout;
import com.ngo.ducquang.appspa.storageList.createStore.model.ResponseCreateStore;
import com.ngo.ducquang.appspa.storageList.model.ResponseStoreList;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseComment;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseRate;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseStoreDetail;
import com.ngo.ducquang.appspa.userList.model.ResponseGetListUser;
import com.ngo.ducquang.appspa.userList.model.ResponseUserDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ducqu on 9/21/2018.
 */

public interface ApiService
{
    @POST("account/Login")
    @FormUrlEncoded
    Call<ResponseLogin> login(@FieldMap Map<String, String> params);

    @POST("user/create")
    @FormUrlEncoded
    Call<ResponseRegister> register(@FieldMap Map<String, String> params);

    @POST("user/update")
    @FormUrlEncoded
    Call<ResponseRegister> updateProfile(@FieldMap Map<String, String> params);

    @POST("account/Logout")
    @FormUrlEncoded
    Call<ResponseLogout> logout(@Field("Token") String token);

    @POST("user/index")
    @FormUrlEncoded
    Call<ResponseGetListUser> getListUser(@Field("Token") String token);

    @POST("user/detail")
    @FormUrlEncoded
    Call<ResponseUserDetail> userDetail(@FieldMap Map<String, String> params);

    @POST("BorrowEquip/approveindex")
    @FormUrlEncoded
    Call<ResponseBody> bookCalendar(@FieldMap Map<String, String> params);

    @GET("account/GetAddress")
    Call<ResponseGetAddress> getAddress();

    @POST("store/Create")
    @FormUrlEncoded
    Call<ResponseCreateStore> createStore(@FieldMap Map<String, String> params);


    @POST("store/update")
    @FormUrlEncoded
    Call<ResponseCreateStore> updateStore(@FieldMap Map<String, String> params); //todo thịnh check

    @POST("store/index")
    @FormUrlEncoded
    Call<ResponseStoreList> getStoreList(@FieldMap Map<String, String> params);


    @POST("store/detail")
    @FormUrlEncoded
    Call<ResponseStoreDetail> storeDetail(@FieldMap Map<String, String> params);

    @POST("store/active")
    @FormUrlEncoded
    Call<ResponseMessage> activeOrUnActiveStore(@FieldMap Map<String, String> params);

    @POST("store/rate")
    @FormUrlEncoded
    Call<ResponseRate> rateStore(@FieldMap Map<String, String> params);

    @POST("store/deleteRate")
    @FormUrlEncoded
    Call<ResponseRate> deleteRateStore(@FieldMap Map<String, String> params); //todo

    @POST("store/comment")
    @FormUrlEncoded
    Call<ResponseComment> commentStore(@FieldMap Map<String, String> params);//todo

    // service: //todo for admin
    @POST("manager/services")
    @FormUrlEncoded
    Call<ResponseServiceAdmin> getListServiceAdmin(@Field("Token") String token,
                                                   @Field("IDStore") int idStore);

    @POST("manager/createService")
    @FormUrlEncoded
    Call<ResponseCreateService> createService(@FieldMap Map<String, String> params);

    @POST("manager/updateService")
    @FormUrlEncoded
    Call<ResponseCreateService> updateService(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "manager/deleteService", hasBody = true)
    Call<ResponseMessage> deleteService(@Field("Token") String token, @Field("ID") int idService);

    // đặt lịch todo
    @POST("order/index")
    @FormUrlEncoded
    Call<ResponseListOder> getListOrder(@FieldMap Map<String, String> params);

    @POST("order/create")
    @FormUrlEncoded
    Call<ResponseOrder> orderCreate(@FieldMap Map<String, String> params);

    @POST("order/update")
    @FormUrlEncoded
    Call<ResponseOrder> orderUpdate(@FieldMap Map<String, String> params);

    // get store:
    @GET("account/getstores")
    Call<ResponseGetStore> getStore(@Query("Token") String token,
                                    @Query("IDCategory") String idCategory);

    @GET("account/GetImage")
    Call<ResponseGetImage> getImage(@Query("Token") String token);

    @GET("account/GetStoreToOrder")
    Call<ResponseGetStoreToOrder> getStoreToOrder(@Query("Token") String token,
                                                  @Query("Latitude") double latitude,
                                                  @Query("Longitude") double longitude);

    @GET("account/GetStoreToOrder")
    Call<ResponseGetStoreToOrder> getStoreToOrder(@Query("Token") String token,
                                                  @Query("IDSt") int idStore);

    @POST("order/Approved")
    @FormUrlEncoded
    Call<ResponseMessage> approvedOrder(@Field("Token") String token,
                                       @Field("ID") int idOrder);

    @POST("order/Rejected")
    @FormUrlEncoded
    Call<ResponseMessage> rejectOrder(@Field("Token") String token,
                                        @Field("ID") int idOrder);

    @POST("order/Cancel")
    @FormUrlEncoded
    Call<ResponseMessage> cancelOrder(@Field("Token") String token,
                                        @Field("ID") int idOrder);

    @POST("order/Done")
    @FormUrlEncoded
    Call<ResponseMessage> doneOrder(@Field("Token") String token,
                                        @Field("ID") int idOrder);

    @POST("order/detail")
    @FormUrlEncoded
    Call<ResponseDetailOrder> detailOrder(@FieldMap HashMap<String, String> params);

    // thông báo:
    @GET("notification/getnoti")
    Call<ResponseNotification> getNotificationInTop(@Query("Token") String token);

    @POST("notification/index")
    @FormUrlEncoded
    Call<ResponseNotification> getListNotification(@FieldMap HashMap<String, String> params);

    @POST("notification/create")
    @FormUrlEncoded
    Call<ResponseMessage> createNotification(@FieldMap HashMap<String, String> params);

    // thống kê
    @POST("manager/reportByStore")
    @FormUrlEncoded
    Call<ResponseReportByStore> reportByStore(@FieldMap HashMap<String, String> params);

    @POST("manager/reportbyaddress")
    @FormUrlEncoded
    Call<ResponseReportByAddress> reportByAddress(@FieldMap HashMap<String, String> params);

    class Factory
    {
        private static ApiService service;
        public static Retrofit retrofit;
        public static ApiService getInstance()
        {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            for(Cookie cookie : cookies)
                            {
                                HttpClientInstance.cookieStore.add(cookie);
                            }
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            return HttpClientInstance.cookieStore != null ? HttpClientInstance.cookieStore : new ArrayList<Cookie>();
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalVariables.BASE_URL_DEV)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            service = retrofit.create(ApiService.class);
            return service;
        }
    }
}
