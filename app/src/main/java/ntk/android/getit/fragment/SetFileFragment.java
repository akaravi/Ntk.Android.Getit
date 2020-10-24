package ntk.android.getit.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.getit.R;
import ntk.android.getit.config.ConfigRestHeader;
import ntk.android.getit.config.ConfigStaticValue;
import ntk.android.getit.utill.AppUtill;
import ntk.base.api.core.model.CaptchaResponce;
import ntk.base.api.file.interfase.IFile;
import ntk.base.api.utill.RetrofitManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SetFileFragment extends BaseFragment{
    private static final int READ_REQUEST_CODE = 150;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sub_set_file, container, false);

    }

    @Override
    public void onCaptchaReady(CaptchaResponce responce) {

    }
    private void UploadFileToServer(String url) {
        if (AppUtill.isNetworkAvailable(getContext())) {
            File file = new File(String.valueOf(Uri.parse(url)));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            RetrofitManager retro = new RetrofitManager(getContext());
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());
            IFile iFile = retro.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IFile.class);
            Observable<String> Call = iFile.uploadFileWithPartMap(headers, new HashMap<>(), MultipartBody.Part.createFormData("File", file.getName(), requestFile));
            Call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(String model) {
//                            adapter.notifyDataSetChanged();
//                            fileId.add(model);

                        }

                        @Override
                        public void onError(Throwable e) {

//                            attaches.remove(attaches.size() - 1);
//                            adapter.notifyDataSetChanged();
//                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    init();
//                                }
//                            }).show();
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

//                    attaches.add(getPath(NewTicketActivity.this, uri));
//                    adapter.notifyDataSetChanged();
//                    UploadFileToServer(getPath(NewTicketActivity.this, uri));
                }
            }
        }
    }
}
