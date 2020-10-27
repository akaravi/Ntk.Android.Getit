package ntk.android.getit;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import ntk.base.api.file.entity.FileUploadModel;
import ntk.base.api.file.interfase.IFile;
import ntk.base.api.utill.NTKUtill;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRt {
    public static final String TAG = "RetrofitManager";

    public static String BASE_URL = "https://apicms.ir/";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    private Context mContext;

    private Retrofit mRetrofit, mCachedRetrofit;

    private Cache mCache;
    private OkHttpClient mOkHttpClient, mCachedOkHttpClient;

    public MyRt(Context context) {
        mContext = context;
    }

    public Retrofit getRetrofit(String Url) {
        if (mRetrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(provideOfflineCacheInterceptor())
                    .addNetworkInterceptor(provideCacheInterceptor())
                    .cache(provideCache());

            mOkHttpClient = httpClient.build();
            String BaseUrl = BASE_URL;
            if (Url != null && !Url.isEmpty()) {
                BaseUrl = Url;
            }
            if (BaseUrl.charAt(BaseUrl.length() - 1) != '/')
                BaseUrl = BaseUrl + "/";
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }

        return mRetrofit;
    }

    public Retrofit getCachedRetrofit(String Url) {
        if (mCachedRetrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    // Add all interceptors you want (headers, URL, logging)
                    .addInterceptor(provideForcedOfflineCacheInterceptor())
                    .cache(provideCache());

            mCachedOkHttpClient = httpClient.build();
            String BaseUrl = BASE_URL;
            if (Url != null && !Url.isEmpty()) {
                BaseUrl = Url;
            }
            if (BaseUrl.charAt(BaseUrl.length() - 1) != '/')
                BaseUrl = BaseUrl + "/";
            mCachedRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mCachedOkHttpClient)
                    .build();
        }

        return mCachedRetrofit;
    }

    public Retrofit getRetrofitUnCached(String Url) {
        if (mRetrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(provideInterceptor());

            mOkHttpClient = httpClient.build();

            String BaseUrl = BASE_URL;
            if (Url != null && !Url.isEmpty()) {
                BaseUrl = Url;
            }
            if (BaseUrl.charAt(BaseUrl.length() - 1) != '/')
                BaseUrl = BaseUrl + "/";
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }


    private Cache provideCache() {
        if (mCache == null) {
            try {
                mCache = new Cache(new File(mContext.getCacheDir(), "http-cache"),
                        10 * 1024 * 1024); // 10 MB
            } catch (Exception e) {
                Log.e(TAG, "Could not create Cache!");
            }
        }

        return mCache;
    }

    private Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            CacheControl cacheControl;

            if (NTKUtill.isNetworkAvailable(mContext)) {
                cacheControl = new CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build();
            } else {
                cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();
            }

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build();

        };
    }

    private Interceptor provideInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header("User-Agent", "NTKApp-Android")
                    .build();

        };
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();

            if (!NTKUtill.isNetworkAvailable(mContext)) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

    private Interceptor provideForcedOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build();

            return chain.proceed(request);
        };
    }

    public void clean() {
        if (mOkHttpClient != null) {
            // Cancel Pending Request
            mOkHttpClient.dispatcher().cancelAll();
        }

        if (mCachedOkHttpClient != null) {
            // Cancel Pending Cached Request
            mCachedOkHttpClient.dispatcher().cancelAll();
        }

        mRetrofit = null;
        mCachedRetrofit = null;

        if (mCache != null) {
            try {
                mCache.evictAll();
            } catch (IOException e) {
                Log.e(TAG, "Error cleaning http cache");
            }
        }

        mCache = null;
    }
}
