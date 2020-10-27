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
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.core.model.CaptchaResponce;

public class TicketingApp extends MultiDexApplication {
    private CaptchaModel captchaModel = null;
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


    public CaptchaModel getCaptchaModel() {
        return captchaModel;
    }

    public void setCaptchaModel(CaptchaModel item) {
        captchaModel = item;
    }
}
