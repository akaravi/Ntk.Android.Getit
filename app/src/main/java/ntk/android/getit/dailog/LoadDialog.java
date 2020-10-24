package ntk.android.getit.dailog;

import android.content.Context;
import android.content.Intent;

import ntk.android.getit.activity.LoadingActivity;

public class LoadDialog {

    public boolean showDialog(Context context) {
        Intent viewIntent = new Intent(context, LoadingActivity.class);
        viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(viewIntent);
        return true;
    }
}
