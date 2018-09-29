package com.ngo.ducquang.appspa.base.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by hdh lee ltronghau@gmail.com +8498 345 7505 on 5/22/2017.
 */

public class HttpClientInstance
{
    private static HttpClientInstance instance;
    private OkHttpClient client;
    //    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
    public static final List<Cookie> cookieStore = new ArrayList<>();

    public static HttpClientInstance instance()
    {
        synchronized (HttpClientInstance.class)
        {
            if (instance == null)
            {
                instance = new HttpClientInstance();
            }

            return instance;
        }
    }

    private HttpClientInstance()
    {
        client = createHttpClient();
    }

    private OkHttpClient createHttpClient()
    {
        String userAgent = "FSI DocPro Android";
        return new OkHttpClient.Builder()
            .addNetworkInterceptor(new HeaderInterceptor(userAgent))
            .connectTimeout(65, TimeUnit.SECONDS)
            .writeTimeout(55, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .cookieJar(new CookieJar()
            {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
                {
//                    LogManager.tagDefault().error("url" + url);
//                    for(Cookie cookie : cookies)
//                    {
//                        LogManager.tagDefault().error("name " + cookie.name());
//                    }
//
//                    cookieStore.put(url, cookies);

                    for(Cookie cookie : cookies)
                    {
//                        LogManager.tagDefault().error("name " + cookie.name());
                        cookieStore.add(cookie);
                    }
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url)
                {
//                    List<Cookie> cookies = cookieStore.get(url);
//                    LogManager.tagDefault().error("url" + url);
//                    if(cookies != null)
//                    {
//                        for(Cookie cookie : cookies)
//                        {
//                            LogManager.tagDefault().error("name " + cookie.name());
//                        }
//                    }
//
//                    return cookies != null ? cookies : new ArrayList<Cookie>();

//                    if(cookieStore != null)
//                    {
//                        LogManager.tagDefault().error("size " + cookieStore.size());
//                        for(Cookie cookie : cookieStore)
//                        {
//                            LogManager.tagDefault().error("name " + cookie.name());
//                        }
//                    }

                    return cookieStore != null ? cookieStore : new ArrayList<Cookie>();

//                    final ArrayList<Cookie> oneCookie = new ArrayList<>(1);
//                    oneCookie.add(createNonPersistentCookie());
//                    return oneCookie;
                }
            })
            .build();
    }

    public OkHttpClient getClient()
    {
        return client;
    }

    private Cookie createNonPersistentCookie()
    {
        return new Cookie.Builder()
                .domain("api.docpro.vn")
                .path("/")
//                .name("cookie-name")
//                .name("ASP.NET_SessionId")
                .name("TestCookie")
//                .value("cookie-value")
//                .value("qh402uvxtlqls2vah0dwyo2w")
                .value("Test")
                .httpOnly()
                .secure()
                .build();
    }
}
