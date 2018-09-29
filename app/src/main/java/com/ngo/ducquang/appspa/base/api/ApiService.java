package com.ngo.ducquang.appspa.base.api;

import com.ngo.ducquang.appspa.base.GlobalVariables;
import com.ngo.ducquang.appspa.base.getAddress.ResponseGetAddress;
import com.ngo.ducquang.appspa.base.reponseMessage.ResponseMessage;
import com.ngo.ducquang.appspa.login.modelLogin.ResponseLogin;
import com.ngo.ducquang.appspa.login.modelRegister.ResponseRegister;
import com.ngo.ducquang.appspa.slideMenu.logout.ResponseLogout;
import com.ngo.ducquang.appspa.storageList.createStore.model.ResponseCreateStore;
import com.ngo.ducquang.appspa.storageList.model.ResponseStoreList;
import com.ngo.ducquang.appspa.storageList.storeDetail.model.ResponseStoreDetail;
import com.ngo.ducquang.appspa.userList.model.ResponseGetListUser;
import com.ngo.ducquang.appspa.userList.model.ResponseUserDetail;

import java.util.ArrayList;
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
import retrofit2.http.POST;

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
    Call<ResponseUserDetail> userDetail(@FieldMap Map<String, String> params); //todo

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
    Call<ResponseStoreList> getStoreList(@Field("Token") String token);


    @POST("store/detail")
    @FormUrlEncoded
    Call<ResponseStoreDetail> storeDetail(@FieldMap Map<String, String> params);

    @POST("store/active")
    @FormUrlEncoded
    Call<ResponseMessage> activeOrUnActiveStore(@FieldMap Map<String, String> params);

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
                    .baseUrl(GlobalVariables.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            service = retrofit.create(ApiService.class);
            return service;
        }
    }
}
