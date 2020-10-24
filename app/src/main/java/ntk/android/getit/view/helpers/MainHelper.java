package ntk.android.getit.view.helpers;

import android.view.View;

import com.skydoves.doublelift.DoubleLiftLayout;

import ntk.android.getit.R;

public class MainHelper {
    View v;
    int prevSelected = -1;

    public MainHelper(View view) {
        v = view;
        v.findViewById(R.id.bt1).setOnClickListener(v -> {
            setExpand(0);
        });
        v.findViewById(R.id.bt2).setOnClickListener(v -> {
            setExpand(1);
        });
        v.findViewById(R.id.bt3).setOnClickListener(v -> {
            setExpand(2);
        });
        v.findViewById(R.id.bt4).setOnClickListener(v -> {
            setExpand(3);
        });
    }

    public void setExpand(int k) {

        findLift(k).expand();
        if (prevSelected != -1)
            findLift(prevSelected).collapse();
        prevSelected = k;
    }

    private DoubleLiftLayout findLift(int k) {
        if (k == 0)
            return v.findViewById(R.id.lift1);
        if (k == 1)
            return v.findViewById(R.id.lift2);
        if (k == 2)
            return v.findViewById(R.id.lift3);
        if (k == 3)
            return v.findViewById(R.id.lift4);
        return null;
    }
}
