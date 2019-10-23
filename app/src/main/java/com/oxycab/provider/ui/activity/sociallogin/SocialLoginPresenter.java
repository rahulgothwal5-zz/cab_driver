package com.oxycab.provider.ui.activity.sociallogin;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Token;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialLoginPresenter<V extends SocialLoginIView> extends BasePresenter<V> implements SocialLoginIPresenter<V> {

    @Override
    public void loginGoogle(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().loginGoogle(obj);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Token) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
    @Override
    public void loginFacebook(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().loginFacebook(obj);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Token) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
