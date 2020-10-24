package ntk.android.getit;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.android.getit.utill.FontManager;
import ntk.base.api.core.interfase.ICoreGet;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.utill.RetrofitManager;

public class TicketingApp extends MultiDexApplication {
    private CaptchaResponce responce;
    public static boolean Inbox = false;
    private static TicketingApp ourInstance;

    public static TicketingApp getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!new File(getCacheDir(), "image").exists()) {
            new File(getCacheDir(), "image").mkdirs();
        }
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(getCacheDir(), "image")))
                .diskCacheFileNameGenerator(imageUri -> {
                    String[] Url = imageUri.split("/");
                    return Url[Url.length];
                })
                .build();
        ImageLoader.getInstance().init(config);

        Toasty.Config.getInstance()
                .setToastTypeface(FontManager.GetTypeface(getApplicationContext(), FontManager.IranSans))
                .setTextSize(14).apply();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ourInstance = this;
        MultiDex.install(base);
    }

    public void renewCaptcha(CaptchaResponce captchaResponce) {
//        DateTime( captchaResponce.Item.Expire)
    }

    public void setCaptcha(CaptchaResponce captcha) {
    }

    public void getCaptcha() {
        //get captcha
        RetrofitManager retro = new RetrofitManager(this);
        ICoreGet iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(ICoreGet.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
        Observable<CaptchaResponce> Call = iTicket.GetCaptcha(headers);
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CaptchaResponce>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CaptchaResponce captchaResponce) {
                        setCaptcha(captchaResponce);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toasty.warning(ourInstance.getApplicationContext(), "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public CaptchaResponce getResponce() {
        return responce;
    }
}
