package com.oxycab.provider.ui.activity.profile;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Panic;
import com.oxycab.provider.data.network.model.User;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfilePresenter<V extends ProfileIView> extends BasePresenter<V> implements ProfileIPresenter<V> {

    @Override
    public void profileUpdate(Map<String, RequestBody> params, MultipartBody.Part file) {
        Observable modelObservable = APIClient.getAPIClient().profileUpdate(params, file);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessUser((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void profilePanic(int panicFlag) {
        Observable modelObservable = APIClient.getAPIClient().profilePanic(panicFlag);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessPanic((Panic) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void getProfile() {
        Observable modelObservable = APIClient.getAPIClient().getProfile();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
