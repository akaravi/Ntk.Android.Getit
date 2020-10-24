package ntk.android.getit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ntk.android.getit.R;
import ntk.base.api.core.model.CaptchaResponce;

public class SetFileFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_set_file, container, false);

    }

    @Override
    public void onCaptchaReady(CaptchaResponce responce) {

    }
}
