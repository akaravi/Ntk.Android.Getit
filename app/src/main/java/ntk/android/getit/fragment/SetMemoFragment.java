package ntk.android.getit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;

import es.dmoral.toasty.Toasty;
import ntk.android.getit.R;
import ntk.android.getit.TicketingApp;
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetRequest;

public class SetMemoFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_set_memo, container, false);

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
        String memoTxt = ((EditText) findViewById(R.id.memoEt)).getText().toString();
        String captchaText = ((EditText) findViewById(R.id.captchaEt)).getText().toString();
        if (memoTxt.trim().equals(""))
            Toasty.warning(getContext(), "متن وارد نشده است", Toasty.LENGTH_LONG, true).show();
        else if (captchaText.trim().equals(""))
            Toasty.warning(getContext(), "متن کپچا وارد نشده است", Toasty.LENGTH_LONG, true).show();
        else {
            LinkManagementTargetActShortLinkSetRequest req = new LinkManagementTargetActShortLinkSetRequest();
            req.CaptchaText = captchaText;
            req.Description = memoTxt;
            req.CaptchaKey = TicketingApp.getInstance().getCaptchaModel().Key;
            callShortLinkSetApi(req);
        }
    }



}
