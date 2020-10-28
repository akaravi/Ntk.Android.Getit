package ntk.android.getit.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ntk.android.getit.R;
import ntk.android.getit.entity.UploadResultEntity;

abstract class BaseResultFragment extends BaseFragment {
   protected UploadResultEntity response=new UploadResultEntity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.keyTxt).setOnClickListener(v -> onClickCopy((TextView) v));
        findViewById(R.id.urlTxt).setOnClickListener(v -> onClickCopy((TextView) v));
        findViewById(R.id.shortUrlTxt).setOnClickListener(v -> onClickCopy((TextView) v));
        if (response.Key != null && !response.Key.equals("")) {
            ((TextView) findViewById(R.id.keyTxt)).setText(response.Key);
        } else {
            findViewById(R.id.keyWord).setVisibility(View.GONE);
            findViewById(R.id.keyTxt).setVisibility(View.GONE);
        }
        if (response.Description != null && !response.Description.equals("")) {
            ((TextView) findViewById(R.id.DescriptionTxt)).setText(response.Description);
        } else {
            findViewById(R.id.DescriptionTxt).setVisibility(View.GONE);
            findViewById(R.id.DescriptionWord).setVisibility(View.GONE);
        }
        if (response.ShortLinkQRCodeBase64 != null && !response.ShortLinkQRCodeBase64.equals("")) {
            showQrCode();

        } else {
            findViewById(R.id.qrCodeImage).setVisibility(View.GONE);

        }
        if (response.ShortLinkUrl != null && !response.ShortLinkUrl.equals("")) {
            ((TextView) findViewById(R.id.shortUrlTxt)).setText(response.ShortLinkUrl);
        } else {
            findViewById(R.id.shortUrlWord).setVisibility(View.GONE);
            findViewById(R.id.shortUrlTxt).setVisibility(View.GONE);
        }
        if (response.UrlAddress != null && !response.UrlAddress.equals("")) {
            ((TextView) findViewById(R.id.shortUrlTxt)).setText(response.UrlAddress);
        } else {
            findViewById(R.id.UrlAddressWord).setVisibility(View.GONE);
            findViewById(R.id.UrlAddressTxt).setVisibility(View.GONE);
        }
    }

    private void showQrCode() {
        String encdoedDataString = response.ShortLinkQRCodeBase64.replace("data:image/jpeg;base64,", "");

        byte[] imageAsBytes = Base64.decode(encdoedDataString.getBytes(), 0);
        ((ImageView) findViewById(R.id.qrCodeImage)).setImageBitmap(BitmapFactory.decodeByteArray(
                imageAsBytes, 0, imageAsBytes.length));
    }

    public void onClickCopy(TextView v) {
        int sdk_Version = android.os.Build.VERSION.SDK_INT;
        if (sdk_Version < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(v.getText().toString());   // Assuming that you are copying the text from a TextView
            Toast.makeText(getActivity(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", v.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
    }
}
