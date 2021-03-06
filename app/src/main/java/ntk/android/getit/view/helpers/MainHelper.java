package ntk.android.getit.view.helpers;

import android.os.Handler;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.skydoves.doublelift.DoubleLiftLayout;
import com.skydoves.doublelift.OnExpandListener;

import ntk.android.getit.R;
import ntk.android.getit.activity.MainActivity;
import ntk.android.getit.fragment.BaseFragment;
import ntk.android.getit.fragment.GetLinkFragment;
import ntk.android.getit.fragment.ResultGetFragment;
import ntk.android.getit.fragment.ResultSetFragment;
import ntk.android.getit.fragment.SetFileFragment;
import ntk.android.getit.fragment.SetLinkFragment;
import ntk.android.getit.fragment.SetMemoFragment;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetResponce;

public class MainHelper {
    View v;
    int prevSelected = -1;

    MainActivity ac;

    public MainHelper(MainActivity activity, View view) {
        v = view;
        ac = activity;
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
        OnExpandListener listener = b -> {
            if (b) new Handler().postDelayed(() -> showFragment(), 300);
        };
        ((DoubleLiftLayout) v.findViewById(R.id.lift1)).setOnExpandListener(listener);
        ((DoubleLiftLayout) v.findViewById(R.id.lift2)).setOnExpandListener(listener);
        ((DoubleLiftLayout) v.findViewById(R.id.lift3)).setOnExpandListener(listener);
        ((DoubleLiftLayout) v.findViewById(R.id.lift4)).setOnExpandListener(listener);
    }

    public void setExpand(int k) {

        findLiftView(k).expand();
        findCardView(k).setCardBackgroundColor(ContextCompat.getColor(ac, R.color.purple_700));
        if (prevSelected != -1 && prevSelected != k) {
            findLiftView(prevSelected).collapse();
            findCardView(prevSelected).setCardBackgroundColor(ContextCompat.getColor(ac, R.color.colorPrimary));
        }
        prevSelected = k;

    }

    public void showFragment() {
// Replace the contents of the container with the new fragment
        FragmentTransaction ft = ac.getSupportFragmentManager().beginTransaction();
        BaseFragment frag = null;
        switch (prevSelected) {
            case 0:
                frag = new GetLinkFragment();
                break;
            case 1:
                frag = new SetLinkFragment();
                break;
            case 2:
                frag = new SetFileFragment();
                break;
            case 3:
                frag = new SetMemoFragment();
                break;
        }
        ft.replace(R.id.framelayout, frag);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showResultGetFragment(LinkManagementTargetActShortLinkGetResponce linkResponse) {
        FragmentTransaction ft = ac.getSupportFragmentManager().beginTransaction();
        ResultGetFragment frag = ResultGetFragment.newInstance(new Gson().toJson(linkResponse));
        ft.replace(R.id.framelayout, frag);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showResultSetFragment(LinkManagementTargetActShortLinkSetResponce linkResponse) {
        FragmentTransaction ft = ac.getSupportFragmentManager().beginTransaction();
        ResultSetFragment frag = ResultSetFragment.newInstance(new Gson().toJson(linkResponse));
        ft.replace(R.id.framelayout, frag);
        ft.addToBackStack(null);
        ft.commit();
    }

    private DoubleLiftLayout findLiftView(int k) {
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

    private CardView findCardView(int k) {
        if (k == 0)
            return v.findViewById(R.id.bt1);
        if (k == 1)
            return v.findViewById(R.id.bt2);
        if (k == 2)
            return v.findViewById(R.id.bt3);
        if (k == 3)
            return v.findViewById(R.id.bt4);
        return null;
    }

}
