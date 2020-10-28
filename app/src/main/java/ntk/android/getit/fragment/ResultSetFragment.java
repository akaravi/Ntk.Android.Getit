package ntk.android.getit.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import ntk.android.getit.R;
import ntk.base.api.linkManagemen.entity.LinkManagementTargetActShortLinkSet;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetResponce;

public class ResultSetFragment extends BaseResultFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Result.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultSetFragment newInstance(String param1) {
        ResultSetFragment fragment = new ResultSetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            LinkManagementTargetActShortLinkSet model = new Gson().fromJson(getArguments().getString(ARG_PARAM1), LinkManagementTargetActShortLinkSetResponce.class).Item;
            response.Key = model.Key;
            response.ShortLinkUrl = model.ShortLinkUrl;
            response.ShortLinkQRCodeBase64 = model.ShortLinkQRCodeBase64;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
        //todo show
    }
}
