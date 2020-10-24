package ntk.android.getit;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import es.dmoral.toasty.Toasty;
import ntk.android.getit.utill.FontManager;
import ntk.base.api.core.model.CaptchaResponce;

public class TicketingApp extends MultiDexApplication {
    private static CaptchaResponce responce;
    public static boolean Inbox = false;

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
        MultiDex.install(base);
    }

    public static void RENEW_CAPTCHA(CaptchaResponce captchaResponce) {
//        DateTime( captchaResponce.Item.Expire)
    }

    public static void SET_CAPTCHA(CaptchaResponce captcha) {
    }
    public static void GET_CAPTCHA() {
    }
}
