package ntk.android.getit;

import ntk.android.base.ApplicationParameter;
import ntk.android.base.NTKBASEApplication;

/**
 * Created by Mr.Soheil on 02/01/2016.
 */
public class MyApplication extends NTKBASEApplication {



    public MyApplication() {
        super();

//        applicationStyle = new VitrinAppStyle();
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public ApplicationParameter getApplicationParameter() {
        return new ApplicationParameter(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
    }


}
