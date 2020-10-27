package ntk.android.getit.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

import ntk.android.getit.activity.BaseActivity;
import ntk.android.getit.utill.CaptchaReadyListener;

public abstract class BaseFragment extends Fragment implements CaptchaReadyListener {
    protected View findViewById(int id) {
        return getView().findViewById(id);
    }

    BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

}
