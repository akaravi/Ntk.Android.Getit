package ntk.android.getit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.getit.R;
import ntk.android.getit.TicketingApp;
import ntk.android.getit.activity.MainActivity;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.base.api.linkManagemen.interfase.ILinkManagement;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetRequest;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;
import ntk.base.api.utill.RetrofitManager;

public class GetLinkFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_get_link, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.captchaImg).setOnClickListener(v -> TicketingApp.getInstance().getCaptcha());
        findViewById(R.id.generateBtn).setOnClickListener(v -> callApi());
    }

    private void callApi() {
        String keyText = ((EditText) findViewById(R.id.shortLinkEt)).getText().toString();
        String captchaText = ((EditText) findViewById(R.id.captchaEt)).getText().toString();
        if (keyText.trim().equals(""))
            Toasty.warning(getContext(), "کلید وارد نشده است", Toasty.LENGTH_LONG, true).show();
        else if (captchaText.trim().equals(""))
            Toasty.warning(getContext(), "متن کپچا وارد نشده است", Toasty.LENGTH_LONG, true).show();
        LinkManagementTargetActShortLinkGetRequest req = new LinkManagementTargetActShortLinkGetRequest();
        req.CaptchaText = captchaText;
        req.Key = keyText;
        req.CaptchaKey = TicketingApp.getInstance().getResponce().Item.Key;
        callapi(req);
    }

    private void callapi(LinkManagementTargetActShortLinkGetRequest req) {
        //get captcha
        RetrofitManager retro = new RetrofitManager(getContext());
        ILinkManagement iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(ILinkManagement.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());
        Observable<LinkManagementTargetActShortLinkGetResponce> Call = iTicket.LinkManagementTargetActShortLinkGet(headers, req);
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LinkManagementTargetActShortLinkGetResponce>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull LinkManagementTargetActShortLinkGetResponce linkResponse) {
                        ((MainActivity) getActivity()).showResultFragment(linkResponse);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Toasty.warning(getContext(), "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}