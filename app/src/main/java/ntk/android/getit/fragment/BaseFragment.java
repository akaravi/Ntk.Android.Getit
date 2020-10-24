package ntk.android.getit.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected View findViewById(int id) {
        return getView().findViewById(id);
    }
}
