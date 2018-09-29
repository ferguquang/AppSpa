package com.ngo.ducquang.appspa.base.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hdh lee ltronghau@gmail.com +8498 345 7505 on 5/22/2017.
 */

public class HeaderInterceptor implements Interceptor
{
    private final String userAgent;

    public HeaderInterceptor(String userAgent)
    {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest.newBuilder()
                .header("User-Agent", userAgent)
//                .header("Connection", "Keep-Alive")
                .addHeader("Connection", "close")
                .addHeader("Accept", "application/json")
//                .addHeader("Cookie", "ASP.NET_SessionId=qh402uvxtlqls2vah0dwyo2w")
//                .addHeader("Cookie", "cookie-name=cookie-value")
//                .addHeader("Cookie", "TestCookie=Test")
//                .addHeader("content-type", "application/json")
                .header("charset", "utf-8")
                .build();

        return chain.proceed(requestWithUserAgent);
    }
}