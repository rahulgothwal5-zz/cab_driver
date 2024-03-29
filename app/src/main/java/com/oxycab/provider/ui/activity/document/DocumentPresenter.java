package com.oxycab.provider.ui.activity.document;

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

public class DocumentPresenter<V extends DocumentIView> extends BasePresenter<V> implements DocumentIPresenter<V> {
    @Override
    public void documents() {

        Observable modelObservable = APIClient.getAPIClient().documents();
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            List<Document> documents = (List<Document>) trendsResponse;
                            DocumentPresenter.this.getMvpView().onSuccess(documents);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            DocumentPresenter.this.getMvpView().onError((Throwable) throwable);
                        });

    }

    @Override
    public void documents(List<MultipartBody.Part> myDocuments) {
        getMvpView().showLoading();
        Observable modelObservable = APIClient.getAPIClient().documents(myDocuments);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            Toast.makeText(getMvpView().activity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            //DocumentUploadPresenter.this.getMvpView().onSuccess((Object) trendsResponse);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            DocumentPresenter.this.getMvpView().onError((Throwable) throwable);
                        });
    }
}
