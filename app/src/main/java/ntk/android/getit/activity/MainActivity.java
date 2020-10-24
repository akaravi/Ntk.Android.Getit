package ntk.android.getit.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import ntk.android.getit.R;
import ntk.android.getit.view.helpers.MainHelper;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;

public class MainActivity extends BaseActivity {
    MainHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MainHelper(this, findViewById(R.id.mainLinear));

    }

    public void showResultFragment(LinkManagementTargetActShortLinkGetResponce linkResponse) {
        helper.showResultFragment(linkResponse);
    }
}
