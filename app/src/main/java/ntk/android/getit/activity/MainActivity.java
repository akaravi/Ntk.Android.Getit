package ntk.android.getit.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

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
import ntk.android.getit.view.helpers.MainHelper;
import ntk.base.api.core.interfase.ICoreGet;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;
import ntk.base.api.utill.RetrofitManager;

public class MainActivity extends BaseActivity {
    MainHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MainHelper(findViewById(R.id.mainLinear));

    }

    public void showResultFragment(LinkManagementTargetActShortLinkGetResponce linkResponse) {
        //todo show fragmnet
    }
}
