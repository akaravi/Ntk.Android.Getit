package ntk.android.getit.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;

import ntk.android.getit.R;
import ntk.android.getit.TicketingApp;
import ntk.android.getit.activity.BaseActivity;
import ntk.android.getit.utill.CaptchaReadyListener;
import ntk.base.api.core.entity.CaptchaModel;

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
            ImageLoader.getInstance().displayImage(lastCaptcha.image, (ImageView) findViewById(R.id.captchaImg));

        }
    }
}
