package com.oxycab.provider.ui.activity.document_upload;

import android.widget.Toast;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Document;
import com.oxycab.provider.ui.adapter.DocumentAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class DocumentUploadPresenter<V extends DocumentUploadIView> extends BasePresenter<V> implements DocumentUploadIPresenter<V> {

    @Override
    public void documents(List<MultipartBody.Part> myDocuments) {
        getMvpView().showLoading();
        Observable modelObservable = APIClient.getAPIClient().documents(myDocuments);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            Toast.makeText(getMvpView().activity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            DocumentUploadPresenter.this.getMvpView().onSuccess(true);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            DocumentUploadPresenter.this.getMvpView().onError((Throwable) throwable);
                        });
    }
}
