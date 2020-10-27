package ntk.android.getit.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.getit.R;
import ntk.android.getit.TicketingApp;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.android.getit.utill.CaptchaReadyListener;
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.core.interfase.ICoreGet;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.utill.RetrofitManager;

public class BaseActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 102;
    private Dialog dialog;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    public void getCaptchaApi(CaptchaReadyListener f) {

        //get captcha
        RetrofitManager retro = new RetrofitManager(this);
        ICoreGet iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(ICoreGet.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
        Observable<CaptchaResponce> Call = iTicket.GetCaptcha(headers);
        showLoading();
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CaptchaResponce>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CaptchaResponce captchaResponce) {
                        hideLoading();
                        if (captchaResponce.IsSuccess) {
                            ((TicketingApp) getApplicationContext()).setCaptchaModel(captchaResponce.Item);
                            f.onCaptchaReady();
                        } else
                            Toasty.warning(BaseActivity.this, "خطا در دریافت کپچا", Toasty.LENGTH_LONG, true).show();


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideLoading();
                        Toasty.warning(BaseActivity.this, "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    public void showLoading() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_load);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void hideLoading() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        dialog = null;
    }

    @Override
    protected void onDestroy() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        super.onDestroy();
    }

    public CaptchaModel getLastCaptcha(CaptchaReadyListener f) {
        CaptchaModel captchaModel = ((TicketingApp) getApplicationContext()).getCaptchaModel();
        if (captchaModel == null)
            getCaptchaApi(f);
        else
            return captchaModel;
        return null;
    }
}
