package ntk.android.getit.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.getit.R;
import ntk.android.getit.TicketingApp;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.android.getit.model.FileUploadModel;
import ntk.android.getit.utill.AppUtill;
import ntk.android.getit.utill.FileManaging;
import ntk.base.api.core.entity.CaptchaModel;
import ntk.base.api.file.interfase.IFile;
import ntk.base.api.linkManagemen.model.LinkManagementTargetActShortLinkSetRequest;
import ntk.base.api.utill.RetrofitManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class SetFileFragment extends BaseFragment {
    Uri fileName;
    String uploadedString = "";
    private static final int READ_REQUEST_CODE = 150;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_set_file, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(R.id.captchaImg).setOnClickListener(v -> getBaseActivity().getCaptchaApi(this));
        findViewById(R.id.generateBtn).setOnClickListener(v -> callApi());
        CaptchaModel lastCaptcha = getBaseActivity().getLastCaptcha(this);
        if (lastCaptcha != null) {
            ImageLoader.getInstance().displayImage(lastCaptcha.image, (ImageView) findViewById(R.id.captchaImg));

        }
        findViewById(R.id.uploadBtn).setOnClickListener(v -> {
            if (CheckPermission()) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 220);
            }
        });

    }

    private boolean CheckPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            return getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }


    private void callApi() {
        String captchaText = ((EditText) findViewById(R.id.captchaEt)).getText().toString();
        if (uploadedString == null)
            Toasty.warning(getContext(), "فایلی اپلود نشده است", Toasty.LENGTH_LONG, true).show();
        else if (captchaText.trim().equals(""))
            Toasty.warning(getContext(), "متن کپچا وارد نشده است", Toasty.LENGTH_LONG, true).show();
        else {
                LinkManagementTargetActShortLinkSetRequest req = new LinkManagementTargetActShortLinkSetRequest();
                req.CaptchaText = captchaText;
                req.UploadFileKey = uploadedString;
                req.CaptchaKey = TicketingApp.getInstance().getCaptchaModel().Key;
                callShortLinkSetApi(req);
            }
        }
    }



    private void UploadFileToServer() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            File file = new File(FileManaging.getPath(getContext(), fileName));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RetrofitManager retro = new RetrofitManager(getContext());
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());
            IFile iFile = retro.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IFile.class);
            Observable<ResponseBody> Call = iFile.uploadFileWithPartMap(headers, new HashMap<>(), MultipartBody.Part.createFormData("File", file.getName(), requestFile));
            getBaseActivity().showLoading();
            Call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(ResponseBody model) {
                            getBaseActivity().hideLoading();
                            ((TextView) findViewById(R.id.status)).setText("");
                            try {
                                uploadedString = new Gson().fromJson(model.string(), FileUploadModel.class).FileKey;
                            } catch (IOException e) {
                                Toasty.warning(getContext(), "خطای خواندن اطلاعات", Toasty.LENGTH_LONG, true).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            getBaseActivity().hideLoading();
                            getBaseActivity().getCaptchaApi(SetFileFragment.this);
                            Toasty.warning(getContext(), "خطای در بارگذاری فایل", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {

            Toasty.warning(getContext(), "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                if (uri != null) {
                    fileName = uri;
                    UploadFileToServer();
                }
            }
        }
    }


}
