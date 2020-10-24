package ntk.android.getit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import ntk.android.getit.R;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkGetResponce;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultGetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultGetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinkManagementTargetActShortLinkGetResponce shortLinkResponse;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Result.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultGetFragment newInstance(String param1) {
        ResultGetFragment fragment = new ResultGetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shortLinkResponse = new Gson().fromJson(getArguments().getString(ARG_PARAM1), LinkManagementTargetActShortLinkGetResponce.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo shwo result
    }
}