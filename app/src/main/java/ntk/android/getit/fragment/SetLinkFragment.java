package ntk.android.getit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import ntk.android.getit.activity.MainActivity;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.linkManagemen.interfase.ILinkManagement;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetRequest;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetRequest;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetResponce;
import ntk.base.api.utill.RetrofitManager;

public class SetLinkFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_set_link, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.captchaImg).setOnClickListener(v ->   getBaseActivity().getCaptchaApi(this));
        findViewById(R.id.generateBtn).setOnClickListener(v -> callApi());
        CaptchaModel lastCaptcha = getBaseActivity().getLastCaptcha(this);
        if (lastCaptcha != null) {
            ImageLoader.getInstance().displayImage(lastCaptcha.image, (ImageView) findViewById(R.id.captchaImg));

        }
    }

    private void callApi() {
        String url = ((EditText) findViewById(R.id.urlAddressEt)).getText().toString();
        String captchaText = ((EditText) findViewById(R.id.captchaEt)).getText().toString();
        if (url.trim().equals(""))
            Toasty.warning(getContext(), "آدرس وارد نشده است", Toasty.LENGTH_LONG, true).show();
        else if (captchaText.trim().equals(""))
            Toasty.warning(getContext(), "متن کپچا وارد نشده است", Toasty.LENGTH_LONG, true).show();
        else{
        LinkManagementTargetActShortLinkSetRequest req = new LinkManagementTargetActShortLinkSetRequest();
        req.CaptchaText = captchaText;
        req.UrlAddress = url;
        req.CaptchaKey = TicketingApp.getInstance().getCaptchaModel().Key;
        callShortLinkSetApi(req);
    }}


}
