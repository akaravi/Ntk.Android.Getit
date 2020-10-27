package ntk.android.getit.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.getit.R;
import ntk.android.getit.TicketingApp;
import ntk.android.getit.activity.BaseActivity;
import ntk.android.getit.activity.MainActivity;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.android.getit.utill.AppUtill;
import ntk.android.getit.utill.CaptchaReadyListener;
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.linkManagemen.interfase.ILinkManagement;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetRequest;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetResponce;
import ntk.base.api.utill.RetrofitManager;

public abstract class BaseFragment extends Fragment implements CaptchaReadyListener {
    protected View findViewById(int id) {
        return getView().findViewById(id);
    }

    BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    @Override
    public void onCaptchaReady() {
        CaptchaModel lastCaptcha = ((TicketingApp) getActivity().getApplicationContext()).getCaptchaModel();
        if (lastCaptcha != null) {
            if (getView()!=null)
            ImageLoader.getInstance().displayImage(lastCaptcha.image, (ImageView) findViewById(R.id.captchaImg));

        }
    }


    public void callShortLinkSetApi(LinkManagementTargetActShortLinkSetRequest req) {
        if (AppUtill.isNetworkAvailable(getContext())) {
            //get captcha
            RetrofitManager retro = new RetrofitManager(getContext());
            ILinkManagement iTicket = retro.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(ILinkManagement.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());
            Observable<LinkManagementTargetActShortLinkSetResponce> Call = iTicket.LinkManagementTargetActShortLinkSet(headers, req);
            Call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<LinkManagementTargetActShortLinkSetResponce>() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull LinkManagementTargetActShortLinkSetResponce linkResponse) {
                            if (linkResponse.IsSuccess)
                                ((MainActivity) getActivity()).showResultSetFragment(linkResponse);
                            else
                                Toasty.warning(getContext(), linkResponse.ErrorMessage, Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            Toasty.warning(getContext(), "خطای سامانه", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {

            Toasty.warning(getContext(), "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }
}
