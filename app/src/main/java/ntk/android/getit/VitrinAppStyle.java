package ntk.android.getit;


import ntk.android.base.ApplicationStyle;
import ntk.android.getit.activity.MainActivity;

public class VitrinAppStyle extends ApplicationStyle {
    public VitrinAppStyle() {
        super();
    }

    @Override
    public Class<?> getMainActivity() {
        return MainActivity.class;
    }

    public VitrinAppStyle defultConfig() {
        SIGNING_REQUIRED = false ;


        return this;
    }
}
