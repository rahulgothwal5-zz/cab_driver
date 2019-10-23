package com.oxycab.provider.ui.activity.regsiter.details;

import android.widget.Toast;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.MyOTP;
import com.oxycab.provider.data.network.model.Status;
import com.oxycab.provider.data.network.model.User;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class AddDriverPresenter<V extends AddDriverIView> extends BasePresenter<V> implements AddDriverIPresenter<V> {

    @Override
    public void register(@PartMap Map<String, RequestBody> params, @Part List<MultipartBody.Part> files) {
        Observable modelObservable = APIClient.getAPIClient().register(params, files);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            AddDriverPresenter.this.getMvpView().onSuccess((User) trendsResponse);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            AddDriverPresenter.this.getMvpView().onError((Throwable) throwable);
                        });

    }

}
