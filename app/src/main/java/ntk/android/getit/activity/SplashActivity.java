package ntk.android.getit.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.BuildConfig;

import ntk.android.getit.R;

public class SplashActivity extends AppCompatActivity {
    long TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((TextView) findViewById(R.id.getitTxt)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HARLOWSI.TTF"));
        ((TextView) findViewById(R.id.corpTv)).setText("@NTK");
        ((TextView) findViewById(R.id.corpTv)).setText("Version  " + (BuildConfig.VERSION_NAME));
        new Handler().postDelayed(() -> {
//                Loading.setVisibility(View.GONE);
//            startActivity(new Intent(SplashActivity.this, MainAcstivity.class));
//            finish();
        }, TIME);
        return;
    }
}
